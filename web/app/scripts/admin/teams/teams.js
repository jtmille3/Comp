/*global define */
define(function() {
    'use strict';
    return {
        render: function() {
            var self = this;

            // query teams here...
            var teamsTemplate = window.comp['web/app/scripts/admin/teams/teams.html'];
            $('#admin-content').html(teamsTemplate());

            // self.attachHandlers();
        }
    };
});
