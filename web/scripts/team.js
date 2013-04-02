define(function(require) {
	'use strict';

	return {
		setTeams: function(seasons) {
			for(var i = 0; i < seasons.length; i++) {
				var season = seasons[i];
				var teams = {};
				for(var j = 0;j < season.teams.length; j++) {
					var team = season.teams[j];
					teams[team.id] = team;
				}
				season.teams = teams;
			}

			return seasons;
		}
	};
});