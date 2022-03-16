require.config({
//    paths: {
//        jquery: '../components/jquery/jquery'
//    },
//    shim: {
//        'jquery': {
//            exports: '$'
//        }
//    }
});

require([
    'admin/scores/scores',
    'admin/seasons/seasons',
    'admin/players/players',
    'admin/teams/teams',
    'admin/cache/cache',
], function (
    adminController,
    seasonsController,
    playersController,
    teamsController,
    cacheController
) {
    'use strict';

    var adminTemplate = window.comp['web/app/scripts/admin/admin.html'];
    $('#administrator').html(adminTemplate());

    //setup crossroads
    crossroads.addRoute('', function() {
        adminController.render();

        $('#admin-menu').children().removeClass('active');
        $('#scores-menu').addClass('active');

        $('#scores-content').children().removeClass('active');
        $('#scores').addClass('active');
    });

    crossroads.addRoute('scores', function() {
        adminController.render();

        $('#admin-menu').children().removeClass('active');
        $('#scores-menu').addClass('active');

        $('#admin-content').children().removeClass('active');
        $('#scores').addClass('active');
    });

    crossroads.addRoute('seasons', function() {
        seasonsController.render();

        $('#admin-menu').children().removeClass('active');
        $('#seasons-menu').addClass('active');

        $('#admin-content').children().removeClass('active');
        $('#seasons').addClass('active');
    });

    crossroads.addRoute('players', function() {
        playersController.render();

        $('#admin-menu').children().removeClass('active');
        $('#players-menu').addClass('active');

        $('#admin-content').children().removeClass('active');
        $('#players').addClass('active');
    });

    crossroads.addRoute('teams', function() {
        teamsController.render();

        $('#admin-menu').children().removeClass('active');
        $('#teams-menu').addClass('active');

        $('#admin-content').children().removeClass('active');
        $('#teams').addClass('active');
    });

    crossroads.addRoute('cache', function() {
        cacheController.render();

        $('#admin-menu').children().removeClass('active');
        $('#cache-menu').addClass('active');

        $('#admin-content').children().removeClass('active');
        $('#cache').addClass('active');
    });

    var firstHash = null;
    function parseHash(hash) {
        crossroads.parse(hash);
        if(!firstHash)
            firstHash = hash;
    }

    hasher.initialized.add(parseHash);
    hasher.changed.add(parseHash);
    hasher.init();
});
