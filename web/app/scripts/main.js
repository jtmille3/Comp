require.config({
});

require(['./controller/competitive'], function (competitiveController) {
    'use strict';

    //setup crossroads
    crossroads.addRoute('seasons/{id}', function(id) {
        competitiveController.renderSeason(id);
    });
    crossroads.addRoute('seasons/{id}/standings', function(id) {
        competitiveController.renderStandings(id);
    });
    crossroads.addRoute('seasons/{id}/schedule', function(id) {
        competitiveController.renderSchedule(id);
    });
    crossroads.addRoute('seasons/{id}/statistics', function(id) {
        competitiveController.renderStatistics(id);
    });
    crossroads.addRoute('seasons/{id}/playoffs', function(id) {
        competitiveController.renderPlayoffs(id);
    });
    crossroads.addRoute('alltime', function() {
        competitiveController.renderAllTime();
    });
    crossroads.addRoute('reset');

    var firstHash = null;
    function parseHash(hash) {
        crossroads.parse(hash);
        if(!firstHash)
            firstHash = hash;
    }

    hasher.initialized.add(parseHash);
    hasher.changed.add(parseHash);
    hasher.init();

    $.getJSON('/service/competitive', function(competitive) {
        competitiveController.init(competitive);

        if(firstHash) {
            hasher.setHash('reset');
            hasher.setHash(firstHash);
        } else {
            // navigate to the first season
            var season = Lazy(competitive.seasons).first();
            hasher.setHash('seasons/' + season.id);
        }
    });
});