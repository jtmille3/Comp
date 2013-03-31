require.config({
    paths: {
        jquery: '../components/jquery/jquery',
        handlebars: '../components/handlebars/handlebars'
    },
    shim: {
        'jquery': {
            exports: '$'
        },
        'handlebars': {
            exports: 'Handlebars'
        }
    }
});

require(['app'], function (app) {
    'use strict';
    console.log(app);
});