const express = require('express');
const router = express.Router();
const twilio = require('twilio');
const accountSID = 'AC6979ecab7ab572d28296166f0e89cce6';
const authToken = '54c529db23603d08b85fcca226e9abe9';
const client = new twilio(accountSID, authToken);
const User = require('../models/usuario.js');


/*client.messages.create({
	body: 'probando',
	to: '+524424588186',
	from: '+18327802197'
})
.then((message) => console.log(message.sid));*/

function pedir_codigo(SID, req, res) {
	client.verify.services(SID)
             .verifications
             .create({to: req.params.numero, channel: 'sms'})
             .then(verification => res.send(verification.sid))
			 .catch(err => res.send(err));
}

function confirmar_codigo(SID, req, res){
	client.verify.services(SID)
      .verificationChecks
      .create({to: req.params.numero, code: req.params.codigo})
      .then( function (verification_check) {
		  if (verification_check.valid == true) {
			  User.activar(req.params.numero, function (err, result) {
				  if (err) {
					  res.send("Error al activar la cuenta\n Intente de nuevo");
				  } else {
					  res.send('código aceptado');
				  }
			  });


		  } else {
			  res.send('código incorrecto');
		  }
		  
	  })
	  .catch( function (err) {
		if (err.code == 60200) {
			res.send("El codigo debe ser de 6 dígitios");
		}
		else if (err.code == 60203) {
			res.send("A acabado con el limite de intentos. Espere un momento y vuelva a solicitar un código");
		}
		else if (err.code == 20404) {
			res.send("El código ha expirado, solicite uno nuevo");
		}
		res.send(err)
	  });
}

var routes = {

	pedir_codigo_activar: function (req, res, next) {
		pedir_codigo('VA5dd38329aafed48966f3a5a051886744', req, res);
	},
	confirmar_codigo_activar: function (req, res, next) {
		confirmar_codigo('VA5dd38329aafed48966f3a5a051886744', req, res);
	},
	pedir_codigo_recuperar: function (req, res, next) {
		pedir_codigo('VAb4cd24494000b5eac8c7a4937408c449', req, res);

	},
	confirmar_codigo_recuperar: function (req, res, next) {
		confirmar_codigo('VAb4cd24494000b5eac8c7a4937408c449', req, res);
	}
};

router.get('/solicitar/activar/:numero', routes.pedir_codigo_activar);
router.post('/confirmar/activar/:numero/:codigo', routes.confirmar_codigo_activar);
router.get('/solicitar/recuperar/:numero', routes.pedir_codigo_recuperar);
router.post('/confirmar/recuperar/:numero/:codigo', routes.confirmar_codigo_recuperar);

module.exports = router;
