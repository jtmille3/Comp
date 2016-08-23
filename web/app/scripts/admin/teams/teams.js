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
                var seasonId = $(this).attr('id').substring('season-'.length);
                self.renderTeams(seasonId);

                $('#team-name').attr('readonly', false);

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

            $.get('/service/seasons/' + seasonId + '/teams', function(teams) {
                var teamListTemplate = window.comp['web/app/scripts/admin/teams/team_list.html'];
                $('#team-list').html(teamListTemplate({
                    teams: teams
                }));

                self.attachTeamSearch(teams);
            });
        },

        attachTeamSearch: function(teams) {
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

                $.ajax({
                    type: 'POST',
                    data: JSON.stringify({name: name}),
                    url: '/service/teams',
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    success: function() {
                        var team = {name: name};
                        names.add(team);
                        teams.push(team);

                        var $teamMessage = $('#team-message');
                        $teamMessage.html('<div class="alert alert-success">Added team ' + name + '</div>');
                    }
                });

                var $addButton = $('#add-team-button');
                $addButton.prop('disabled', true);

                var search = $('#team-name');
                search.val('');
            });
        }
    };
});
