/*global define */
define(function() {
    'use strict';
    return {
        render: function() {
            var self = this;
            var playersTemplate = window.comp['web/app/scripts/admin/players/players.html'];
            $('#admin-content').html(playersTemplate());

            // self.attachHandlers();
            $.ajax({
                url: '/service/players',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function(players) {
                    self.attachSearch(players);
                }
            });
        },

        attachSearch: function(players) {
            var names = new Bloodhound({
                limit: 10,
                datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                local: players
            });

            names.initialize();

            var search = $('#player-name');

            search.on('keyup', function() {
                var text = search.val();
                var $addButton = $('#add-player-button');
                var player = Lazy(players).find(function(player) { return player.name.toUpperCase() === text.toUpperCase(); });
                $addButton.prop('disabled', !!player);
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

            var $addButton = $('#add-player-button');
            $addButton.on('click', function() {
                var search = $('#player-name');
                var name = search.val();

                $.ajax({
                    type: 'POST',
                    data: JSON.stringify({name: name}),
                    url: '/service/players',
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    success: function() {
                        var player = {name: name};
                        names.add(player);
                        players.push(player);

                        var $playerMessage = $('#player-message');
                        $playerMessage.html('<div class="alert alert-success">Added user ' + name + '</div>');
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
