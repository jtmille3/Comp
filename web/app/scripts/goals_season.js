/**
 * Record shutouts
 */

define(function (require) {
    'use strict';

    var d3 = require("../components/d3/d3");

    return {
        getGoalData: function (player, competitive) {
            var toReturn = {};
            var data = [];        // overall, regular season plus playoff
            var seasonData = [];  // regular season only
            var playoffData = []; // playoff goals only

            var goals = [];
            Lazy(competitive.goals).each(function (goal) {
                if (goal && goal.player === player.name) {
                    goals.push(goal);
                }
            });

            var seasons = [];
            Lazy(competitive.seasons).each(function (season) {
                var found = Lazy(season.playerStatistics).where({ name: player.name });
                if (found.size()) {
                    seasons.push(season);
                }
            });

            Lazy(seasons).each(function (season, j) {
                var name = season.name.split(' ')[1].substring(0, 1) + season.name.split(' ')[0].substring(2,4);
                if (!data[j]) {
                    data[j] = {
                        name: name,
                        goals: 0
                    };
                }
                if (!seasonData[j]) {
                    seasonData[j] = {
                        name: name,
                        goals: 0
                    };
                }
                if (!playoffData[j]) {
                    playoffData[j] = {
                        name: name,
                        goals: 0
                    };
                }

                Lazy(season.leagueSchedule).each(function (game) {
                    var games = Lazy(goals).where({ gameId: game.id });
                    if (games.size()) {
                        data[j].goals += games.size();
                        seasonData[j].goals += games.size();
                    }
                });

                Lazy(season.playoffSchedule).each(function (game) {
                    var games = Lazy(goals).where({ gameId: game.id });
                    if (games.size()) {
                        data[j].goals += games.size();
                        playoffData[j].goals += games.size();
                    }
                });
            });
            
            var overall = Lazy(data).reverse().toArray();
            var season  = Lazy(seasonData).reverse().toArray();
            var playoff = Lazy(playoffData).reverse().toArray();
            toReturn.overall = overall;
            toReturn.season = season;
            toReturn.playoff = playoff;

            return toReturn;
        },
        getShutoutData: function (player, competitive) {
            var data = [];

            var seasons = [];
            Lazy(competitive.seasons).each(function (season) {
                var found = Lazy(season.playerStatistics).where({ name: player.name });
                if (found.size()) {
                    seasons.push(season);
                }
            });

            Lazy(seasons).each(function (season, j) {
                if (!data[j]) {
                    var name = season.name.split(' ')[1].substring(0, 1) + season.name.split(' ')[0].substring(2,4);
                    data[j] = {
                        name: name,
                        shutouts: 0
                    };
                }

                Lazy(season.standings).each(function (team) {
                    Lazy(season.playerStatistics).each(function (p2) {
                        if (team.teamId === p2.teamId && p2.name === player.name) {
                            data[j].shutouts = team.shutouts;
                        }
                    });
                });
            });

            return Lazy(data).reverse().toArray();
        },
        getLeaguePlayoffWinnerData: function(player, competitive) {
            var data = [];
            var seasons = [];
            var leagueWinners = {};
            var playoffWinners = {};
            var teamIds = new Set();
            Lazy(competitive.seasons).each(function (season) {
                var leagueWinner = season.standings.filter(function(standing){ return standing.leagueWinner == 1});
                var playoffWinner = season.standings.filter(function(standing){ return standing.playoffWinner == 1});
                // short season name, F18 for example, determined by parsing long season name, Fall 2018.
                var sname = season.name.split(' ')[1].substring(0, 1) + season.name.split(' ')[0].substring(2,4);
                var playerteams = season.playerStatistics.filter(function(p) {return p.name == player.name});
                var playerteam = null;
                if( playerteams.length > 0 ) {
                    playerteam = playerteams.pop();
                    teamIds.add(playerteam.teamId);
                }
                if( leagueWinner.length > 0 ) {
                    leagueWinners[sname] = leagueWinner.pop();
                }
                if( playoffWinner.length > 0 ) {
                    playoffWinners[sname] = playoffWinner.pop();
                }
                var found = Lazy(season.playerStatistics).where({ name: player.name });
                if (found.size()) {
                    seasons.push(season);
                }
            });
            
            Lazy(seasons).each(function (season, j) {
                if (!data[j]) {
                    var name = season.name.split(' ')[1].substring(0, 1) + season.name.split(' ')[0].substring(2,4);
                    var playerWonLeague = false;
                    var playerWonPlayoff = false;
                    var leagueTeam = null;
                    var playoffTeam = null;
                    var playerTopScorerSearch = season.playerStatistics.filter(function(p){ return p.rank == 1 && p.name == player.name && p.goals > 0 });
                    var playerTopScorer = false;
                    if( playerTopScorerSearch.length > 0 ) {
                        playerTopScorer = true;
                    }
                    if( leagueWinners.hasOwnProperty(name) ) {
                        var leagueWinner = leagueWinners[name];
                        if( teamIds.has(leagueWinner.teamId) ) {
                            playerWonLeague = true;
                            leagueTeam = leagueWinner.team;
                        }
                    }
                    if( playoffWinners.hasOwnProperty(name) ) {
                        var playoffWinner = playoffWinners[name];
                        if( teamIds.has(playoffWinner.teamId) ) {
                            playerWonPlayoff = true;
                            playoffTeam = playoffWinner.team;
                        }
                    }
                    data[j] = {
                           name: name,
                        playerWonLeague: playerWonLeague,
                        playerWonPlayoff: playerWonPlayoff,
                        leagueTeam: leagueTeam,
                        playoffTeam: playoffTeam,
                        playerTopScorer: playerTopScorer
                    };
                }
            });
            
            return Lazy(data).reverse().toArray();
        
        },
        generate: function (player, competitive) {
            var shutoutData = this.getShutoutData(player, competitive);
            var goalDataObj = this.getGoalData(player, competitive);
            var goalData = goalDataObj.overall;
            var bardata = [];
            for( var i = 0 ; i < goalDataObj.overall.length ; i++ ) {
                // depends on same number of seasons in overall as season and playoff (should always be the case)
                var name = goalDataObj.overall[i].name;
                var seasonGoals = goalDataObj.season[i].goals;
                var playoffGoals = goalDataObj.playoff[i].goals;
                bardata.push({name: name, season: seasonGoals, playoff: playoffGoals});
            }
            //console.log('bardata');
            //console.log(bardata);

            // Transpose the data into layers
            var dataset = d3.layout.stack()(["season", "playoff"].map(function(type) {
              return bardata.map(function(d) {
                return {x: d.name, y: +d[type], type: type};
              });
            }));
            //console.log('dataset');
            //console.log(dataset);

            var max = Lazy(goalData).max(function (d) {
                return d.goals;
            });

            var ticks = Math.min(max.goals, 10);
            var pointRadius = 5;
            var maxDataPointsForDots = 50;
            
            var margin = {top: 35, right: 30, bottom: 35, left: 30};
            //var margin = {top: 20, right: 20, bottom: 20, left: 20};

            var width = 900 - margin.left - margin.right,
                height = 400 - margin.top - margin.bottom;

            var svg = d3.select("#goals-per-season")
              .attr("width", width + margin.left + margin.right)
              .attr("height", height + margin.top + margin.bottom)
              .append("g")
              .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
              //.attr("style", "outline: thin solid red;")
              ;

            // Set x, y and colors
            var x = d3.scale.ordinal()
              .domain(dataset[0].map(function(d) { return d.x; }))
              .rangeRoundBands([10, width-10], 0.65);

            var y = d3.scale.linear()
              .domain([0, d3.max(dataset, function(d) { return d3.max(d, function(d) { return d.y0 + d.y; });  })])
              .range([height, 0]);

            var colors = ["#3399ff", "#00539B"];

            // Define and draw axes
            var yAxis = d3.svg.axis()
              .scale(y)
              .orient("left")
              .ticks(ticks)
              .tickSize(-width, 0, 0)
              .tickPadding(10)
              .tickFormat( function(d) { return d } );

            var xAxis = d3.svg.axis()
              .scale(x)
              .orient("bottom")
              .tickPadding(10)
              .tickSize(-height, 0, 0);

            svg.append("g")
              .attr("class", "y axis")
              .call(yAxis);

            svg.append("g")
              .attr("class", "x axis")
              .attr("transform", "translate(0," + height + ")")
              .call(xAxis);


            // Create groups for each series, rects for each segment
            var groups = svg.selectAll("g.goals")
              .data(dataset)
              .enter().append("g")
              .attr("class", "goals")
              .style("fill", function(d, i) { return colors[i]; })
            ;

            var rect = groups.selectAll("rect")
              .data(function(d) { return d; })
              .enter()
              .append("rect")
              .attr("x", function(d) { return x(d.x); })
              .attr("y", function(d) { return y(d.y0 + d.y); })
              .attr("height", function(d) { return y(d.y0) - y(d.y0 + d.y); })
              .attr("width", x.rangeBand())
              .append("svg:title")
                .text(function(d, i) {
                  var type = d.type;
                  return d.y + ' ' + type + ' goals'; 
                })
              ;

            svg.append('svg:g')
                .selectAll('.data-point')
                .data(goalData)
                .enter()
                .append('svg:circle')
                .attr('class', 'data-point')
                .attr('cx', function (d) {
                    return x(d.name)+x.rangeBand()/2
                })
                .attr('cy', function (d) {
                    return y(d.goals)
                })
                .attr('r', function () {
                    return (goalData.length <= maxDataPointsForDots) ? pointRadius : 0;
                })
                .append("svg:title")
                    .text(function(d, i) { 
                        return d.goals+' total goals'; 
                    })
                ;

            var winData = this.getLeaguePlayoffWinnerData(player, competitive);
            var playerTopScorer = winData.filter(function(dat){return dat.playerTopScorer});
            var leagueTitles = winData.filter(function(dat){return dat.playerWonLeague});
            var playoffTitles = winData.filter(function(dat){return dat.playerWonPlayoff});
            
            // important.  cannot position icons until the graph is rendered and we cannot determine position in advance, 
            // so use callback when modal is shown.
            $("#playerDialog").on('shown.bs.modal', function() {
                for (var i = 0; i < leagueTitles.length; i++) {
                    var s = leagueTitles[i];
                    var id = 'lt'+s.name;
                    $("div.modal-body").append('<i id="'+id+'" class="fas fa-award" style="color: #3399ff;" title="League Winner\n' + s.leagueTeam + '"></i>');
                    var offset=$( "text:contains('" + s.name + "')" ).offset();
                    offset.left = offset.left + 0;
                    offset.top = offset.top + 15;
                    $('#'+id).offset(offset);
                }
                for (var i = 0; i < playoffTitles.length; i++) {
                    var s = playoffTitles[i];
                    var id = 'pt'+s.name;
                    $("div.modal-body").append('<i id="'+id+'" class="fas fa-trophy" style="color: #ffcc00;" title="Playoff Winner\n' + s.playoffTeam + '"></i>');
                    var offset=$( "text:contains('" + s.name + "')" ).offset();
                    offset.left = offset.left + 10;
                    offset.top = offset.top + 15;
                    $('#'+id).offset(offset);
                }
                for (var i = 0; i < playerTopScorer.length; i++) {
                    var s = playerTopScorer[i];
                    var id = 'ts'+s.name;
                    $("div.modal-body").append('<i id="'+id+'" class="fas fa-futbol" title="Top Scorer"></i>');
                    var offset=$( "text:contains('" + s.name + "')" ).offset();
                    offset.left = offset.left + 5;
                    offset.top = offset.top + 30;
                    $('#'+id).offset(offset);
                }
            });

        }
    }
    ;
})
;
