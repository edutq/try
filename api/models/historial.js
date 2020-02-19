const db = require('../DB/connection.js');

var Historial = function (historial) {
    this.id = historial.id;
    this.idUsuario = historial.idUsuario;
    this.idCandado = historial.idCandado;
    this.idEvento = historial.idEvento;
    this.fecha = historial.fecha;
};

Historial.registrar = function (idUsuario, idCandado, idEvento, result) {

    db.query(`CALL REGISTRAR_HISTORIAL(${idUsuario}, ${idCandado}, ${idEvento})`, 
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                if (results) {
                    result(null, results);
                }
            }
        });

};

Historial.registrarOffline = function (fecha, idUsuario, idCandado, idEvento, result) {

    db.query(`CALL REGISTRAR_HISTORIAL_OFFLINE('${fecha}', ${idUsuario}, ${idCandado}, ${idEvento})`, 
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                if (results) {
                    result(null, results);
                }
            }
        });

};

Historial.miHistorial = function (idUsuario, result) {

    db.query(`CALL FETCH_HISTORIAL(${idUsuario})`, 
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                if (results[0]) {
                    result(null, results[0]);
                }
            }
        });

};

Historial.miHistorialPorFecha = function (idUsuario, fechaInicio, fechaFin, result) {

    db.query(`CALL FETCH_HISTORIAL_POR_FECHA(${idUsuario}, '${fechaInicio}', '${fechaFin}')`,
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                if (results[0]) {
                    result(null, results[0]);
                }
            }
        });
}

module.exports = Historial;