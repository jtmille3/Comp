/*global define */
define(function(require) {
	'use strict';

	var compTemplate = window.comp['web/templates/comp.html'];

	/*
	Before implementing make sure click around the page doesn't
	get reset once we load new data.  Otherwise breaks user
	experience.

	if(Modernizr.localstorage) {
		seasons = JSON.parse(localStorage.seasons);
		$('#seasons').replaceWith(compTemplate(allData));
	}
	*/

	$.getJSON('/comp/service/competitive', function(competitive) {
		/*
		if(Modernizr.localstorage) {
			localStorage.seasons = JSON.stringify(seasons);
		}
		*/
		$('#seasons').replaceWith(compTemplate(competitive));
	});

	return 'Initialized';
});