this["comp"] = this["comp"] || {};

Handlebars.registerPartial("alltime", Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [2,'>= 1.0.0-rc.3'];
helpers = helpers || Handlebars.helpers; data = data || {};
  var buffer = "", stack1, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n        <tr>\r\n          <td>";
  if (stack1 = helpers.player) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.player; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n          <td>";
  if (stack1 = helpers.goals) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.goals; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n          <td>";
  if (stack1 = helpers.leagueWinner) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.leagueWinner; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n          <td>";
  if (stack1 = helpers.playoffWinner) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.playoffWinner; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n        </tr>\r\n      ";
  return buffer;
  }

function program3(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n            <tr>\r\n              <td>";
  if (stack1 = helpers.player) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.player; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n              <td>";
  if (stack1 = helpers.shutouts) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.shutouts; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n            </tr>\r\n          ";
  return buffer;
  }

  buffer += "<div class=\"tab-pane\" id=\"all-time\">\r\n  <div class=\"row-fluid\">\r\n    <div class=\"span6\">\r\n      <table id=\"player-all-time-table\" class=\"table table-condensed table-bordered table-striped table-hover tablesorter\">\r\n    <thead>\r\n      <th>Name</th>\r\n      <th>Goals</th>\r\n      <th>League Titles</th>\r\n      <th>Championships</th>\r\n    </thead>\r\n    <tbody>\r\n      ";
  stack1 = helpers.each.call(depth0, depth0.playerStatistics, {hash:{},inverse:self.noop,fn:self.program(1, program1, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n    </tbody>\r\n  </table>\r\n    </div>\r\n    <div class=\"span6\">\r\n      <table id=\"goalie-all-time-table\" class=\"table table-condensed table-bordered table-striped table-hover tablesorter\">\r\n        <thead>\r\n          <th>Name</th>\r\n          <th>Shutouts</th>\r\n        </thead>\r\n        <tbody>\r\n          ";
  stack1 = helpers.each.call(depth0, depth0.goalieStatistics, {hash:{},inverse:self.noop,fn:self.program(3, program3, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n        </tbody>\r\n      </table>\r\n    </div>\r\n  </div>\r\n</div>";
  return buffer;
  }));

Handlebars.registerPartial("cache", Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [2,'>= 1.0.0-rc.3'];
helpers = helpers || Handlebars.helpers; data = data || {};
  


  return "<a href=\"#\" class=\"btn btn-primary btn-large\" id=\"reset-cache\">Reset Cache</a>";
  }));

Handlebars.registerPartial("games", Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [2,'>= 1.0.0-rc.3'];
helpers = helpers || Handlebars.helpers; data = data || {};
  


  return "<h3>Scores</h3>\r\n<div class=\"input-append date game-datepicker\" data-date-format=\"mm/dd/yyyy\">\r\n	<input size=\"16\" type=\"text\" readonly><span class=\"add-on\"><i class=\"icon-calendar\"></i></span>\r\n</div>\r\n<div id=\"admin-games\"></div>\r\n<div id=\"admin-game-score\"></div>";
  }));

Handlebars.registerPartial("league_schedule", Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [2,'>= 1.0.0-rc.3'];
helpers = helpers || Handlebars.helpers; data = data || {};
  var buffer = "", stack1, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n			<tr id=\"";
  if (stack1 = helpers.gameId) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.gameId; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" class=\"selectable\">\r\n				<td>";
  if (stack1 = helpers.home) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.home; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.away) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.away; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.played) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.played; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.score) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.score; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n			</tr>\r\n		";
  return buffer;
  }

  buffer += "<table id=\"";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "-league-schedule-table\" class=\"table table-condensed table-bordered table-striped table-hover tablesorter\">\r\n	<thead>\r\n		<th>Home</th>\r\n		<th>Away</th>\r\n		<th>Date</th>\r\n		<th>Result</th>\r\n	</thead>\r\n	<tbody>\r\n		";
  stack1 = helpers.each.call(depth0, depth0.leagueSchedule, {hash:{},inverse:self.noop,fn:self.program(1, program1, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n	</tbody>\r\n</table>";
  return buffer;
  }));

