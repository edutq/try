const db = require('../DB/connection.js');

var Candado = function (candado) {
    this.nombre = candado.nombre;
    this.alias = candado.alias;
};

Candado.asignarCandado = function (idUsuario, idCandado, idAsignador, result) {

    db.query(`CALL ASIGNAR_CANDADO(${idUsuario}, ${idCandado}, ${idAsignador})`,
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

Candado.removerCandado = function (idCandado, idSuperUsuario, result) {

    db.query(`CALL REMOVER_CANDADO(${idCandado}, ${idSuperUsuario})`,
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

Candado.misCandados = function (idUsuario, result) {

    db.query(`CALL MIS_CANDADOS(${idUsuario})`,
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                result(null, results[0])
            }
        });

};

Candado.misCandadosCompartidos = function (idUsuario, result) {

    db.query(`CALL MIS_CANDADOS_COMPARTIDOS(${idUsuario})`,
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                result(null, results[0]);
            }
        });

};

Candado.compartirCandado = function (idUsuario, idCandado, idDueno, administrador, result){

    db.query(`CALL COMPARTIR_CANDADO(${idUsuario}, ${idCandado}, ${idDueno}, ${administrador})`,
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                result(null, results);
            }
        });
};

Candado.dejarDeCompartir = function (idUsuario, idCandado, idDueno, result) {

    db.query(`CALL DEJAR_DE_COMPARTIR(${idUsuario}, ${idCandado}, ${idDueno})`, 
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                result(null, result)
            }
        });
};

Candado.misCandadosPropios = function (idUsuario, result) {

    db.query(`CALL MIS_CANDADOS_PROPIOS(${idUsuario})`,
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                result(null, results[0]);
            }
        });


}

Candado.usuariosCandadoCompartido = function (idCandado, idUsuario, result) {

    db.query(`CALL USUARIOS_CANDADO_COMPARTIDO(${idCandado}, ${idUsuario})`,
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                result(null, results);
            }
        });
};

Candado.cambiarAlias = function (idCandado, idUsuario, nuevoalias, result) {

    db.query(`CALL CAMBIAR_ALIAS(${idCandado}, ${idUsuario}, '${nuevoalias}')`,
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                result(null, results);
            }
        });
};

Candado.modificarAdministracion = function (idUsuario, idCandado, idDueno, administrador, result) {

    db.query(`CALL MODIFICAR_ADMINISTRACION(${idUsuario}, ${idCandado}, ${idDueno}, ${administrador})`, 
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                result(null, results);
            }  
        });
};
module.exports = Candado;
