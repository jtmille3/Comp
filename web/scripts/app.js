/*global define */
define(function(require) {
    'use strict';

    var $ = require('jquery');
    var templates = require('./scripts/templates.js');
    var compTemplate = templates['web/templates/comp.html'];

    $.getJSON('http://localhost:8080/comp/service/seasons', function(result) {
    	console.log(result);
        $('#seasons').append(compTemplate(result));
    });

    return 'Initialized';
});