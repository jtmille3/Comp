/*global define */
define(function(require) {
	'use strict';
	return {
		render: function() {
			var adminTemplate = window.comp['web/templates/admin.html'];
			$('#administrator').html(adminTemplate());

			$('#reset-cache').click(function() {
				$.get('/comp/service/cache/reset', function() {
					console.log('done');
				});
			});

			this.attachDatePickerHandler();
		},

		attachDatePickerHandler: function() {
			var self = this;
			var $gameDatePicker = $('.game-datepicker');
			$gameDatePicker.datepicker({
				autoclose: true,
				todayBtn: true
			}).on('changeDate', function(ev) {
				var date = self.parseDate($gameDatePicker.data('datepicker').getDate());
				self.getGames(date);
			});

			var today = this.parseDate(new Date());
			$gameDatePicker.datepicker('update', today);
		},

		attachPlayerTypeAheadHandler: function(players) {
			var self = this;
			console.log(players);
			$('#game-players').typeahead({
				source: players,
				matcher: function(player) {
					return ~player.name.toLowerCase().indexOf(this.query.toLowerCase())
				},
				sorter: function(player) {
					return player;
				}, 
				highlighter: function(player) {
					return player;
				}
			});
		},

		parseDate: function(date) {
			var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
			var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
			return month + '/' + day + '/' + date.getFullYear();
		},

		getGames: function(date) {
			var self = this;
			$.get('/comp/service/schedules?date=' + date, function(games) {
				self.renderGames(games);
			});
		},

		renderGames: function(games) {
			console.log(games);
			var self = this;

			var scheduleTemplate = window.comp['web/templates/schedule.html'];
			$('#admin-games').html(scheduleTemplate(games));

			$('#game-schedule-table tr').click(function(row) {
				var $row = $(row.currentTarget);
				var gameId = parseInt($row.attr('id'), 10);
				$row.addClass('selected').siblings().removeClass('selected');
				for(var i = 0; i < games.length; i++) {
					var game = games[i];
					if(game.gameId === gameId) {
						self.selectedGame(game);
						break;
					}
				}
			});
		},

		selectedGame: function(game) {
			var self = this;
			var scoresTemplate = window.comp['web/templates/scores.html'];
			$('#admin-game-score').html(scoresTemplate(game));
			
			$.get('/comp/service/games/'+game.gameId+'/players', function(players) {
				self.attachPlayerTypeAheadHandler(players);	
			});
		}
	};
});