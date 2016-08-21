/*global define */
define(function() {
    'use strict';
    return {
        render: function() {
            var self = this;

            $.get('/service/seasons', function(seasons) {
                var seasonsTemplate = window.comp['web/app/scripts/admin/seasons/seasons.html'];
                $('#admin-content').html(seasonsTemplate(seasons));

                self.attachHandlers();
            });
        },

        attachHandlers: function() {
            var self = this;

            $('#add-season-button').on('click', function() {
                var name = $('#season-name').val();
                $.ajax({
                    type: 'POST',
                    url: '/service/seasons',
                    data: JSON.stringify({name: name}),
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    success: function(){
                        self.render();
                    },
                    failure: function(errMsg) {
                        alert(errMsg);
                    }
                });
                return false;
            });
        }
    };
});
