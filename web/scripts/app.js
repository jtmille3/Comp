/*global define */
define(function(require) {
	'use strict';

	var compTemplate = window.comp['web/templates/comp.html'];
	var helper = require('./helper');
	var season = require('./season');

	/*
	Before implementing make sure click around the page doesn't
	get reset once we load new data.  Otherwise breaks user
	experience.

	if(Modernizr.localstorage) {
		var allData = {
			seasons:JSON.parse(localStorage.seasons),
			players:JSON.parse(localStorage.players)
		};
		$('#seasons').replaceWith(compTemplate(allData));
	}
	*/

	$.getJSON('/comp/service/seasons', function(seasons) {
		/*
		if(Modernizr.localstorage) {
			localStorage.seasons = JSON.stringify(seasons);
		}
		*/
		console.log(seasons);
		$.getJSON('/comp/service/players', function(players) {
			/*
			if(Modernizr.localstorage) {
				localStorage.players = JSON.stringify(players);
			}
			*/

			/*
			Really what we want is a fully built out seasons
			tree, and then a denormalized players list for
			all time statistics.  Bam!
			*/
			var playerMap = helper.createMap(players);
			season.addPlayers(seasons, playerMap);
			var allData = {
				seasons:seasons,
				players:players
			};

			$('#seasons').replaceWith(compTemplate(allData));
		});
	});

	return 'Initialized';
});