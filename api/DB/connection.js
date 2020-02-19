const mysql = require('mysql');

var connection = mysql.createConnection({
    host     : 'localhost',
    port     : '3306',
    user     : 'root',
    password : 'ADmin123',
    database : 'translock',
    multipleStatements: true
});

connection.connect( function (err) {
    if (err) {
        console.error(err.stack);
    }
    else {
        console.log('connection success');
    }
});

module.exports = connection;