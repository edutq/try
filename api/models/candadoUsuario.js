const db = require('../DB/connection.js');

var candadoUsuario = function (candadoUsuario) {
    this.idUsuario = candadoUsuario.idUsuario;
    this.idCandado = candadoUsuario.idCandado;
    this.administrador = candadoUsuario.administrador;
};

candadoUsuario.otorgarPermiso = function (fecha_inicio, hora_inicio, fecha_fin, hora_fin, idUsuario, idCandado, idDueno, result) {

    db.query(`CALL OTORGAR_PERMISO('${fecha_inicio}', '${hora_inicio}', '${fecha_fin}',
                                     '${hora_fin}', ${idUsuario}, ${idCandado}, ${idDueno})`,
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

candadoUsuario.verPermisos = function (idUsuario, idCandado, result) {

    db.query(`CALL FETCH_PERMISOS(${idUsuario}, ${idCandado})`, 
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

candadoUsuario.misPermisos = function (idUsuario, result) {

    db.query(`CALL FETCH_TODOS_MIS_PERMISOS(${idUsuario})`, 
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

candadoUsuario.eliminarPermiso = function (idPermiso, idDueno, result) {

    db.query(`CALL ELIMINAR_PERMISO(${idPermiso}, ${idDueno})`, 
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

candadoUsuario.editarPermiso = function (fecha_inicio, hora_inicio, fecha_fin, hora_fin, idPermiso, idCandado, idDueno, result) {

    db.query(`CALL EDITAR_PERMISO('${fecha_inicio}', '${hora_inicio}', '${fecha_fin}',
                                     '${hora_fin}', ${idPermiso}, ${idCandado}, ${idDueno})`,
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                if (results) {
                    result(null, results);
                }
            }
        });
}

module.exports = candadoUsuario;