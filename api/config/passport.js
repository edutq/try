var LocalStrategy = require('passport-local').Strategy;
const User = require('../models/usuario.js');
const bcrypt = require('bcrypt');
const saltRounds = 10;

module.exports = function (passport) {


    passport.serializeUser(function(user, done) {
        done(null, user.telefono);
      });

      passport.deserializeUser(function(telefono, done) {
        User.getUserByTelefono(telefono, function(err, user) {
          done(err, user);
        });
      });


    passport.use('local-login', new LocalStrategy ({

        usernameField: 'telefono',
        passwordField: 'password',
        passReqToCallback: true

    }, function (req, username, password, done) {

            User.getUserByTelefono(username, function (err, user) {

                if (err) {
                    done(err);
                } else {
                    if (user) {
                        console.log(user);
                        bcrypt.compare(password, user.password, function (err, pass) {
                            if (pass) {

                                done(null, user);
                            } else {
                                done(null, false, {message: "Teléfono y/o Constraseña Incorrecto(s)"});
                            }
                        });

                    } else {
                        done(null, false, {message: "Teléfono y/o Constraseña Incorrecto(s)"});
                    }
                }

            });
    }));

    passport.use('local-signUp', new LocalStrategy({

        usernameField: 'telefono',
        passwordField: 'password',
        passReqToCallback: true

    }, function (req, username, password, done) {

        User.createUser(username, req.params.nombre, req.params.apellido,
            password, function (err, resultado) {

                if (err) {

                    done(err);

                } else {
                    if (resultado) {
                        User.getUserByTelefono(username, function (err, user) {
                            if (err) {
                                done(err);
                            } else {
                                if (user) {
                                    done(null, user);
                                }
                            }
                        });


                    }
                }

            });

    }));

};
