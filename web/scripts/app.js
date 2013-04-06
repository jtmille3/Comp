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

	$.getJSON('/comp/service/seasons', function(seasons) {
		/*
		if(Modernizr.localstorage) {
			localStorage.seasons = JSON.stringify(seasons);
		}
		*/
		console.log(seasons);
		$('#seasons').replaceWith(compTemplate(seasons));
	});

	return 'Initialized';
});