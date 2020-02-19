/*CREATE TABLE rol (
  id int NOT NULL AUTO_INCREMENT,
  nombre varchar(15),
  PRIMARY KEY (id)
);

CREATE TABLE usuario (
  id int NOT NULL AUTO_INCREMENT,
  numero varchar(10) NOT NULL UNIQUE,
  rol int,
  nombre varchar(30),
  apellido varchar(30),
  password varchar(255),
  PRIMARY KEY (id),
  FOREIGN KEY (rol) REFERENCES rol(id)
);

CREATE TABLE candado (
  id int NOT NULL AUTO_INCREMENT,
  nombre varchar(30),
  alias varchar(30),
  dueno int,
  estado tinyint(1),
  PRIMARY KEY (id),
  FOREIGN KEY (dueno) REFERENCES usuario(id)
);



CREATE TABLE permisos_de_apertura (
  id int NOT NULL AUTO_INCREMENT,
  fecha_inicio DATETIME,
  fecha_fin DATETIME,
  PRIMARY KEY (id)
);

    CREATE TABLE candado_usuario (
      id_usuario int,
      id_candado int,
      FOREIGN KEY (id_usuario) REFERENCES usuario(id),
      FOREIGN KEY (id_candado) REFERENCES candado(id),
      PRIMARY KEY (id_usuario, id_candado),
    );

CREATE TABLE permisos_de_rol (
  id int NOT NULL AUTO_INCREMENT,
  nombre varchar(15),
  PRIMARY KEY (id)
);

CREATE TABLE permisos_rol (
  id_permiso int,
  id_rol int,
  FOREIGN KEY (id_permiso) REFERENCES permisos_de_rol(id),
  FOREIGN KEY (id_rol) REFERENCES rol(id)
);


CREATE TABLE permisos_usuario_candado (
  id_permiso int,
  id_usuario_candado int,
  id int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id),
  FOREIGN KEY (id_permiso) REFERENCES permisos_de_apertura(id),
  FOREIGN KEY (id_usuario_candado) REFERENCES candado_usuario(id)
);

*/
