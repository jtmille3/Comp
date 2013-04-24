/*global define */
define(function(require) {
	'use strict';
	return {
		render: function(competitive) {
			var self = this;
			this.competitive = competitive;

			/*
			if(Modernizr.localstorage) {
				localStorage.competitive = JSON.stringify(competitive);
			}
			*/
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
		selectedTeam: function(season, teamId) {
			var schedule = this.schedule(season, teamId);
			var roster = this.roster(season, teamId);

			var teamScheduleTemplate = window.comp['web/app/templates/team_schedule.html'];
			var template = teamScheduleTemplate({
				id: teamId,
				schedule: schedule,
				roster: roster
			});

			$('#' + season.id + '-team-schedule').html(template);

			$('#' + teamId + '-team-schedule-table').tablesorter( {sortList: [[2,0]]} );
			$('#' + teamId + '-team-roster-table').tablesorter( {sortList: [[1,0]]} );
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
				if(match.homeId === teamId) {
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
		}
	};
});