Handlebars.registerPartial("playoff_schedule", Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [2,'>= 1.0.0-rc.3'];
helpers = helpers || Handlebars.helpers; data = data || {};
  var buffer = "", stack1, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n			<tr id=\"";
  if (stack1 = helpers.gameId) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.gameId; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" class=\"selectable\">\r\n				<td>";
  if (stack1 = helpers.home) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.home; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.away) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.away; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.played) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.played; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.score) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.score; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n			</tr>\r\n		";
  return buffer;
  }

  buffer += "<table id=\"";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "-playoff-schedule-table\" class=\"table table-condensed table-bordered table-striped table-hover tablesorter\">\r\n	<thead>\r\n		<th>Home</th>\r\n		<th>Away</th>\r\n		<th>Date</th>\r\n		<th>Result</th>\r\n	</thead>\r\n	<tbody>\r\n		";
  stack1 = helpers.each.call(depth0, depth0.playoffSchedule, {hash:{},inverse:self.noop,fn:self.program(1, program1, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n	</tbody>\r\n</table>";
  return buffer;
  }));

Handlebars.registerPartial("standings", Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [2,'>= 1.0.0-rc.3'];
helpers = helpers || Handlebars.helpers; data = data || {};
  var buffer = "", stack1, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n			<tr id=\"";
  if (stack1 = helpers.teamId) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.teamId; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" class=\"selectable\">\r\n				<td>";
  if (stack1 = helpers.rank) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.rank; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.team) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.team; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.wins) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.wins; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.losses) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.losses; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.ties) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.ties; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.points) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.points; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.goalsFor) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.goalsFor; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.goalsAgainst) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.goalsAgainst; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.goalDifferential) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.goalDifferential; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.shutouts) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.shutouts; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n			</tr>\r\n		";
  return buffer;
  }

  buffer += "<table id=\"";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "-standings-table\" class=\"table table-condensed table-bordered table-striped table-hover tablesorter\">\r\n	<thead>\r\n		<th>Rank</th>\r\n		<th>Name</th>\r\n		<th>W</th>\r\n		<th>L</th>\r\n		<th>T</th>\r\n		<th>Pts</th>\r\n		<th>GF</th>\r\n		<th>GA</th>\r\n		<th>GD</th>\r\n		<th>Shutouts</th>\r\n	</thead>\r\n	<tbody>\r\n		";
  stack1 = helpers.each.call(depth0, depth0.standings, {hash:{},inverse:self.noop,fn:self.program(1, program1, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n	</tbody>\r\n</table>\r\n<div id=\"";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "-team-schedule\"></div>";
  return buffer;
  }));

Handlebars.registerPartial("statistics", Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [2,'>= 1.0.0-rc.3'];
helpers = helpers || Handlebars.helpers; data = data || {};
  var buffer = "", stack1, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n					<tr>\r\n						<td>";
  if (stack1 = helpers.rank) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.rank; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n						<td>";
  if (stack1 = helpers.player) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.player; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n						<td>";
  if (stack1 = helpers.team) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.team; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n						<td>";
  if (stack1 = helpers.goals) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.goals; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n					</tr>\r\n				";
  return buffer;
  }

