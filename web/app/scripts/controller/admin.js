/*global define */
define(function(require) {
	'use strict';
	return {
		render: function() {
			var self = this;
			var passwordTemplate = window.comp['web/app/templates/password.html'];
			$('#administrator').html(passwordTemplate());
			$('#passwordDialog').modal({
				backdrop: false,
				show:true
			});

			$('#password').keyup(function(e) {
				var code = (e.keyCode ? e.keyCode : e.which);
				if(code == 13) {
					self.login();
				}
			});

			$('#passwordButton').click(function() {
				self.login();	
			});

			$('#passwordDialog').on('shown.bs.modal', function() {
				$('#password').focus();
			});
		},

		login: function() {
			$('#passwordButton').prop('disabled', true);
			$('#password').prop('disabled', true);

			var self = this;
			var password = md5($('#password').val());
			$.ajax({
				url: '/service/authentication?password=' + password,
				type: 'POST',
				success: function() {
					var adminTemplate = window.comp['web/app/templates/admin.html'];
					$('#administrator').html(adminTemplate());

					self.attachResetCacheHandler();
					self.attachDatePickerHandler();
				},
				error: function() {
					$('#password-group').addClass('error');
					$('#password').val('');
					$('#passwordHelp').show();

					$('#passwordButton').prop('disabled', false);
					$('#password').prop('disabled', false);
				}
			});
		},

		attachResetCacheHandler: function() {
			var $cacheMessage = $('#cache-message');
			$('#reset-cache').click(function() {
				$cacheMessage.html('<div class="alert alert-error">Clearing cache, please wait...</div>');
				$.get('/service/cache/reset', function() {
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
				$('#admin-game-score').html('');
				var $row = $(row.currentTarget);
				var gameId = parseInt($row.data('game-id'), 10);
				$row.addClass('selected').siblings().removeClass('selected');

				if($row.find('.played').prop('checked')) {
					var game = self.getGame(gameId, games);
					self.selectedGame(game);
				}
			});

			$('.played').click(function() {
				$(this).prop('checked', true);
				$(this).prop('disabled', true);
				
				var gameId = $(this).data('game-id');
				var game = self.getGame(gameId, games);
				game.homeScore = 0;
				game.awayScore = 0;

				var scoreTemplate = window.comp['web/app/templates/score.html'];
				$('#' + gameId + '-score').html(scoreTemplate(game));

				self.played(game);
			});
		},

		selectedGame: function(game) {
			var self = this;

			$.get('/service/games/'+game.id+'/players', function(players) {
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

				game.homePlayers.push({
					player: 'Ghost',
					teamId: game.homeId,
					playerId: -1,
					goals: self.getGhostGoals(game.homeId, game.homeScore, players)
				});

				game.awayPlayers.push({
					player: 'Ghost',
					teamId: game.awayId,
					playerId: -2,
					goals: self.getGhostGoals(game.awayId, game.awayScore, players)
				});

				var scoresTemplate = window.comp['web/app/templates/scores.html'];
				$('#admin-game-score').html(scoresTemplate(game));

				$('.add-goal').click(function() {
					var playerId = $(this).data('player-id');
					self.addGoal(game, playerId);
				});

				$('.remove-goal').click(function() {
					var playerId = $(this).data('player-id');
					self.removeGoal(game, playerId);
				});
			});
		},

		addGoal: function(game, playerId) {
			var $goals = $('#' + playerId + '-goals');
			var goals = parseInt($goals.html(), 10) || 0;
			$goals.html(goals + 1);

			var teamId = this.getTeamId(game, playerId);
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
					gameId: game.id
				})
			});
		},

		removeGoal: function(game, playerId) {
			var $goals = $('#' + playerId + '-goals');
			var goals = parseInt($goals.html(), 10) || 0;

			var teamId = this.getTeamId(game, playerId);
			var $score = $('.' + teamId + '-score');
			var score = parseInt($score.html(), 10) || 0;

			$goals.html(goals - 1);
			$score.html(score - 1);

			$.ajax({
				url: '/service/goals',
				type: 'DELETE',
				contentType: 'application/json; charset=utf-8',
				dataType: 'json',
				data: JSON.stringify({
					playerId: playerId,
					gameId: game.id
				})
			});
		},

		played: function(game) {
			$.ajax({
				url: '/service/games/' + game.id + '/score',
				type: 'PUT',
				contentType: 'application/json; charset=utf-8',
				dataType: 'json',
				data: JSON.stringify(game)
			});
		},

		getGame: function(gameId, games) {
			for(var i = 0; i < games.length; i++) {
				var game = games[i];
				if(game.id === gameId) {
					return game;
				}
			}

			return null;
		},

		getTeamId: function(game, playerId) {
			for(var i = 0; i < game.homePlayers.length; i++) {
				if(playerId === game.homePlayers[i].id) {
					return game.homeId;
				}
			}
			for(var i = 0; i < game.awayPlayers.length; i++) {
				if(playerId === game.awayPlayers[i].id) {
					return game.awayId;
				}
			}

			return null;
		},

		isVisiting: function(game, playerId) {
			for(var i = 0; i < game.awayPlayers.length; i++) {
				if(playerId === game.awayPlayers[i].id) {
					return true;
				}
			}

			return false;
		},

		getGhostGoals: function(teamId, score, players) {
			for(var i = 0; i < players.length; i++) {
				var player = players[i];
				if(player.teamId === teamId) {
					score = score - player.goals;
				}
			}

			return score;
		}
	};
});