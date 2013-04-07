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
			$('#competitive').replaceWith(compTemplate(competitive));

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
			console.log('clicked ' + teamId);
			var teamSchedule = [];
			for(var i = 0; i < season.leagueSchedule.length; i++) {
				var schedule = season.leagueSchedule[i];
				if(schedule.homeId === teamId || schedule.awayId === teamId) {
					teamSchedule.push(schedule);
				}
			}

			var teamScheduleTemplate = window.comp['web/templates/team_schedule.html'];
			var template = teamScheduleTemplate(teamSchedule);
			$('#' + season.id + '-team-schedule').replaceWith(template);
		}
	};
});