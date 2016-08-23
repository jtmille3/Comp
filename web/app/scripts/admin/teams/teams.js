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

                var $div = $(this).parent().parent().parent();
                var $btn = $div.find('button');
                $btn.html($(this).text() + ' <span class="caret"></span>');
                $div.removeClass('open');
                e.preventDefault();
                return false;
            });
        },

        renderTeams: function(seasonId) {
            $.get('/service/seasons/' + seasonId + '/teams', function(teams) {
                var teamListTemplate = window.comp['web/app/scripts/admin/teams/team_list.html'];
                $('#team-list').html(teamListTemplate({
                    teams: teams
                }));
            });
        }
    };
});
