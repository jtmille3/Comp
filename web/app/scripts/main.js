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

require(['./controller/competitive'], function (competitiveController) {
    'use strict';

    /*
    Before implementing make sure click around the page doesn't
    get reset once we load new data.  Otherwise breaks user
    experience.

    var compTemplate = window.comp['web/templates/comp.html'];

    if(Modernizr.localstorage) {
        competitive = JSON.parse(localStorage.competitive);
        $('#competitive').replaceWith(compTemplate(allData));
    }
    */

    $.getJSON('/service/competitive', function(competitive) {
        competitiveController.render(competitive);
    });
});