/**
 * Record shutouts
 * Line break season name and year?
 * Change bar chart to line chart with goals and shutouts combined?
 * Refactor season database so it's name and year
 * For the player list the number of seasons played
 */

define(function (require) {
    'use strict';

    var d3 = require("../components/d3/d3");

    return {
        generate: function (player, competitive) {
            console.log(competitive);
            var data = [];

            var goals = [];
            Lazy(competitive.goals).each(function (goal) {
                if (goal && goal.player === player.name) {
                    goals.push(goal);
                }
            });

            var seasons = [];
            Lazy(competitive.seasons).each(function(season) {
                // should do this by playerId :P
                var found = Lazy(season.playerStatistics).where({ name: player.name });
                if(found.size()) {
                    seasons.push(season);
                }
            });

            Lazy(seasons).each(function (season, j) {
                if (!data[j]) {
                    data[j] = {
                        name: season.name,
                        goals: 0
                    };
                }

                Lazy(season.leagueSchedule).each(function (game) {
                    var games = Lazy(goals).where({ gameId: game.gameId });
                    if (games.size()) {
                        data[j].goals += games.size();
                    }
                });

                Lazy(season.playoffSchedule).each(function (game) {
                    var games = Lazy(goals).where({ gameId: game.gameId });
                    if (games.size()) {
                        data[j].goals += games.size();
                    }
                });
            });

            data = Lazy(data).reverse().toArray();
            var max = Lazy(data).max(function (d) {
                return d.goals;
            });

            var ticks = Math.min(max.goals, 10);

            var margin = {top: 20, right: 20, bottom: 50, left: 40},
                width = 550 - margin.left - margin.right,
                height = 300 - margin.top - margin.bottom;

            var x = d3.scale.ordinal()
                .rangeRoundBands([0, width], .1);

            var y = d3.scale.linear()
                .range([height, 0]);

            var xAxis = d3.svg.axis()
                .scale(x)
                .orient("bottom");

            var yAxis = d3.svg.axis()
                .scale(y)
                .orient("left")
                .ticks(ticks) // calculate the max
                .tickFormat(d3.format(".0f"));

            var svg = d3.select("#goals-per-season")
                .attr("width", width + margin.left + margin.right)
                .attr("height", height + margin.top + margin.bottom)
                .data(data)
                .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

            x.domain(data.map(function (d) {
                return d.name;
            }));

            y.domain([0, d3.max(data, function (d) {
                return d.goals;
            })]);

            svg.append("g")
                .attr("class", "x axis")
                .attr("transform", "translate(0," + height + ")")
                .call(xAxis)
                .selectAll("text")
                .attr("transform", "rotate(20)")
                .attr("dy", "1.3em")
                .style("text-anchor", "start");

            svg.append("g")
                .attr("class", "y axis")
                .call(yAxis);

            var yTextPadding = 20;
            svg.selectAll(".bar")
                .data(data)
                .enter().append("rect")
                .attr("class", "bar")
                .attr("x", function (d) {
                    return x(d.name);
                })
                .attr("width", x.rangeBand())
                .attr("y", function (d) {
                    return y(d.goals);
                })
                .attr("height", function (d) {
                    return height - y(d.goals);
                });
        }
    };
});