/*global define */
define(function() {
    'use strict';
    return {
        seasonId: '',
        render: function() {
            var self = this;

            $.get('/service/seasons', function(seasons) {
                self.seasons = seasons;

                var gamesTemplate = window.comp['web/app/scripts/admin/games/games.html'];
                $('#admin-content').html(gamesTemplate({
                    seasons: seasons
                }));

                self.attachHandlers();
            });
        },

        attachHandlers: function() {
            var self = this;

            $('#seasons-dropdown-games a').on('click', function (e) {
                var seasonId = $(this).data('seasonId');
                self.seasonId = seasonId;
                self.getGames(seasonId);

                var $div = $(this).parent().parent().parent();
                var $btn = $div.find('button');
                $btn.html($(this).text() + ' <span class="caret"></span>');
                $div.removeClass('open');

                e.preventDefault();
                return false;
            });
        },

        getGames: function(seasonId) {
            var self = this;

            $.get('/service/seasons/' + seasonId + '/teams', function(teams) {
                // sort teams by name
                teams.sort((a, b) => {
                    if (a.name < b.name) {
                        return -1;
                    }
                    if (a.name > b.name) {
                        return 1;
                    }
                    return 0;
                });
                $.get('/service/seasons/' + seasonId + '/schedule', function(games) {
                    Lazy(games).each(function (game, g) {
                        game.teams = teams;
                        var parts = game.played.split(" ");
                        game.date = parts[0];
                        game.time = parts[1];
                    });
                    self.renderGames(games);
                    self.attachGameHandlers(teams);
                });
            });
        },

        renderGames: function(games) {
            var scheduleTemplate = window.comp['web/app/scripts/admin/games/schedule.html'];
            $('#games-form').html(scheduleTemplate({games: games}));
        },

        attachGameHandlers: function(teams) {
            var self = this;
            $('#game-schedule-table .input-group.date').datepicker({
                format: "yyyy/mm/dd",
                autoclose: true
            });
            $('#new-game-schedule-table .input-group.date').datepicker({
                format: "yyyy/mm/dd",
                autoclose: true
            });
            $('#game-schedule-table .save-game').click(function() {
                var gameId = $(this).data('game-id');
                var homeId = $('#game-schedule-table select[name="game_'+gameId+'_home_team_id"]').find(":selected").val();
                var awayId = $('#game-schedule-table select[name="game_'+gameId+'_away_team_id"]').find(":selected").val();
                var date = $('#game-schedule-table input[name="date-game-' + gameId + '"]').val();
                var time = $('#game-schedule-table input[name="time-game-' + gameId + '"]').val();
                var when = date + ' ' + time;
                var playoff = $('#game-schedule-table input[name="playoff-game-' + gameId + '"]').is(':checked');
                console.log('save seasonId: ' + self.seasonId + ' game: ' + gameId + ' homeId: ' + homeId + ' awayId: ' + awayId + ' when: ' + when + ' playoff: ' + playoff);
                self.saveGame({seasonId: self.seasonId, id: gameId, homeId: homeId, awayId: awayId, date: when, playoff: playoff});
            });
            $('#game-schedule-table .delete-game').click(function() {
                var gameId = $(this).data('game-id');
                var home = $('#game-schedule-table select[name="game_'+gameId+'_home_team_id"]').find(":selected").text();
                var away = $('#game-schedule-table select[name="game_'+gameId+'_away_team_id"]').find(":selected").text();
                var date = $('#game-schedule-table input[name="date-game-' + gameId + '"]').val();
                var time = $('#game-schedule-table input[name="time-game-' + gameId + '"]').val();
                var when = date + ' ' + time;
                var msg = 'delete game: ' + gameId + ' home: ' + home + ' away: ' + away + ' when: ' + when;
                console.log(msg);
                if (confirm(msg) == true) {
                    self.deleteGame({seasonId: self.seasonId, id: gameId});
                }
            });
            $('#admin-add-game-modal').click(function() {
                $('#new-game-modal').modal('show');
            });
            $('#new-game-save').click(function() {
                var homeId = $('select[name=new-game-home]').val();
                var awayId = $('select[name=new-game-away]').val();
                var when = $('input[name=new-game-date]').val() + ' ' + $('input[name=new-game-time]').val();
                var playoff = $('input[name=new-game-playoff]').is(':checked');
                $('#new-game-modal').modal('hide');
                $(".modal-backdrop").remove();
                self.addGame({seasonId: self.seasonId, homeId: homeId, awayId: awayId, date: when, playoff: playoff});
            });
        },

        addGame: function(game) {
            var self = this;
            // use post to add new game
            $.ajax({
                url: '/service/games/new',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(game),
                success: function(data) {
                    console.log('success');
                    console.log(JSON.stringify(data));
                    self.getGames(game.seasonId);
                },
                error: function(xhr) {
                    alert('error occurred, see console log\n'+xhr.statusText);
                    console.log(xhr.statusText);
                    console.log(xhr.responseText);
                }
            });
        },

        saveGame: function(game) {
            var self = this;
            // use put to update existing game
            $.ajax({
                url: '/service/games/' + game.id + '/save',
                type: 'PUT',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(game),
                success: function(data) {
                    console.log('success');
                    console.log(JSON.stringify(data));
                    $('#game-schedule-table tr[data-game-id='+game.id+']').removeClass('bg-danger').addClass('bg-success');
                    self.getGames(game.seasonId);
                },
                error: function(xhr) {
                    alert('error occurred, see console log\n'+xhr.statusText);
                    console.log(xhr.statusText);
                    console.log(xhr.responseText);
                }
            });
        },
        
        deleteGame: function(game) {
            var self = this;
            $.ajax({
                url: '/service/games/' + game.id + '/delete',
                type: 'DELETE',
                contentType: 'application/json; charset=utf-8',
                success: function(data) {
                    console.log('success');
                    console.log(JSON.stringify(data));
                    self.getGames(game.seasonId);
                },
                error: function(xhr) {
                    alert('error occurred, see console log\n'+xhr.statusText);
                    console.log(xhr.statusText);
                    console.log(xhr.responseText);
                }
            });
        }

    };
});
