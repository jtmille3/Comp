require.config({
//    paths: {
//        jquery: '../components/jquery/dist/jquery',
//        d3: '../components/d3/d3'
//    },
//    shim: {
//        'jquery': {
//            exports: '$'
//        },
//        'd3': {
//            exports: 'd3'
//        }
//    }
});

define(['./controller/competitive'], function (competitiveController) {
    'use strict';
    $.getJSON('/service/competitive', function(competitive) {
        competitiveController.render(competitive);
    });
});