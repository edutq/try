const express = require('express');
const router = express.Router();
const Historial = require('../models/historial.js');

var routes = {
    registrar_historial: function (req, res, next) {

        Historial.registrar(req.user.id, req.params.idCandado, req.params.idEvento, function (err, result) {
            if (err) {
                res.json({error : err.message});
            } else {
                if (result) {
                    res.json({success: true});
                }
            }
        }) ;
        
    },
    registrar_historial_offline: function (req, res, next) {

        Historial.registrarOffline(req.params.fecha, req.params.idUsuario, req.params.idCandado, req.params.idEvento, function (err, result) {
            if (err) {
                res.json({error : err.message});
            } else {
                if (result) {
                    res.json({success: true});
                }
            }
        }) ;
        
    },
    mi_historial: function (req, res, next) {
        Historial.miHistorial(req.user.id, function (err, result) {
            if (err) {
                res.json({error: err.message});
            } else {
                if (result) {
                    res.json(result);
                }
            }
        });
    },
    mi_historial_por_fecha: function (req, res, next) {
        Historial.miHistorialPorFecha(req.user.id, req.params.fechaInicio, req.params.fechaFin, function (err, result) {
            if (err) {
                res.json({error: err.message});
            } else {
                if (result) {
                    res.json(result);
                }
            }
        });
    }
}

router.post('/registrar/:idCandado/:idEvento', routes.registrar_historial);
router.post('/registrar_offline/:fecha/:idUsuario/:idCandado/:idEvento', routes.registrar_historial);
router.get('/mi_historial', routes.mi_historial);
router.get('/mi_historial_por_fecha/:fechaInicio/:fechaFin', routes.mi_historial_por_fecha);

module.exports = router;