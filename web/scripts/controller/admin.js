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
			
			$.get('/comp/service/games/'+game.gameId+'/players', function(players) {
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

				var scoresTemplate = window.comp['web/templates/scores.html'];
				$('#admin-game-score').html(scoresTemplate(game));

				$('.add-goal').click(function() {
					var playerId = $(this).data('player-id');
					var gameId = $(this).data('game-id');
					var teamId = $(this).data('team-id');
					
					self.addPlayerGoal(playerId, gameId);
					self.addTeamGoal(teamId);
				});

				$('.remove-goal').click(function() {
					var playerId = $(this).data('player-id');
					var gameId = $(this).data('game-id');
					var teamId = $(this).data('team-id');
					
					self.removePlayerGoal(playerId, gameId);
					self.removeTeamGoal(teamId);
				});
			});
		},

		addPlayerGoal: function(playerId, gameId) {
			var $goals = $('#' + playerId + '-goals');
			var goals = parseInt($goals.html(), 10);
			$goals.html(goals + 1);
		},

		addTeamGoal: function(teamId) {
			var $score = $('#' + teamId + '-score');
			var score = parseInt($score.html(), 10);
			$score.html(score + 1);
		},

		removePlayerGoal: function(playerId, gameId) {
			var $goals = $('#' + playerId + '-goals');
			var goals = parseInt($goals.html(), 10);
			if(goals > 0) {
				$goals.html(goals - 1);
			}
		},

		removeTeamGoal: function(teamId) {
			var $score = $('#' + teamId + '-score');
			var score = parseInt($score.html(), 10);
			if(score > 0) {
				$score.html(score - 1);
			}
		}
	};
});