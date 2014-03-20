/*global define */
define(function(require) {
	'use strict';

    var bracket = require('bracket');

	return {
		render: function(competitive) {
			var self = this;
			this.competitive = competitive;

            var headerTemplate = window.comp['web/app/templates/header.html'];
            $('#header').html(headerTemplate(competitive));

			var compTemplate = window.comp['web/app/templates/comp.html'];
			$('#competitive').html(compTemplate(competitive));

			$('#player-all-time-table').tablesorter( {sortList: [[1,1]]} );
			$('#goalie-all-time-table').tablesorter( {sortList: [[1,1]]} );

			for(var i = 0; i < competitive.seasons.length; i++) {
				var season = competitive.seasons[i];
				$('#' + season.id + '-standings-table').tablesorter( {sortList: [[0,0]]} );
				$('#' + season.id + '-playoff-schedule-table').tablesorter( {sortList: [[2,0]]} );
				$('#' + season.id + '-league-schedule-table').tablesorter( {sortList: [[2,0]]} ); 
				$('#' + season.id + '-player-statistics-table').tablesorter( {sortList: [[3,1]]} );
				$('#' + season.id + '-goalie-statistics-table').tablesorter( {sortList: [[2,1]]} );

				this.attachStandingsClickHandler(season);
				this.attachLeagueScheduleClickHandler(season);
				this.attachPlayoffScheduleClickHandler(season);
			}

            this.getWeather(function(weather) {
                var weatherTemplate = window.comp['web/app/templates/weather.html'];
                $('#weather').html(weatherTemplate(weather));
            });
		},
		attachStandingsClickHandler: function(season) {
			var self = this;
			$('#' + season.id + '-standings-table tr').click(function(row) {
				var $row = $(row.currentTarget);
				var teamId = parseInt($row.attr('id'), 10);
				$row.addClass('selected').siblings().removeClass('selected');
				self.selectedTeam(season, teamId);
				self.attachTeamClickHandler(teamId);
			});
		},
		attachTeamClickHandler: function(teamId) {
			var self = this;
			$('#' + teamId + '-team-schedule-table tr').click(function(row) {
				var $row = $(row.currentTarget);
				$row.addClass('selected').siblings().removeClass('selected');
				var gameId = parseInt($row.attr('id'), 10);
				self.selectedGame(gameId, teamId);
			});
		},
		attachLeagueScheduleClickHandler: function(season) {
			this.attachScheduleClickHandler(season, 'league');
		},
		attachPlayoffScheduleClickHandler: function(season) {
            this.attachBracket(season);
			this.attachScheduleClickHandler(season, 'playoff');
		},
		attachScheduleClickHandler: function(season, name) {
			var self = this;
			$('#' + season.id + '-' + name + '-schedule-table tr').click(function(row) {
				var $row = $(row.currentTarget);
				$row.addClass('selected').siblings().removeClass('selected');
				var gameId = parseInt($row.attr('id'), 10);
				$("#goalsRow").remove();

				if($row.hasClass('selectable')) {
					$("<tr id='goalsRow'><td colspan='4'>"+self.goalPrint(gameId)+"</td></tr>").insertAfter($row);
				}
			});
		},
        attachBracket: function(season) {
            var id = 'playoff-bracket-' + season.id;
            bracket.generate(id, season.playoffSchedule, false);
        },
		selectedTeam: function(season, teamId) {
			var schedule = this.schedule(season, teamId);
			var roster = this.roster(season, teamId);
            var team = this.getTeam(season, teamId);

			var teamScheduleTemplate = window.comp['web/app/templates/team_schedule.html'];
			var template = teamScheduleTemplate({
				id: team.teamId,
                name: team.team,
				schedule: schedule,
				roster: roster
			});

			$('#' + season.id + '-team-schedule').html(template);

			$('#' + team.teamId + '-team-schedule-table').tablesorter( {sortList: [[2,0]]} );
			$('#' + team.teamId + '-team-roster-table').tablesorter( {sortList: [[1,0]]} );
		},

		selectedGame: function(gameId, teamId) {
			$('#' + teamId + '-goal-summary').html(this.goalPrint(gameId));
		},

		goalPrint: function(gameId) {
			var goals = this.goals(gameId);
			var print = 'Goals: ';
			for(var i = 0; i < goals.length; i++) {
				var goal = goals[i];
				print += goal.player + ' (' + goal.times + ') &nbsp;';
			}
			return print;
		},

		goals: function(gameId) {
			var goals = [];
			var unique;
			for(var i = 0; i < this.competitive.goals.length; i++) {
				var goal = this.competitive.goals[i];
				if(goal.gameId === gameId) {
					if(goals.length === 0 || goals[goals.length - 1].playerId !== goal.playerId) {
						unique = goal;
						unique.times = 1;
						goals.push(unique);
					} else {
						unique.times++;
					}
				}
			}
			return goals;
		},
        getTeam: function(season, teamId) {
            for(var i = 0; i < season.standings.length; i++) {
                var team = season.standings[i];
                if(team.teamId === teamId) {
                    return team;
                }
            }

            return {};
        },
		roster: function(season, teamId) {
			var roster = [];
			for(var j = 0; j < season.playerStatistics.length; j++) {
				var player = season.playerStatistics[j];
				if(player.teamId === teamId) {
					roster.push(player);
				}
			}
			return roster;
		},
		schedule: function(season, teamId) {
			var schedule = [];
			for(var i = 0; i < season.leagueSchedule.length; i++) {
				var match = season.leagueSchedule[i];
                match.homeTeam = false;
                match.awayTeam = false;
				if(match.homeId === teamId) {
                    match.homeTeam = true;
					if(match.available) {
						if(match.homeScore > match.awayScore) {
							match.result = 'won';
						} else if(match.homeScore < match.awayScore) {
							match.result = 'lost';
						} else if(match.homeScore === match.awayScore && match.score) {
							match.result = 'tied';
						}
					}
					schedule.push(match);
				} else if(match.awayId === teamId) {
                    match.awayTeam = true;
					if(match.available) {
						if(match.homeScore < match.awayScore) {
							match.result = 'won';
						} else if(match.homeScore > match.awayScore) {
							match.result = 'lost';
						} else if(match.homeScore === match.awayScore && match.score) {
							match.result = 'tied';
						}
					}
					schedule.push(match);
				}
			}
			return schedule;
		},
        getWeather: function(callback) {
            // Specify the ZIP/location code and units (f or c)
            var loc = '27513';
            var u = 'f';

            var query = "SELECT item.condition FROM weather.forecast WHERE location='" + loc + "' AND u='" + u + "'";
            var cacheBuster = Math.floor((new Date().getTime()) / 1200 / 1000);
            var url = 'http://query.yahooapis.com/v1/public/yql?q=' + encodeURIComponent(query) + '&format=json&_nocache=' + cacheBuster;

            window['wxCallback'] = function(data) {
                callback(data.query.results.channel.item.condition);
            };

            $.ajax({
                url: url,
                dataType: 'jsonp',
                cache: true,
                jsonpCallback: 'wxCallback'
            });
        }
	};
});