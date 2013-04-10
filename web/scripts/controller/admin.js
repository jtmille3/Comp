/*global define */
define(function(require) {
	'use strict';
	return {
		render: function() {
			var adminTemplate = window.comp['web/templates/admin.html'];
			$('#administrator').html(adminTemplate());
		}
	};
});