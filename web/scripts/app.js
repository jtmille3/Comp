/*global define */
define(function(require) {
	'use strict';

	var compTemplate = window.comp['web/templates/comp.html'];

	/*
	Before implementing make sure click around the page doesn't
	get reset once we load new data.  Otherwise breaks user
	experience.

	if(Modernizr.localstorage) {
		competitive = JSON.parse(localStorage.competitive);
		$('#competitive').replaceWith(compTemplate(allData));
	}
	*/

	$.getJSON('/comp/service/competitive', function(competitive) {
		/*
		if(Modernizr.localstorage) {
			localStorage.competitive = JSON.stringify(competitive);
		}
		*/
		$('#competitive').replaceWith(compTemplate(competitive));
	});

	return 'Initialized';
});