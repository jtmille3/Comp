/*global define */
define(function(require) {
	'use strict';
	return {
		render: function() {
			var adminTemplate = window.comp['web/app/templates/admin.html'];
			$('#administrator').html(adminTemplate());

			this.attachResetCacheHandler();
			this.attachDatePickerHandler();
		},

		attachResetCacheHandler: function() {
			var $cacheMessage = $('#cache-message');
			$('#reset-cache').click(function() {
				$cacheMessage.html('<div class="alert alert-error">Clearing cache, please wait...</div>');
				$.get('/comp/service/cache/reset', function() {
					$cacheMessage.html('<div class="alert alert-success">Cache cleared</div>');
				});
			});
		},

		attachDatePickerHandler: function() {
			var self = this;
			var $gameDatePicker = $('#game-date');
			$gameDatePicker.datepicker({
				autoclose: true,
				todayBtn: true
			}).on('changeDate', function(ev) {
				var date = self.parseDate($gameDatePicker.data('datepicker').getDate());
				self.getGames(date);
			});

			var today = this.parseDate(new Date());
			$gameDatePicker.datepicker('update', today);
			self.getGames(today); // automatically set todays games
		},

		parseDate: function(date) {
			var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
			var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
			return month + '/' + day + '/' + date.getFullYear();
		},

		getGames: function(date) {
			var self = this;
			$('#admin-game').html('');
			$('#admin-game-score').html('');
			$.get('/service/games?date=' + date, function(games) {
				self.renderGames(games);
			});
		},

		renderGames: function(games) {
			var self = this;
			var scheduleTemplate = window.comp['web/app/templates/schedule.html'];
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

			$('.played').click(function() {
				$(this).prop('checked', true);
				$(this).prop('disabled', true);
				var gameId = $(this).data('game-id');
				var homeId = $(this).data('home-id');
				var awayId = $(this).data('away-id');

				var game = {
					gameId: gameId,
					homeId: homeId,
					homeScore: 0,
					awayId: awayId,
					awayScore: 0
				};

				var scoreTemplate = window.comp['web/app/templates/score.html'];
				$('#' + gameId + '-score').html(scoreTemplate(game));

				self.updateScore(game);
			});
		},

		selectedGame: function(game) {
			var self = this;
			
			$.get('/service/games/'+game.gameId+'/players', function(players) {
				game.homePlayers = [];
				game.awayPlayers = [];
				for(var i = 0; i < players.length; i++) {
					var player = players[i];
					if(game.homeId === player.teamId) {
						game.homePlayers.push(player);
					} else if(game.awayId === player.teamId) {
						game.awayPlayers.push(player);
					}
				}

				var scoresTemplate = window.comp['web/app/templates/scores.html'];
				$('#admin-game-score').html(scoresTemplate(game));

				$('.add-goal').click(function() {
					var playerId = $(this).data('player-id');
					var gameId = $(this).data('game-id');
					var teamId = $(this).data('team-id');
					var type = $(this).data('type');
					self.addGoal(gameId, teamId, playerId, type);
				});

				$('.remove-goal').click(function() {
					var playerId = $(this).data('player-id');
					var gameId = $(this).data('game-id');
					var teamId = $(this).data('team-id');
					var type = $(this).data('type');
					self.removeGoal(gameId, teamId, playerId, type);
				});

				$('#goalless').click(function() {
					var gameId = parseInt($(this).data('game-id'), 10);
					var game = {
						gameId: gameId,
						homeScore: 0,
						awayScore: 0
					};
					self.updateScore(game);
					$('#' + gameId + '-played').prop('checked', true);
					$(this).remove();
				});
			});
		},

		addGoal: function(gameId, teamId, playerId, type) {
			var $goals = $('#' + playerId + '-goals');
			var goals = parseInt($goals.html(), 10) || 0;
			$goals.html(goals + 1);

			var $score = $('.' + teamId + '-score');
			var score = parseInt($score.html(), 10) || 0;
			$score.html(score + 1);

			$.ajax({
				url: '/service/goals',
				type: 'POST',
				contentType: 'application/json; charset=utf-8',
				dataType: 'json',
				data: JSON.stringify({
					playerId: playerId,
					gameId: gameId
				})
			});

			var game = {};
			game.gameId = gameId;{
			game[type + 'Score'] = score + 1;
			this.updateScore(game);}
		},

		removeGoal: function(gameId, teamId, playerId, type) {
			var $goals = $('#' + playerId + '-goals');
			var goals = parseInt($goals.html(), 10) || 0;

			var $score = $('.' + teamId + '-score');
			var score = parseInt($score.html(), 10) || 0;

			if(goals > 0) {
				$goals.html(goals - 1);
				$score.html(score - 1);

				$.ajax({
					url: '/service/goals',
					type: 'DELETE',
					contentType: 'application/json; charset=utf-8',
					dataType: 'json',
					data: JSON.stringify({
						playerId: playerId,
						gameId: gameId
					})
				});

				var game = {};
				game.gameId = gameId;
				game[type + 'Score'] = score - 1;
				this.updateScore(game);
			}
		},

		updateScore: function(game) {
			$.ajax({
				url: '/service/games/' + game.gameId + '/score',
				type: 'PUT',
				contentType: 'application/json; charset=utf-8',
				dataType: 'json',
				data: JSON.stringify(game)
			});
		}
	};
});