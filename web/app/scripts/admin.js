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

require(['./controller/admin'], function (adminController) {
    'use strict';

    adminController.render();
});