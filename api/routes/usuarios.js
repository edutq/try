const express = require('express');
const router = express.Router();
const db = require('../DB/connection.js');
const bcrypt = require('bcrypt');
const saltRounds = 10;
const User = require('../models/usuario.js');

module.exports = function (passport) {

	var routes = {

		buscar: function (req, res, next) {
			//res.send("hola");
			User.getUserByTelefono(req.params.telefono, function (err, user) {
				if (err) {
					res.send(err);
				} else {
					res.json(user);
				}
			});
		},

		editar_nombre: function (req, res, next) {
			User.editarNombre(req.params.nombre, req.params.apellido, req.user.id, function(err, result) {
				if (err) {
					res.send(err);
				} else {
					if (result) {
						res.send("Exito");
					}
				}
			});
		},

		editar_contrasena: function (req, res, next) {

			User.getPassword(req.user.id, function (err, result) {
				if (err) {
					res.send(err.message);
				} else {
					if (result) {
						
						bcrypt.compare(req.params.current_pass, result[0][0]['password'], function (err, pass) {
							if (pass) {
								bcrypt.hash(req.params.new_pass, saltRounds, function(err, hash) {
									db.query(`CALL CAMBIAR_PASSWORD(${req.user.id}, '${hash}')`,
									function (err, results, fields) {
										if (err) {
											res.send(err.message);
										} else {
											res.send("Exito");
										}
									});
								});
	
							} else {
								res.send('La contraseña actual es incorrecta!');
							}
						});
					}
				}
			});
		},
		
		mostrar_todos: function (req, res, next) {

			User.getAll(function (err, result) {
				if (err) {
					res.json(err);
				} else {
					if (result) {
						res.json(result);
					}
				}
			});
		},

		buscar_usuarios: function (req, res, next) {

			User.buscarUsuarios(req.user.id, function (err, result) {
				if (err) {
					res.json(err);
				} else {
					if (result) {
						res.json(result);
					}
				}
			});
		}
	};

	router.put('/editar/:nombre/:apellido', routes.editar_nombre);
	router.put('/cambiarpass/:current_pass/:new_pass', routes.editar_contrasena);
	router.get('/todos', routes.mostrar_todos);
	router.get('/buscar', routes.buscar_usuarios);
	router.get('/buscar/:telefono', routes.buscar);
	return router;

};
