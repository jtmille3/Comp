define(function (require) {
    'use strict';

    return {
        generate: function (id, games) {
            var root = this.transformToBracket(games);
            var selector = '#' + id;
            var width = $(window).width() - 70;
            var size = { width: width, height: 700 };
            var tree = d3.layout.tree()
                .size([size.height, size.width])
                .children(function (d) {
                    return (!d.contents || d.contents.length === 0) ? null : d.contents;
                });

            var nodes = tree.nodes(root);
            var maxDepth = 0;
            nodes.forEach(function (d) {
                maxDepth = Math.max(d.depth, maxDepth);
            });
            var widthOffset = width - ((maxDepth + 1) * 200);
            nodes.forEach(function (d) {
                d.y = size.width - (d.depth * 200) - widthOffset;
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
                .attr("class", function (d) {
                    if (d.champion) {
                        return "node champion";
                    } else if (d.won) {
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
                    if (d.champion) {
                        return d.name.length > 18 ? d.name.substring(0, 15) + "..." : d.name;
                    } else {
                        // return d.score !== undefined ? (d.name.length > 3 ? d.name.substring(0, 3) + "..." : d.name + " ") + d.date.substring(0,10) + " (" + d.score + ")" : (d.name.length > 3 ? d.name.substring(0, 3) + "..." : d.name + " ");
                        return d.score !== undefined ? (d.name.length > 15 ? d.name.substring(0, 12) + "..." : d.name + " ") + "(" + d.score + ")" : d.name;
                    }
                });
        },
        transformToBracket: function (games) {
            // start from the newest and build our tree from it.
            var links = Lazy(games).sortBy(function (game) {
                return game.played;
            }).reverse().toArray();

            if (links.length > 0) {
                return this.buildTree(links);
            }

            return {
                name: "Playoffs",
                contents: []
            };
        },
        buildTree: function (links) {
            var root = {
                name: "Root",
                id: "root",
                contents: []
            };

            var nodes = [];
            for(var i = 0; i < links.length; i++) {
                var game = links[i]; // found home game remove it
                nodes = nodes.concat(this.getContents(game));
            }

            var date = null;
            var siblings = [];
            var j = nodes.length;
            while(j--) {
                var node = nodes.splice(j, 1)[0];

                if(!date) {
                    date = node.date.substring(0, 10);
                }

                if(date !== node.date.substring(0, 10)) {
                    this.attach(root, siblings);
                    date = node.date.substring(0, 10);
                    siblings = [];
                }

                siblings.push(node);
            }

            this.attach(root, siblings);

            if(root.contents.length > 2) {
                root.contents = root.contents.splice(root.contents.length - 2, root.contents.length);
                root.name = root.contents[0].won ? root.contents[0].name : root.contents[1].name;
                root.champion = true;
            }
            return root;
        },
        attach: function(root, siblings) {
            var i = siblings.length;
            var loser = null;
            while(i--) {
                var sibling = siblings.splice(i, 1)[0];

                var j = root.contents.length;
                while(j--) {
                    var child = root.contents[j];
                    if(child.id === sibling.id && child.won) {
                        root.contents.splice(j, 1);  // only splicing the winner
                        root.contents.push(sibling);
                        sibling.contents.push(child, child.played);
                        loser = child.played;
                        break;
                    }
                }

                if(!loser) { // not losers but new roots
                    root.contents.push(sibling);
                }
//                else { // need to splice loser too
//                    var k = root.contents.length;
//                    while(k--) {
//                        var node = root.contents[k];
//                        if(node.id === loser.id) {
//                            root.contents.splice(k, 1);
//                            break;
//                        }
//                    }
//                }

                loser = null;
            }
        },
        getContents: function(game) {
            var home = {
                "name": game.home,
                "id": game.homeId,
                "date": game.played,
                "score": game.homeScore,
                "won": game.homeScore > game.awayScore,
                "contents": []
            };

            var away = {
                "name": game.away,
                "id": game.awayId,
                "date": game.played,
                "score": game.awayScore,
                "won": game.awayScore > game.homeScore,
                "contents": []
            };

            away.played = home;
            home.played = away;

            return [ home, away ];
        }
    };
});