require.config({
    paths: {
        jquery: '../components/jquery/jquery'
    },
    shim: {
        'jquery': {
            exports: '$'
        }
    }
});

require(['app'], function (app) {
    'use strict';
    console.log(app);
});