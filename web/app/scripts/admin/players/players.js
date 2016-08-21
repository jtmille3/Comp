/*global define */
define(function() {
    'use strict';
    return {
        render: function() {
            var playersTemplate = window.comp['web/app/scripts/admin/players/players.html'];
            $('#admin-content').html(playersTemplate());

            // self.attachHandlers();
        }
    };
});