function program3(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n					<tr>\r\n						<td>";
  if (stack1 = helpers.player) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.player; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n						<td>";
  if (stack1 = helpers.team) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.team; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n						<td>";
  if (stack1 = helpers.shutouts) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.shutouts; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n						<td>";
  if (stack1 = helpers.goalsAgainst) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.goalsAgainst; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n					</tr>\r\n				";
  return buffer;
  }

  buffer += "<div class=\"row-fluid\">\r\n    <div class=\"span6\">\r\n		<table id=\"";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "-player-statistics-table\" class=\"table table-condensed table-bordered table-striped table-hover\">\r\n			<thead>\r\n				<th>Rank</th>\r\n				<th>Top Scorers</th>\r\n				<th>Team</th>\r\n				<th>Goals</th>\r\n			</thead>\r\n			<tbody>\r\n				";
  stack1 = helpers.each.call(depth0, depth0.playerStatistics, {hash:{},inverse:self.noop,fn:self.program(1, program1, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n			</tbody>\r\n		</table>\r\n	</div>\r\n	<div class=\"span6\">\r\n		<table id=\"";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "-goalie-statistics-table\" class=\"table table-condensed table-bordered table-striped table-hover\">\r\n			<thead>\r\n				<th>Goalie</th>\r\n				<th>Team</th>\r\n				<th>Shutouts</th>\r\n				<th>Goals Against</th>\r\n			</thead>\r\n			<tbody>\r\n				";
  stack1 = helpers.each.call(depth0, depth0.goalieStatistics, {hash:{},inverse:self.noop,fn:self.program(3, program3, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n			</tbody>\r\n		</table>\r\n	</div>\r\n</div>";
  return buffer;
  }));

this["comp"]["web/templates/admin.html"] = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [2,'>= 1.0.0-rc.3'];
helpers = helpers || Handlebars.helpers; partials = partials || Handlebars.partials; data = data || {};
  var buffer = "", stack1, self=this;


  buffer += "<div class=\"span3\">\r\n  <div class=\"well sidebar-nav\">\r\n    <ul class=\"nav nav-list\">\r\n      <li class=\"nav-header\">Tables</li>\r\n      <li class=\"active\"><a href=\"#scores\" data-toggle=\"tab\">Scores</a></li>\r\n      <li class=\"nav-header\">Cache</li>\r\n      <li><a href=\"#cache\" data-toggle=\"tab\">Reset</a></li>\r\n    </ul>\r\n  </div><!--/.well -->\r\n</div>\r\n<div class=\"span9\">\r\n    <div class=\"tab-content\">\r\n        <div class=\"tab-pane active\" id=\"scores\">\r\n            ";
  stack1 = self.invokePartial(partials.games, 'games', depth0, helpers, partials, data);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n        </div>\r\n        <div class=\"tab-pane\" id=\"cache\">\r\n            ";
  stack1 = self.invokePartial(partials.cache, 'cache', depth0, helpers, partials, data);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n        </div>\r\n    </div>\r\n</div>";
  return buffer;
  });

this["comp"]["web/templates/comp.html"] = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [2,'>= 1.0.0-rc.3'];
helpers = helpers || Handlebars.helpers; partials = partials || Handlebars.partials; data = data || {};
  var buffer = "", stack1, self=this, functionType="function", escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n  <li ";
  stack1 = helpers.unless.call(depth0, data.index, {hash:{},inverse:self.noop,fn:self.program(2, program2, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "><a href=\"#season-";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" data-toggle=\"tab\">";
  if (stack1 = helpers.name) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.name; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</a></li>\r\n  ";
  return buffer;
  }
function program2(depth0,data) {
  
  
  return "class='active'";
  }

function program4(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n  <div class=\"tab-pane ";
  stack1 = helpers.unless.call(depth0, data.index, {hash:{},inverse:self.noop,fn:self.program(5, program5, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\" id=\"season-";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\">\r\n    <ul class=\"nav nav-pills\">\r\n      <li class=\"active\"><a href=\"#standings-";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" data-toggle=\"pill\">Standings</a></li>\r\n      <li><a href=\"#schedule-";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" data-toggle=\"pill\">Schedule</a></li>\r\n      <li><a href=\"#statistics-";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" data-toggle=\"pill\">Statistics</a></li>\r\n      <li><a href=\"#playoffs-";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" data-toggle=\"pill\">Playoffs</a></li>\r\n    </ul>\r\n    <div class=\"pill-content\">\r\n      <div class=\"pill-pane active\" id=\"standings-";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\">";
  stack1 = self.invokePartial(partials.standings, 'standings', depth0, helpers, partials, data);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "</div>\r\n      <div class=\"pill-pane\" id=\"schedule-";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\">";
  stack1 = self.invokePartial(partials.league_schedule, 'league_schedule', depth0, helpers, partials, data);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "</div>\r\n      <div class=\"pill-pane\" id=\"statistics-";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\">";
  stack1 = self.invokePartial(partials.statistics, 'statistics', depth0, helpers, partials, data);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "</div>\r\n      <div class=\"pill-pane\" id=\"playoffs-";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\">";
  stack1 = self.invokePartial(partials.playoff_schedule, 'playoff_schedule', depth0, helpers, partials, data);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "</div>\r\n    </div>\r\n  </div>\r\n  ";
  return buffer;
  }
function program5(depth0,data) {
  
  
  return "active";
  }

  buffer += "<ul class=\"nav nav-tabs\">\r\n  ";
  stack1 = helpers.each.call(depth0, depth0.seasons, {hash:{},inverse:self.noop,fn:self.program(1, program1, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n  <li><a href=\"#all-time\" data-toggle=\"tab\">All Time Stats</a></li>\r\n</ul>\r\n<div class=\"tab-content\">\r\n  ";
  stack1 = helpers.each.call(depth0, depth0.seasons, {hash:{},inverse:self.noop,fn:self.program(4, program4, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n  ";
  stack1 = self.invokePartial(partials.alltime, 'alltime', depth0, helpers, partials, data);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n</div>";
  return buffer;
  });

this["comp"]["web/templates/schedule.html"] = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [2,'>= 1.0.0-rc.3'];
helpers = helpers || Handlebars.helpers; data = data || {};
  var buffer = "", stack1, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n			<tr id=\"";
  if (stack1 = helpers.gameId) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.gameId; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" class=\"selectable\">\r\n				<td>";
  if (stack1 = helpers.home) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.home; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.away) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.away; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td>";
  if (stack1 = helpers.played) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.played; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n				<td><span class=\"";
  if (stack1 = helpers.homeId) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.homeId; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "-score\">";
  if (stack1 = helpers.homeScore) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.homeScore; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</span> - <span class=\"";
  if (stack1 = helpers.awayId) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.awayId; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "-score\">";
  if (stack1 = helpers.awayScore) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.awayScore; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</span></td>\r\n			</tr>\r\n		";
  return buffer;
  }

  buffer += "<table id=\"game-schedule-table\" class=\"table table-condensed table-bordered table-striped table-hover tablesorter\">\r\n	<thead>\r\n		<th>Home</th>\r\n		<th>Away</th>\r\n		<th>Date</th>\r\n		<th>Result</th>\r\n	</thead>\r\n	<tbody>\r\n		";
  stack1 = helpers.each.call(depth0, depth0, {hash:{},inverse:self.noop,fn:self.program(1, program1, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n	</tbody>\r\n</table>";
  return buffer;
  });

this["comp"]["web/templates/scores.html"] = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [2,'>= 1.0.0-rc.3'];
helpers = helpers || Handlebars.helpers; data = data || {};
  var buffer = "", stack1, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data,depth1) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n          <tr>\r\n            <td><button class=\"btn btn-mini btn-success add-goal\" data-game-id=\""
    + escapeExpression(((stack1 = depth1.gameId),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\" data-player-id=\"";
  if (stack2 = helpers.playerId) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = depth0.playerId; stack2 = typeof stack2 === functionType ? stack2.apply(depth0) : stack2; }
  buffer += escapeExpression(stack2)
    + "\" data-team-id=\"";
  if (stack2 = helpers.teamId) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = depth0.teamId; stack2 = typeof stack2 === functionType ? stack2.apply(depth0) : stack2; }
  buffer += escapeExpression(stack2)
    + "\"><i class=\"icon-plus icon-white\"></i></button> <strong id=\"";
  if (stack2 = helpers.playerId) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = depth0.playerId; stack2 = typeof stack2 === functionType ? stack2.apply(depth0) : stack2; }
  buffer += escapeExpression(stack2)
    + "-goals\">";
  if (stack2 = helpers.goals) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = depth0.goals; stack2 = typeof stack2 === functionType ? stack2.apply(depth0) : stack2; }
  buffer += escapeExpression(stack2)
    + "</strong> <button class=\"btn btn-mini btn-danger remove-goal\" data-game-id=\""
    + escapeExpression(((stack1 = depth1.gameId),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\" data-player-id=\"";
  if (stack2 = helpers.playerId) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = depth0.playerId; stack2 = typeof stack2 === functionType ? stack2.apply(depth0) : stack2; }
  buffer += escapeExpression(stack2)
    + "\" data-team-id=\"";
  if (stack2 = helpers.teamId) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = depth0.teamId; stack2 = typeof stack2 === functionType ? stack2.apply(depth0) : stack2; }
  buffer += escapeExpression(stack2)
    + "\"><i class=\"icon-minus icon-white\"></i></button></td>\r\n            <td>";
  if (stack2 = helpers.name) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = depth0.name; stack2 = typeof stack2 === functionType ? stack2.apply(depth0) : stack2; }
  buffer += escapeExpression(stack2)
    + "</td>\r\n          </tr>\r\n        ";
  return buffer;
  }

  buffer += "<div class=\"row-fluid\">\r\n  <div class=\"span6\">\r\n    <h4>";
  if (stack1 = helpers.home) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.home; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + " (<span class=\"";
  if (stack1 = helpers.homeId) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.homeId; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "-score\">";
  if (stack1 = helpers.homeScore) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.homeScore; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</span>)</h4>\r\n    <table id=\"home-players-table\" class=\"table table-condensed table-bordered table-striped table-hover\">\r\n      <thead>\r\n        <th width=\"90px\">Goals</th>\r\n        <th>Player</th>\r\n      </thead>\r\n      <tbody>\r\n        ";
  stack1 = helpers.each.call(depth0, depth0.homePlayers, {hash:{},inverse:self.noop,fn:self.programWithDepth(program1, data, depth0),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n      </tbody>\r\n    </table>\r\n  </div>\r\n  <div class=\"span6\">\r\n    <h4>";
  if (stack1 = helpers.away) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.away; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + " (<span class=\"";
  if (stack1 = helpers.awayId) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.awayId; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "-score\">";
  if (stack1 = helpers.awayScore) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.awayScore; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</span>)</h4>\r\n    <table id=\"away-players-table\" class=\"table table-condensed table-bordered table-striped table-hover\">\r\n      <thead>\r\n        <th width=\"90px\">Goals</th>\r\n        <th>Player</th>\r\n      </thead>\r\n      <tbody>\r\n        ";
  stack1 = helpers.each.call(depth0, depth0.awayPlayers, {hash:{},inverse:self.noop,fn:self.programWithDepth(program1, data, depth0),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n      </tbody>\r\n    </table>\r\n  </div>\r\n</div>";
  return buffer;
  });

this["comp"]["web/templates/team_schedule.html"] = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [2,'>= 1.0.0-rc.3'];
helpers = helpers || Handlebars.helpers; data = data || {};
  var buffer = "", stack1, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n					<tr id=\"";
  if (stack1 = helpers.gameId) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.gameId; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" class=\"";
  if (stack1 = helpers.result) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.result; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + " selectable\">\r\n						<td>";
  if (stack1 = helpers.home) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.home; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n						<td>";
  if (stack1 = helpers.away) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.away; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n						<td>";
  if (stack1 = helpers.played) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.played; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n						<td>";
  if (stack1 = helpers.score) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.score; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n					</tr>\r\n				";
  return buffer;
  }

function program3(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n					<tr>\r\n						<td width=\"20px\">\r\n						";
  stack1 = helpers['if'].call(depth0, depth0.goalie, {hash:{},inverse:self.noop,fn:self.program(4, program4, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n						";
  stack1 = helpers['if'].call(depth0, depth0.captain, {hash:{},inverse:self.noop,fn:self.program(6, program6, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n						";
  stack1 = helpers['if'].call(depth0, depth0.coCaptain, {hash:{},inverse:self.noop,fn:self.program(8, program8, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n						</td>\r\n						<td>";
  if (stack1 = helpers.player) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.player; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n						<td>";
  if (stack1 = helpers.goals) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.goals; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "</td>\r\n					</tr>\r\n				";
  return buffer;
  }
function program4(depth0,data) {
  
  
  return "\r\n							<img src=\"img/goalie.png\"/>\r\n						";
  }

function program6(depth0,data) {
  
  
  return "\r\n							<img src=\"img/captain.png\"/>\r\n						";
  }

function program8(depth0,data) {
  
  
  return "\r\n							<img src=\"img/cocaptain.png\"/>\r\n						";
  }

  buffer += "<div class=\"row-fluid\">\r\n    <div class=\"span8\">\r\n    	<table id=\"";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "-team-schedule-table\" class=\"table table-condensed table-bordered table-striped table-hover tablesorter\">\r\n			<thead>\r\n				<th>Home</th>\r\n				<th>Away</th>\r\n				<th>Date</th>\r\n				<th>Result</th>\r\n			</thead>\r\n			<tbody>\r\n				";
  stack1 = helpers.each.call(depth0, depth0.schedule, {hash:{},inverse:self.noop,fn:self.program(1, program1, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n			</tbody>\r\n		</table>\r\n		<div id='";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "-goal-summary'></div>\r\n    </div>\r\n    <div class=\"span4\">\r\n    	<table id=\"";
  if (stack1 = helpers.id) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = depth0.id; stack1 = typeof stack1 === functionType ? stack1.apply(depth0) : stack1; }
  buffer += escapeExpression(stack1)
    + "-team-roster-table\" class=\"table table-condensed table-bordered table-striped table-hover tablesorter\">\r\n			<thead>\r\n				<th colspan=2>Roster</th>\r\n				<th>Goals</th>\r\n			</thead>\r\n			<tbody>\r\n				";
  stack1 = helpers.each.call(depth0, depth0.roster, {hash:{},inverse:self.noop,fn:self.program(3, program3, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n			</tbody>\r\n		</table>\r\n    </div>\r\n</div>\r\n";
  return buffer;
  });