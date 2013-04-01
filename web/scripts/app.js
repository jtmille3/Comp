/*global define */
define(function(require) {
    'use strict';

    var compTemplate = window.comp['web/templates/comp.html'];

    $.getJSON('http://localhost:8080/comp/service/seasons', function(result) {
		for(var i = 0; i < result.length; i++)
		{
			var season = result[i];
			var teamDict = {};
			for(var j = 0;j < season.teams.length; j++)
			{
				var team = season.teams[j];
				teamDict[team.id] = team;
			}
			season.teams = teamDict;
		}
    	console.log(result);
        $('#seasons').append(compTemplate(result));
    });

    return 'Initialized';
});