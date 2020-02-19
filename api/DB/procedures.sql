
/*CREATE DEFINER=`root`@`localhost` PROCEDURE `CAMBIAR_PASSWORD`(IN id_usuario int, IN newpass varchar(60))
BEGIN
	UPDATE usuario SET usuario.password = newpass where usuario.id = id_usuario;
END*/






/*CREATE DEFINER=`root`@`localhost` PROCEDURE `CREAR_USUARIO`(IN nombre varchar(20), IN apellido_p varchar(15), IN apellido_m varchar(15), IN pass varchar (60), IN numero varchar(15))
BEGIN
    insert into usuario (numero, rol, nombre, apellido_p, apellido_m, password)     values (numero, 2, nombre, apellido_p, apellido_m, pass);
END*/






/*CREATE DEFINER=`root`@`localhost` PROCEDURE `EDITAR_USUARIO`(IN nombre varchar(20), IN apellido_p varchar(15), IN apellido_m varchar(15), IN id_usuario int, OUT mensaje varchar(50))
BEGIN
	UPDATE usuario SET usuario.nombre = nombre, usuario.apellido_p = apellido_p, usuario.apellido_m = apellido_m where usuario.id = id_usuario;
    SET mensaje = "Información editada";
END*/


/*CREATE DEFINER=`root`@`localhost` PROCEDURE `ELIMINAR_CANDADO`(IN id_candado int, OUT mensaje varchar(50))
BEGIN
	DELETE FROM candado WHERE id = id_candado;
    SET mensaje = "Candado eliminado";
END*/


/*CREATE DEFINER=`root`@`localhost` PROCEDURE `ES_DUENO`(IN id_usuario int, OUT dueno tinyint(1))
BEGIN
	SELECT EXISTS(select candado.dueno from candado where candado.dueno = id_usuario) INTO dueno;
END*/



/*CREATE DEFINER=`root`@`localhost` PROCEDURE `FETCH_CANDADOS_USUARIO`(IN id_usuario int)
BEGIN
	select candado.id, candado.nombre, candado.alias, candado.estado, candado.dueno from candado join candado_usuario where candado_usuario.id_usuario = id_usuario  AND candado.id = candado_usuario.id_candado;
    
END*/


/*CREATE DEFINER=`root`@`localhost` PROCEDURE `FETCH_ROL`(IN id_usuario int, OUT rol int)
BEGIN
	select usuario.rol INTO rol from usuario where usuario.id = id_usuario;
END*/






/*CREATE DEFINER=`root`@`localhost` PROCEDURE `TIENE_PERMISO`(IN id_permiso int, IN id_usuario int, OUT permiso tinyint)
BEGIN
	DECLARE rol int;
	CALL FETCH_ROL(id_usuario, rol);
	select EXISTS(select * from permisos_rol where permisos_rol.id_permiso = id_permiso AND permisos_rol.id_rol = rol) INTO permiso;
END*/


/*CREATE DEFINER=`root`@`localhost` PROCEDURE `TODOS_LOS_CANDADOS`()
BEGIN
	SELECT * FROM candado;
END*/


/*********************************************************************************************/

/*



CREATE DEFINER=`root`@`localhost` PROCEDURE `EDITAR_CANDADO`(IN id int, IN nombre varchar(30), IN alias varchar(150), OUT mensaje varchar(50))
BEGIN
	UPDATE candado SET candado.nombre = nombre, candado.alias = alias WHERE candado.id = id;
    SET mensaje = "Candado modificado";
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `CREAR_CANDADO`(IN id_usuario int, IN nombre varchar(30), IN clave varchar(60), OUT mensaje varchar(50))
BEGIN
	DECLARE permiso tinyint;
	CALL TIENE_PERMISO(9, id_usuario, permiso);
    IF permiso = 1 THEN
		INSERT INTO candado (nombre, clave) values (nombre, clave);
        SET mensaje = "Candado creado con éxito";
	ELSE
		SET mensaje = "NO TIENES PERMISO PARA CREAR UN CANDADO!";
    END IF;
END

*/