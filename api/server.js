const express = require('express');
const session = require('express-session');
const MySQLStore = require('express-mysql-session')(session);
var cookieParser = require('cookie-parser')
const flash = require("connect-flash");

const app = express();
const port =  process.env.PORT || 3000;
const passport = require("passport");



//app.use(cookieParser())

//conexion con base de datos
const db = require('./DB/connection.js');

var sessionStore = new MySQLStore({}, db);

app.use(session({
    secret: 'SkyGuardian',
    store: sessionStore,
    resave: false,
    saveUninitialized: false
}));

//PASSPORT SESSION
require("./config/passport.js")(passport);
app.use(passport.initialize());
app.use(passport.session());

app.use(flash());

//RUTAS
const usuario = require('./routes/usuarios')(passport);
const permiso = require('./routes/permisos');
const candado = require('./routes/candado');
const sms = require('./routes/sms');
const sesiones = require('./routes/sesiones')(passport);
const historial = require('./routes/historial');
const candado_usuario = require('./routes/candadoUsuario');

function isLoggedIn(req, res, next) {
    if(req.isAuthenticated()) {
        console.log(req.sessionID);
        next();
    } else {
        message = {err: true};
        res.send(message);
    }
  }

//aplicacion de rutas
app.get('/skylock', (req, res) => res.send('hello world'));
app.use('/skylock/usuario', isLoggedIn, usuario);
app.use('/skylock/permiso', isLoggedIn, permiso);
app.use('/skylock/candado', isLoggedIn, candado);
app.use('/skylock/historial', isLoggedIn, historial);
app.use('/skylock/candado_usuario', isLoggedIn, candado_usuario);
app.use('/skylock/sesion', sesiones);
app.use('/skylock/sms', sms);


app.listen(port);
