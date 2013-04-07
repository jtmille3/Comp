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
			var compTemplate = window.comp['web/templates/comp.html'];
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
			}
		},
		attachStandingsClickHandler: function(season) {
			var self = this;
			$('#' + season.id + '-standings-table tr').click(function(row) {
				var $row = $(row.currentTarget);
				var teamId = parseInt($row.attr('id'), 10);
				$row.addClass('selected').siblings().removeClass('selected');
				self.selectedTeam(season, teamId);
			});
		},
		selectedTeam: function(season, teamId) {
			var schedule = [];
			for(var i = 0; i < season.leagueSchedule.length; i++) {
				var match = season.leagueSchedule[i];
				if(match.homeId === teamId || match.awayId === teamId) {
					schedule.push(match);
				}
			}

			var roster = [];
			for(var j = 0; j < season.playerStatistics.length; j++) {
				var player = season.playerStatistics[j];
				if(player.teamId === teamId) {
					roster.push(player);
				}
			}

			var teamScheduleTemplate = window.comp['web/templates/team_schedule.html'];
			var template = teamScheduleTemplate({
				id: teamId,
				schedule: schedule,
				roster: roster
			});

			$('#' + season.id + '-team-schedule').html(template);

			$('#' + teamId + '-team-schedule-table').tablesorter( {sortList: [[2,0]]} );
			$('#' + teamId + '-team-roster-table').tablesorter( {sortList: [[1,1]]} );
		}
	};
});