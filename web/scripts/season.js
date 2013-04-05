define(function(require) {
	'use strict';

	return {
		addPlayers: function(seasons, playerMap) {
			for(var i = 0; i < seasons.length; i++) {
				var season = seasons[i];
				season.players = [];
				season.goalies = [];
				for(var key in season.teams) {
					var team = season.teams[key];
					for(var k = 0; k < team.teamPlayers.length; k++) {
						var teamPlayer = team.teamPlayers[k];
						var player = playerMap[teamPlayer.playerId];
						player.team = team;
						player.goals = teamPlayer.goals;
						var person = {
							name: player.name,
							team: team,
							goals: teamPlayer.goals,
							shutouts: teamPlayer.shutouts
						};
						if(teamPlayer.goals > 0) {
							season.players.push(person);
						}
						if(teamPlayer.goalie) {
							season.goalies.push(person);
						}
					}
				}
			}
		}
	};
});