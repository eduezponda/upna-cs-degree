INSERT INTO claselibro VALUES ('A',7);
INSERT INTO claselibro VALUES ('B',30);
INSERT INTO claselibro VALUES ('C',90);
INSERT INTO claselibro VALUES ('D',180);
INSERT INTO claselibro VALUES ('E',365);

SELECT *FROM CLASELIBRO;
SELECT *FROM LIBRO;
SELECT *FROM PRESTAMO;
SELECT *FROM USUARIO;

//1

INSERT INTO USUARIO (cod,nombre,apellido,edad,SEXO) VALUES ('1','Antonio','Gómez','20','V');
INSERT INTO PRESTAMO (isbn,cod,fecha_ini,fecha_fin) VALUES ('4A','1','07/12/2022',null);
INSERT INTO LIBRO (isbn,clase,titulo,autor,editorial) VALUES ('4A','A','AB',NULL,NULL);

SELECT * FROM LIBRO;

//2
 
UPDATE PRESTAMO SET (fecha_fin) = ADD_MONTHS (fecha_ini,5) WHERE COD = '1' AND ISBN = '4A';

//3

INSERT INTO PRESTAMO (isbn,cod,fecha_ini,fecha_fin) VALUES ('4A','1','12/02/2011','12/12/2015');
DELETE FROM PRESTAMO where fecha_fin < '01/11/2016';

//4

INSERT INTO USUARIO (cod,nombre,apellido,edad,SEXO) VALUES ('2','Jon','Alkorta','10','V');
//error por restriccion edad

//5
INSERT INTO LIBRO (isbn,clase,titulo,autor,editorial) VALUES ('1FBD','F','Ficheros y Bases de Datos',NULL,'Anaya');
INSERT INTO LIBRO (isbn,clase,titulo,autor,editorial) VALUES ('2FBD','F','Ficheros y Bases de Datos',null,'Anaya');
INSERT INTO LIBRO (isbn,clase,titulo,autor,editorial) VALUES ('3FBD','F','Ficheros y Bases de Datos',null,'Anaya');
INSERT INTO CLASELIBRO (clase,tiempo) VALUES ('F','15');