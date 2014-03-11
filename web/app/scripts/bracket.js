define(function (require) {
    'use strict';

    return {
        generate: function (id, games) {
            var root = this.transformToBracket(games);
            var selector = '#' + id;
            var width = $( window ).width() - 70;
            var size = { width: width, height: 700};
            var tree = d3.layout.tree()
                .size([size.height, size.width])
                .children(function (d) {
                    return (!d.contents || d.contents.length === 0) ? null : d.contents;
                });

            var nodes = tree.nodes(root);
            nodes.forEach(function (d) {
                d.y = size.width - (d.depth * 200);
            });

            var links = tree.links(nodes);

            var layoutRoot = d3.select(selector)
                .append("svg")
                .attr("width", size.width)
                .attr("height", size.height)
                .append("g")
                .attr("class", "container")
                .attr("transform", "translate(-100,0)");

            // Edges between nodes as a <path class="link" />
            var link = d3.svg.diagonal()
                .projection(function (d) {
                    return [d.y, d.x];
                });

            layoutRoot.selectAll("path.link")
                .data(links)
                .enter()
                .append("path")
                .attr("class", "link")
                .attr("d", link);

            var nodeGroup = layoutRoot.selectAll("g.node")
                .data(nodes)
                .enter()
                .append("g")
                .attr("class", "node")
                .attr("transform", function (d) {
                    return "translate(" + (d.y - 75) + "," + (d.x - 15) + ")";
                });

            nodeGroup.append("rect")
                .attr("class", function(d) {
                    if(d.champion) {
                        return "node champion";
                    } else if(d.winner) {
                        return "node winner";
                    } else {
                        return "node loser";
                    }
                })
                .attr("width", 150)
                .attr("height", 30)
                .attr("rx", 5)
                .attr("ry", 5);

            nodeGroup.append("text")
                .attr("text-anchor", function (d) {
                    // return d.children ? "end" : "start";
                    return "end";
                })
                .attr("dx", 140)
                .attr("dy", 20)
                .text(function (d) {
                    if(d.champion) {
                        return d.name.length > 18 ? d.name.substring(0, 15) + "..." : d.name;
                    } else {
                        return d.score !== undefined ? (d.name.length > 15 ? d.name.substring(0, 12) + "..." : d.name + " ") + "(" + d.score + ")" : d.name;
                    }
                });
        },
        transformToBracket: function (playoffs) {
            // start from the newest and build our tree from it.
            var games = Lazy(playoffs).sortBy(function (game) {
                return game.played;
            }).toArray();

            if (games.length > 0) {
                var game = games[games.length - 1];
                games.splice(games.length - 1, 1);

                var champion = game.homeScore > game.awayScore ? game.home : game.away;

                var root = {
                    "name": champion,
                    "champion": true,
                    "contents": [
                        {
                            "name": game.home,
                            "id": game.homeId,
                            "score": game.homeScore,
                            "winner": game.homeScore > game.awayScore,
                            "contents": []
                        },
                        {
                            "name": game.away,
                            "id": game.awayId,
                            "score": game.awayScore,
                            "winner": game.awayScore > game.homeScore,
                            "contents": []
                        }
                    ]
                };

                while (games.length) {
                    this.buildTree(games, root);
                }

                return root;
            }

            return {
                name: "Playoffs",
                contents: []
            };
        },
        buildTree: function (games, root) {
            if (root.contents.length > 0) {  // recursively walk until we find no contents
                for (var i = 0; i < root.contents.length; i++) {
                    var node = root.contents[i];
                    this.buildTree(games, node);
                }
                return;
            }

            // find our game
            var i = games.length;
            while (i--) {
                var game = games[i];
                if (root.id === game.homeId || root.id === game.awayId) {
                    games.splice(i, 1); // found home game remove it
                    root.contents = [
                        {
                            "name": game.home,
                            "id": game.homeId,
                            "score": game.homeScore,
                            "winner": game.homeScore > game.awayScore,
                            "contents": []
                        },
                        {
                            "name": game.away,
                            "id": game.awayId,
                            "score": game.awayScore,
                            "winner": game.awayScore > game.homeScore,
                            "contents": []
                        }
                    ];

                    return;
                }
            }
        }
    };
});