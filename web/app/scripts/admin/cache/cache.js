/*global define */
define(function() {
    'use strict';
    return {
        render: function() {
            var cacheTemplate = window.comp['web/app/scripts/admin/cache/cache.html'];
            $('#admin-content').html(cacheTemplate());

            this.attachHandlers();
        },

        attachHandlers: function() {
            $('#reset-cache').click(function() {
                var $cacheMessage = $('#cache-message');
                $cacheMessage.html('<div class="alert alert-error">Clearing cache, please wait...</div>');
                $.get('/service/cache/reset', function() {
                    $cacheMessage.html('<div class="alert alert-success">Cache cleared</div>');
                });

                return false;
            });
        }
    };
});
