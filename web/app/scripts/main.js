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

require(['./controller/competitive'], function (competitiveController) {
    'use strict';

    //setup crossroads
    crossroads.addRoute('seasons/{id}', function(id) {
        competitiveController.renderSeason(id);
    });
    crossroads.addRoute('alltime', function() {
        competitiveController.renderAllTime();
    });
    // crossroads.routed.add(console.log, console); //log all routes

    //setup hasher
    function parseHash(newHash, oldHash) {
        crossroads.parse(newHash);
    }

    hasher.initialized.add(parseHash); //parse initial hash
    hasher.changed.add(parseHash); //parse hash changes
    hasher.init(); //start listening for history change

    $.getJSON('/service/competitive', function(competitive) {
        competitiveController.init(competitive);

        // navigate to the first season
        var season = Lazy(competitive.seasons).first();
        hasher.setHash('seasons/' + season.id);
    });
});