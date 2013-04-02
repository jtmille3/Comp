/*global define */
define(function(require) {
	'use strict';

	var compTemplate = window.comp['web/templates/comp.html'];
	var teamHelper = require('./team');
	$.getJSON('http://localhost:8080/comp/service/seasons', function(seasons) {
		teamHelper.setTeams(seasons);
		$('#seasons').append(compTemplate(seasons));
	});

	return 'Initialized';
});