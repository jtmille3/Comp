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
    'admin/games/games',
    'admin/cache/cache',
], function (
    adminController,
    seasonsController,
    playersController,
    teamsController,
    gamesController,
    cacheController
) {
    'use strict';

    Handlebars.registerHelper('ifEquals', function(arg1, arg2, options) {
        return (arg1 == arg2) ? options.fn(this) : options.inverse(this);
    });

    var adminTemplate = window.comp['web/app/scripts/admin/admin.html'];
    $('#administrator').html(adminTemplate());

    // primitive auth
    $('#password').keyup(function(e) {
        var code = (e.keyCode ? e.keyCode : e.which);
        if(code == 13) {
            login();
        }
    });
    $('#passwordButton').click(function() {
        login();
    });
    $('#passwordDialog').on('shown.bs.modal', function() {
        $('#password').focus();
    });
    var login = function() {
        $('#passwordButton').prop('disabled', true);
        $('#password').prop('disabled', true);
        var password = md5($('#password').val());
        $.ajax({
            url: '/service/authentication?password=' + password,
            type: 'POST',
            success: function() {
                $('#passwordDialog').modal('hide');
                $(".modal-backdrop").remove();
            },
            error: function() {
                $('#password-group').addClass('error');
                $('#password').val('');
                $('#passwordHelp').show();

                $('#passwordButton').prop('disabled', false);
                $('#password').prop('disabled', false);
                $('#password').focus();
            }
        });
    }
    // primitive password
    $('#passwordDialog').modal({
        backdrop: false,
        show:true
    });

    // setup crossroads
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

    crossroads.addRoute('games', function() {
        gamesController.render();

        $('#admin-menu').children().removeClass('active');
        $('#games-menu').addClass('active');

        $('#admin-content').children().removeClass('active');
        $('#games').addClass('active');
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
