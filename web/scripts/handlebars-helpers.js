Handlebars.registerHelper('dateFormat', function(context, block) {
    var d = new Date(context);
    var date = d.getDate();
    date = date < 10 ? '0' + date : date;
    var month = d.getMonth() + 1;
    month = month < 10 ? '0' + month : month;
    var year = d.getFullYear();

    var hour = d.getHours();
    hour = hour < 10 ? '0' + hour : hour;
    var minute = d.getMinutes();
    minute = minute < 10 ? '0' + minute : minute;
    return year + '-' + month + '-' + date + ' ' + hour + ':' + minute;
});