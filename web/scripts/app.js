/*global define */
define(function(require) {
	'use strict';

	var compTemplate = window.comp['web/templates/comp.html'];
	$.getJSON('/comp/service/seasons', function(seasons) {
		console.log(seasons);
		$.getJSON('/comp/service/players', function(players) {
			var allData = {seasons:seasons,players:players};
			$('#seasons').append(compTemplate(allData));
		});
	});

	return 'Initialized';
});