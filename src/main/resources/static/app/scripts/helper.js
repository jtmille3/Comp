define(function(require) {
	'use strict';

	return {
		createMap: function(list) {
			var i = list.length;
			var map = {};
			while(i--) {
				var item = list[i];
				map[item.id] = item;
			}
			return map;
		}
	};
});