/*global define */
define(function(require) {
	'use strict';
	return {
		render: function(competitive) {
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
				$('#' + season.id + '-playoff-table').tablesorter( {sortList: [[0,0]]} );
				$('#' + season.id + '-schedule-table').tablesorter( {sortList: [[2,0]]} ); 
				$('#' + season.id + '-player-statistics-table').tablesorter( {sortList: [[3,1]]} );
				$('#' + season.id + '-goalie-statistics-table').tablesorter( {sortList: [[2,1]]} );

				$('#' + season.id + '-standings-table tr').click(function(row) {
					var $row = $(row.currentTarget);
					var teamId = $row.attr('id');
					$row.addClass('selected').siblings().removeClass('selected');
					console.log('clicked ' + teamId);
				});
			}
		}
	};
});