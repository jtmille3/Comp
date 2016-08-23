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
                    self.players = players;
                    self.attachSearch();
                    self.attachHandlers();
                }
            });
        },
        attachSearch: function() {
            var self = this;
            self.names = new Bloodhound({
                limit: 10,
                datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                local: self.players,
                matcher: function(player) {
                    player.name.toUpperCase() === this.query.toUpperCase()
                }
            });

            self.names.initialize();

            var search = $('#player-name');

            search.on('keyup', function() {
                var text = search.val();
                var $addButton = $('#add-player-button');
                var player = Lazy(self.players).find(function(player) { return player.name.toUpperCase() === text.toUpperCase(); });
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
                source: self.names.ttAdapter(),
                templates: {
                    suggestion: Handlebars.compile('<p><strong>{{name}}</strong></p>')
                }
            });
        },

        attachHandlers: function() {
            var self = this;

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
                        self.names.add(player);
                        self.players.push(player);

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
