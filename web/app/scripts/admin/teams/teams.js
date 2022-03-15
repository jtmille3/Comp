/*global define */
define(function() {
    'use strict';
    return {
        render: function() {
            var self = this;

            // query teams here...
            $.get('/service/seasons', function(seasons) {
                self.seasons = seasons;

                var teamsTemplate = window.comp['web/app/scripts/admin/teams/teams.html'];
                $('#admin-content').html(teamsTemplate({
                    seasons: seasons
                }));

                self.attachHandlers();
            });
        },

        attachHandlers: function() {
            var self = this;

            $('#seasons-dropdown a').on('click', function (e) {
                var seasonId = $(this).data('seasonId');
                self.renderTeams(seasonId);

                var $div = $(this).parent().parent().parent();
                var $btn = $div.find('button');
                $btn.html($(this).text() + ' <span class="caret"></span>');
                $div.removeClass('open');

                e.preventDefault();
                return false;
            });
        },

        renderTeams: function(seasonId) {
            var self = this;

            var teamFormTemplate = window.comp['web/app/scripts/admin/teams/team_form.html'];
            $('#team-form').html(teamFormTemplate());

            $.get('/service/seasons/' + seasonId + '/teams', function(teams) {
                var teamListTemplate = window.comp['web/app/scripts/admin/teams/team_list.html'];
                $('#team-list').html(teamListTemplate({
                    teams: teams
                }));

                self.attachTeamSearch(seasonId, teams);
                self.attachTeamHandler();
                $('#team-name').focus();
            });
        },

        attachTeamSearch: function(seasonId, teams) {
            var self = this;

            var names = new Bloodhound({
                limit: 10,
                datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                local: teams,
                matcher: function(team) {
                    team.name.toUpperCase() === this.query.toUpperCase()
                }
            });

            names.initialize();

            var search = $('#team-name');

            search.on('keyup', function() {
                var text = search.val();
                var $addButton = $('#add-team-button');
                var team = Lazy(teams).find(function(team) { return team.name.toUpperCase() === text.toUpperCase(); });
                $addButton.prop('disabled', !!team);
            });

            search.typeahead({
                    minLength: 1,
                    hint: true,
                    highlight: true
                },
                {
                    name: 'team',
                    displayKey: 'name',
                    source: names.ttAdapter(),
                    templates: {
                        suggestion: Handlebars.compile('<p><strong>{{name}}</strong></p>')
                    }
                });

            var $addButton = $('#add-team-button');
            $addButton.on('click', function() {
                var search = $('#team-name');
                var name = search.val();

                var team = {
                    seasonId: seasonId,
                    name: name,
                    leagueWinner: false,
                    playoffWinner: false
                };

                $.ajax({
                    type: 'POST',
                    data: JSON.stringify(team),
                    url: '/service/teams',
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    success: function() {
                        names.add(team);
                        teams.push(team);

                        self.renderTeams(seasonId);
                    }
                });

                var $addButton = $('#add-team-button');
                $addButton.prop('disabled', true);

                search.val('');
            });
        },

        attachTeamHandler: function() {
            var self = this;

            $('#team-list tr').on('click', function() {
                $('#team-list tr').removeClass('info');
                $(this).addClass('info');

                var teamId = $(this).data('teamId');

                self.renderPlayers(teamId);
            });
        },

        renderPlayers: function(teamId) {
            var self = this;

            var playerFormTemplate = window.comp['web/app/scripts/admin/teams/player_form.html'];
            $('#player-form').html(playerFormTemplate());

            // load players for this team...
            $.get('/service/teams/' + teamId + '/players', function(players) {
                var playerListTemplate = window.comp['web/app/scripts/admin/teams/player_list.html'];
                $('#player-list').html(playerListTemplate({
                    players: players
                }));
                $('.captain').on("click", function() {
                    const playerId = $(this).parent().parent().data('player-id');
                    const player = players.find(p => p.id === playerId);
                    const captain = $(this).prop("checked");
                    const teamPlayer = {
                        id: player.id,
                        isGoalie: player.goalie,
                        isCaptain: captain,
                        isCoCaptain: false
                    };
                    $.ajax({
                        type: 'PUT',
                        data: JSON.stringify(teamPlayer),
                        url: '/service/teams/player',
                        contentType: 'application/json; charset=utf-8',
                        dataType: 'json',
                        success: function() {
                            console.log(`Made ${player.name} a captain: ${captain}`);
                            player.captain = captain;
                        }
                    });
                });

                $('.goalie').on("click", function() {
                    const playerId = $(this).parent().parent().data('player-id');
                    const player = players.find(p => p.id === playerId);
                    const goalie = $(this).prop("checked");
                    const teamPlayer = {
                        id: player.id,
                        isGoalie: goalie,
                        isCaptain: player.captain,
                        isCoCaptain: false
                    };
                    $.ajax({
                        type: 'PUT',
                        data: JSON.stringify(teamPlayer),
                        url: '/service/teams/player',
                        contentType: 'application/json; charset=utf-8',
                        dataType: 'json',
                        success: function() {
                            console.log(`Made ${player.name} a goalie: ${goalie}`);
                            player.goalie = goalie;
                        }
                    });

                });

                $('.remove').on("click", function() {
                    const row = $(this).parent().parent();
                    const playerId = row.data('player-id');
                    $.ajax({
                        type: 'DELETE',
                        url: `/service/teams/player/${playerId}`,
                        success: function() {
                            console.log(`Removed player from team.`);
                            players = players.filter(p => p.id !== playerId);
                            row.remove();
                        }
                    });

                });
            });

            $.get('/service/players', function(players) {
                self.attachPlayerSearch(teamId, players);
                $('#player-name').focus();
            });
        },

        attachPlayerSearch: function(teamId, players) {
            var self = this;

            var names = new Bloodhound({
                limit: 10,
                datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                local: players,
                matcher: function(player) {
                    player.name.toUpperCase() === this.query.toUpperCase()
                }
            });

            names.initialize();

            var search = $('#player-name');

            search.on('keyup', function() {
                var text = search.val();
                var $addButton = $('#add-player-button');
                var player = Lazy(players).find(function(player) { return player.name.toUpperCase() === text.toUpperCase(); });
                $addButton.prop('disabled', !!!player);
            });

            search.typeahead({
                    minLength: 1,
                    hint: true,
                    highlight: true
                },
                {
                    name: 'player',
                    displayKey: 'name',
                    source: names.ttAdapter(),
                    templates: {
                        suggestion: Handlebars.compile('<p><strong>{{name}}</strong></p>')
                    }
                });

            var player;
            search.on('typeahead:selected', function(event, suggestion) {
                player = suggestion;
                var $addButton = $('#add-player-button');
                $addButton.prop('disabled', !!!player);
            });

            var $addButton = $('#add-player-button');
            $addButton.on('click', function() {
                if(!player) {
                    return;
                }

                var teamPlayer = {
                    name: player.name,
                    teamId: teamId,
                    playerId: player.id,
                    isGoalie: false,
                    isCaptain: false,
                    isCoCaptain: false
                };

                $.ajax({
                    type: 'POST',
                    data: JSON.stringify(teamPlayer),
                    url: '/service/teams/player',
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    success: function() {
                        names.add(teamPlayer);

                        self.renderPlayers(teamId);
                    }
                });

                var $addButton = $('#add-player-button');
                $addButton.prop('disabled', true);

                var search = $('#player-name');
                search.val('');
            });
        }
    };
});
