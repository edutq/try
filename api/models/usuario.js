const db = require('../DB/connection.js');
const bcrypt = require('bcrypt');
const saltRounds = 10;

var User = function(user) {
    this.id = user.id;
    this.nombre = user.nombre;
    this.apellido = user.apellido;
    this.telefono = user.telefono;
    //this.password = user.password;
    this.activo = user.activo;
};

User.getUserByTelefono = function (telefono, result) {

    db.query(`CALL BUSCAR_USUARIO('${telefono}');`,
        function (err, results, fields) {
            if (err) {

            console.log(err.message);
            result(err, null);

            }
            if (results) {
                result(null, results[0][0]);
            }


        });

};

User.createUser = function (telefono, nombre, apellido, password, result) {

    User.getUserByTelefono (telefono, function (err, user) {
        if (err) {
            result(err, null);
        } else {
            if (user) {
                result({message: "El teléfono ya esta en uso"}, null);
            } else {
                bcrypt.hash(password, saltRounds, function(err, hash) {

                    db.query(`CALL CREAR_USUARIO('${nombre}', '${apellido}', '${hash}', '${telefono}')`,
                        function (err, results, fields) {
                            if (err) {
                                result(err, null);
                            } else {
                                if (results) {
                                    result(null, results);
                                }
                            }

                    });
                });
            }
        }
    });


}

User.getAll = function (result) {

    db.query(`CALL TODOS_LOS_USUARIOS()`, function (err, results, fields) {
        if (err) {
            result(err, null);
        } else {
            if (results) {
                result(null, results[0]);
            }
        }
    });

}

User.buscarUsuarios = function (idUsuario, result) {

    db.query(`CALL BUSCAR_USUARIOS(${idUsuario})`, function (err, results, fields) {
        if (err) {
            result(err, null);
        } else {
            if (results) {
                result(null, results[0]);
            }
        }
    });

}

User.editarNombre = function (nombre, apellido, telefono, result) {

    db.query(`CALL EDITAR_USUARIO_NOMBRE('${nombre}', '${apellido}', '${telefono}')`,
			function (err, results, fields) {
				if (err) {
					result(err, null);
				} else {
					result(null, results);
				}
			});

}

User.activar = function (telefono, result) {

    telefono = telefono.substring(3, telefono.length);
    db.query(`CALL ACTIVAR_USUARIO('${telefono}');`,
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                result(null, results);
            }
        });
};

User.cambiarPassword = function (idUsuario, password, result) {

    db.query(`CALL CAMBIAR_PASSWORD('${idUsuario}', '${password}');`,
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                result(null, results)
            }
        });
};

User.recuperarPassword = function (telefono, password, result) {

    db.query(`CALL RECUPERAR_PASSWORD('${telefono}', '${password}');`,
        function (err, results, fields) {
            if (err) {
                result(err, null);
            } else {
                result(null, results)
            }
        });
};

User.getPassword = function (idUsuario, result) {

    db.query(`CALL FETCH_PASSWORD(${idUsuario})`,
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

module.exports = User;
