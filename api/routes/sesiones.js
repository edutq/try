const express = require('express');
const router = express.Router();
const db = require('../DB/connection.js');
const bcrypt = require('bcrypt');
const saltRounds = 10;
const User = require('../models/usuario.js');



module.exports = function (passport) {

    var routes = {

        iniciar_sesion: function (req, res, next) {
            res.json(req.user);
            console.log(req.session);
        },

        cerrar_sesion: function (req, res, next) {
            req.session.destroy();
            console.log(req.session);
            res.send("sesion cerrada");

        },
        registrar: function (req, res, next) {
            res.json(req.user);
            console.log(req.session);
        },

        all: function (req, res, next) {
            console.log(req.sessionStore);
            res.json(req.sessionStore);
        },

        validar: function (req, res, next) {
            User.getUserByTelefono(req.params.numero, function (err, user) {
				if (err) {
					res.send(err);
				} else {
                    if (user) {
                        res.json(user);
                    } else {
                        res.json({validar: false});
                    }

				}
			});
        },
        recuperar_contrasena: function (req, res, next) {

			bcrypt.hash(req.params.new_pass, saltRounds, function(err, hash) {
				if (err) {
					res.send(err.message);
				} else {

					User.recuperarPassword(req.params.numero, hash, function (err, result) {
                        if (err) {
                            res.send(err.message);
                        } else {
                            res.send("Contraseña modificada!");
                        }
                    })
				}
			});
		}



    };

    function fillBody(req, res, next) {
        //res.send(req.params.telefono);
        req.query.telefono = req.params.telefono;
        req.query.password = req.params.password;
        next();
    }



    function callPassportLogin (req, res, next) {
        passport.authenticate('local-login', function (err, user, info) {

            if (err) {
                return res.json(err.message);
            }
            if (user) {
                req.logIn (user, function(err) {
                    if (err) {
                        return res.json(err.message);
                    }
                    return next();
                })
            }
            if (info) {
                return res.json(info);
            }

        })(req, res, next);


    }

    function callPassportSignup (req, res, next) {
        passport.authenticate('local-signUp', function (err, user, info) {

            if (err) {
                return res.json(err);
            }
            if (user) {
                req.logIn (user, function(err) {
                    if (err) {
                        return res.json(err.message);
                    }
                    return next();
                })
            }
            if (info) {
                return res.json(info);
            }

        })(req, res, next);


    }

    router.post("/login/:telefono/:password", fillBody, callPassportLogin, routes.iniciar_sesion);
    router.post('/registrar/:nombre/:apellido/:telefono/:password', fillBody, callPassportSignup, routes.registrar);
    router.get("/cerrar", routes.cerrar_sesion);
    router.get("/sessions", routes.all);
    router.get("/validar/:numero", routes.validar);
    router.put('/recuperarpass/:numero/:new_pass', routes.recuperar_contrasena);
    return router;

};
