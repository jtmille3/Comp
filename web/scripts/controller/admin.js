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

			this.renderDatePicker();
		},

		renderDatePicker: function() {
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
			$.get('/comp/service/schedules?date=' + date, function(payload) {
				console.log(payload);
			});
		}
	};
});