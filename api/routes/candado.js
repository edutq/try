const express = require('express');
const router = express.Router();
const db = require('../DB/connection.js');
const bcrypt = require('bcrypt');
const saltRounds = 10;
const Candado = require('../models/candado.js');

var routes = {
	mostrar: function (req, res, next) {

		db.query(`CALL VER_CANDADO(${req.params.id_candado})`, function (err, results, fields){
			if (err) {
				res.send(err.message);
			} else {
				res.send(results[0][0]);
			}
		});
	},
	crear: function (req, res, next) {
		bcrypt.genSalt(saltRounds, function(err, salt) {
			bcrypt.hash(req.params.clave, saltRounds, function(err, hash) {
				db.query(`CALL CREAR_CANDADO(?,?,?, @mensaje); select @mensaje`, [req.params.id_usuario, req.params.nombre, hash],
					function (err, results, fields) {
						if (err) {
							if (err.code == "ER_DUP_ENTRY") {
								res.send("El candado ya existe!");
							}
						}
						res.send(results[1][0]['@mensaje']);
					});
			});
		});
	},
	editar: function (req, res, next) {
		db.query(`CALL EDITAR_CANDADO(${req.params.id_candado}, '${req.params.nombre}', '${req.params.alias}', @mensaje); select @mensaje`,
		function (err, results, fields) {
			if (err) {
				res.send(err.message);
			} else {
				res.send(results[1][0]['@mensaje']);
			}
		});
	},
	eliminar: function (req, res, next) {
		db.query(`CALL ELIMINAR_CANDADO(${req.params.id_candado}, @mensaje); select @mensaje`,
		function (err, results, fields) {
			if (err) {
				res.send(err.message);
			} else {
				res.send(results[1][0]['@mensaje']);
			}
		});
	},

	mostrar_todos: function (req, res, next) {
		db.query(`CALL TODOS_LOS_CANDADOS()`, function (err, results, fileds) {
			if (err) {
				res.send(err.message);
			} else {
				res.send(results[0]);
			}
		});
	},

	mis_candados: function(req, res, next) {

		Candado.misCandados(req.user.id, function (err, result) {
			if (err) {
				res.json({error: err.message});
				console.log(err);
			} else {
				if (result) {
					res.json(result);
					console.log(result);
				}
			}
		})
	},

	mis_candados_compartidos: function (req, res, next) {

		Candado.misCandadosCompartidos(req.user.id, function (err, result) {
			if (err) {
				res.json({error: err.message});
			} else {
				if (result) {
					res.json(result);
				}
			}
		});
	},

	mis_candados_propios: function (req, res, next) {

		Candado.misCandadosPropios(req.user.id, function (err, result) {
			if (err) {
				res.json({error: err.message});
			} else {
				if (result) {
					res.json(result);
				}
			}
		});
	},
	usuarios_candado_compartido: function (req, res, next) {

		Candado.usuariosCandadoCompartido(req.params.idCandado, req.user.id, function (err, result) {
			if (err) {
				res.json([{error: err.message}]);
			} else {
				if (result) {
					res.json(result[0]);
				}
			}
		});
	},
	cambiar_alias: function (req, res, next) {

		Candado.cambiarAlias(req.params.idCandado, req.user.id, req.params.alias, function(err, result) {
			if (err) {
				res.send(err.message);
			} else {
				if (result) {
					res.send("Exito");
				}
			}
		});
	},

	compartir_candado: function (req, res, next) {

		Candado.compartirCandado(req.params.idUsuario, req.params.idCandado, req.user.id, req.params.administrador,
			function (err, result) {
				if (err) {
					res.send(err.message);
				} else {
					if (result) {
						res.send("Exito");
					}
				}
		});
	},

	dejar_de_compartir: function (req, res, next) {

		Candado.dejarDeCompartir(req.params.idUsuario, req.params.idCandado, req.user.id, function (err, result) {
			if (err) {
				res.send(err.message);
			} else {
				if (result) {
					res.send("Exito");
				}
			}
		})
	},
	modificar_administracion: function (req, res, next) {

		Candado.modificarAdministracion(req.params.idUsuario, req.params.idCandado,
										 req.user.id, req.params.administrador, 
			function (err, result) {
				if (err) {
					res.send(err.message);
				} else {
					if (result) {
						res.send("Exito");
					}
				}
			});
	}
};

router.get('/todos', routes.mostrar_todos);
router.get('/ver/:id_candado', routes.mostrar);
router.post('/:nombre/:clave/:id_usuario', routes.crear);
router.put('/editar/:id_candado/:nombre/:alias',routes.editar);
router.delete('/eliminar/:id_candado', routes.eliminar);
router.get('/mis_candados', routes.mis_candados);
router.get('/mis_candados_compartidos', routes.mis_candados_compartidos);
router.get('/mis_candados_propios', routes.mis_candados_propios);
router.get('/usuarios_candado_compartido/:idCandado', routes.usuarios_candado_compartido);
router.put('/cambiar_alias/:idCandado/:alias', routes.cambiar_alias);
router.post('/compartir/:idUsuario/:idCandado/:administrador', routes.compartir_candado);
router.put('/dejar_de_compartir/:idUsuario/:idCandado', routes.dejar_de_compartir);
router.put('/modificar_administracion/:idUsuario/:idCandado/:administrador', routes.modificar_administracion);
module.exports = router;
