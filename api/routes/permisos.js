const express = require('express');
const router = express.Router();

var routes = {
	
	mostrar: function (req, res, next) {
		res.send('mostrar permiso');
	},
	crear: function (req, res, next) {
		res.send('crear permiso');
	},
	editar: function (req, res, next) {
		res.send('editar permiso');
	},
	eliminar: function (req, res, next) {
		res.send('eliminar permiso')
	},
	mostrar_todos: function (req, res, next) {
		res.send('mostrar todos los permisos');
	},
	asignar: function (req, res, next) {
		res.send('asignar permiso a usuario');
	},
	remover: function (req, res, next) {
		res.send('remover permiso a usuario');
	},
	mostrar_otorgados: function (req, res, next) {
		res.send('mostrar permisos otorgados');
	},
	mis_permisos: function (req, res, next) {
		res.send('mostroar permisos asignados');
	}
	
};

router.get('/ver/:id', routes.mostrar);
router.post('/:idcandado/:idhorarios', routes.crear);
router.put('/editar/:id/:idcandado/:idhorarios', routes.editar);
router.delete('/eliminar/:id', routes.eliminar);
router.get('/', routes.mostrar_todos);
router.put('/asignar/:iduser/:idpermiso', routes.asignar);
router.delete('/remover/:iduser/:idpermiso', routes.remover);
router.get('/otorgados/:idusuario');
router.get('/asignados/:iduser');

module.exports = router;