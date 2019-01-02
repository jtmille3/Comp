/**
 * Record shutouts
 */

define(function (require) {
    'use strict';

    var d3 = require("../components/d3/d3");

    return {
        getGoalData: function (player, competitive) {
            var data = [];

            var goals = [];
            Lazy(competitive.goals).each(function (goal) {
                if (goal && goal.player === player.name) {
                    goals.push(goal);
                }
            });

            var seasons = [];
            Lazy(competitive.seasons).each(function (season) {
                var found = Lazy(season.playerStatistics).where({ id: player.id });
                if (found.size()) {
                    seasons.push(season);
                }
            });

            Lazy(seasons).each(function (season, j) {
                if (!data[j]) {
                    var name = season.name.split(' ')[1].substring(0, 1) + season.name.split(' ')[0].substring(2,4);
                    data[j] = {
                        name: name,
                        goals: 0
                    };
                }

                Lazy(season.leagueSchedule).each(function (game) {
                    var games = Lazy(goals).where({ gameId: game.id });
                    if (games.size()) {
                        data[j].goals += games.size();
                    }
                });

                Lazy(season.playoffSchedule).each(function (game) {
                    var games = Lazy(goals).where({ gameId: game.id });
                    if (games.size()) {
                        data[j].goals += games.size();
                    }
                });
            });

            return Lazy(data).reverse().toArray();
        },
        getShutoutData: function (player, competitive) {
            var data = [];

            var seasons = [];
            Lazy(competitive.seasons).each(function (season) {
                var found = Lazy(season.playerStatistics).where({ id: player.id });
                if (found.size()) {
                    seasons.push(season);
                }
            });

            Lazy(seasons).each(function (season, j) {
                if (!data[j]) {
                    var name = season.name.split(' ')[1].substring(0, 2) + season.name.split(' ')[0];
                    data[j] = {
                        name: name,
                        shutouts: 0
                    };
                }

                Lazy(season.standings).each(function (team) {
                    Lazy(season.playerStatistics).each(function (p2) {
                        if (team.teamId === p2.teamId && p2.id === player.id) {
                            data[j].shutouts = team.shutouts;
                        }
                    });
                });
            });

            return Lazy(data).reverse().toArray();
        },
        generate: function (player, competitive) {
            var shutoutData = this.getShutoutData(player, competitive);
            var goalData = this.getGoalData(player, competitive);

            var max = Lazy(goalData).max(function (d) {
                return d.goals;
            });

            var ticks = Math.min(max.goals, 10);

            var maxDataPointsForDots = 50;

            var margin = 20,
                w = 900 - margin * 2,
                h = 400 - margin * 2;
            var pointRadius = 5;

            var x = d3.scale.ordinal().rangeRoundBands([0, w - margin * 2], 1).domain(goalData.map(function (d) {
                return d.name;
            }));

            var y = d3.scale.linear().range([h - margin * 2, 0]).domain([0, d3.max(goalData, function (d) {
                return d.goals;
            })]);

            var xAxis = d3.svg.axis().scale(x).tickSize(h - margin * 2).tickPadding(10).ticks(goalData.length);

            var yAxis = d3.svg.axis().scale(y).orient('left').ticks(ticks).tickSize(-w + margin * 2).tickPadding(10).tickFormat(d3.format(".0f"));

            var svg = d3.select('#goals-per-season')
                .attr('width', w)
                .attr('height', h)
                .attr('class', 'viz')
                .append('svg:g')
                .attr('transform', 'translate(' + margin + ',' + margin + ')');

            // y ticks and labels
            svg.append('svg:g')
                .attr('class', 'yTick')
                .call(yAxis);

            // x ticks and labels
            svg.append('svg:g')
                .attr('class', 'xTick')
                .call(xAxis);

            var dataLines = svg.append('svg:g')
                .selectAll('.data-line')
                .data([goalData]);

            var line = d3.svg.line()
                .x(function (d) {
                    // verbose logging to show what's actually being done
                    return x(d.name);
                })
                .y(function (d) {
                    // verbose logging to show what's actually being done
                    return y(d.goals);
                })
                .interpolate("linear");

            dataLines
                .enter()
                .append('path')
                .attr('class', 'data-line')
                .style('opacity', 0.7)
                .attr("d", line(goalData));

            // Draw the points
            svg.append('svg:g')
                .selectAll('.data-point')
                .data(goalData)
                .enter()
                .append('svg:circle')
                .attr('class', 'data-point')
                .attr('cx', function (d) {
                    return x(d.name)
                })
                .attr('cy', function (d) {
                    return y(d.goals)
                })
                .attr('r', function () {
                    return (goalData.length <= maxDataPointsForDots) ? pointRadius : 0;
                });

            $('svg circle').tipsy({
                gravity: 's',
                html: true,
                title: function() {
                    var d = this.__data__;
                    return d.goals + ' goals';
                }
            });
        }
    }
        ;
})
;