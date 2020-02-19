const express = require('express');
const router = express.Router();
const candadoUsuario = require('../models/candadoUsuario.js');

var routes = {
	otorgar_permiso: function (req, res, next) {
        
        candadoUsuario.otorgarPermiso(req.params.fecha_in, req.params.hora_in, req.params.fecha_f,
                                        req.params.hora_f, req.params.idUsuario, req.params.idCandado, req.user.id, 
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
    ver_permisos: function (req, res, next) {

        candadoUsuario.verPermisos(req.params.idUsuario, req.params.idCandado, function (err, result) {
            if (err) {
                res.json({error: err.message});
            } else {
                res.json(result[0])
            }
        });
    },
    eliminar_permiso: function (req, res, next) {

        candadoUsuario.eliminarPermiso(req.params.idPermiso, req.user.id, function(err, result) {
            if (err) {
                res.send(err.message);
            } else {
                if (result) {
                    res.send("Exito");
                }
            }
        });
    },
    editar_permiso: function (req, res, next) {

        candadoUsuario.editarPermiso(req.params.fecha_in, req.params.hora_in, req.params.fecha_f,
                                        req.params.hora_f, req.params.idPermiso, req.params.idCandado,
                                         req.user.id, 
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
    mis_permisos: function (req, res, next) {

        candadoUsuario.misPermisos(req.user.id, function (err, result) {
            if (err) {
                res.json({error: err.message});
            } else {
                res.json(result[0])
            }
        });
    }
};

router.post('/otorgar_permiso/:fecha_in/:hora_in/:fecha_f/:hora_f/:idUsuario/:idCandado', routes.otorgar_permiso);
router.get('/ver_permisos/:idUsuario/:idCandado', routes.ver_permisos);
//router.get('/mis_permisos/:idCandado', routes.mis_permisos);
router.get('/mis_permisos', routes.mis_permisos);
router.delete('/eliminar_permiso/:idPermiso', routes.eliminar_permiso);
router.put('/editar_permiso/:fecha_in/:hora_in/:fecha_f/:hora_f/:idPermiso/:idCandado', routes.editar_permiso);

module.exports = router;
