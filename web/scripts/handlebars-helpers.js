Handlebars.registerHelper('teamFetch', function(season, teamid, property){
	return season.teams[teamid][property];
});