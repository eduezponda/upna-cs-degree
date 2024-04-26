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

SELECT * FROM PRESTAMO;

//2
 
UPDATE PRESTAMO SET (fecha_fin) = ADD_MONTHS (fecha_ini,5) WHERE COD = '1' AND ISBN = '4A';

//3

INSERT INTO PRESTAMO (isbn,cod,fecha_ini,fecha_fin) VALUES ('4A','1','12/02/2011','12/12/2015');
DELETE FROM PRESTAMO where fecha_fin < '01/11/2016';

//4

INSERT INTO USUARIO (cod,nombre,apellido,edad,SEXO) VALUES ('2','Jon','Alkorta','10','V');
//error por restriccion edad

//5
INSERT INTO LIBRO (isbn,clase,titulo,autor,editorial) VALUES ('1FBD','F','Ficheros y Bases de Datos',NULL,'Anaya'); //tengo que cambiar nombre libro por restricción
INSERT INTO LIBRO (isbn,clase,titulo,autor,editorial) VALUES ('2FBD','F','Ficheros 2 y Bases de Datos',null,'Anaya');
INSERT INTO LIBRO (isbn,clase,titulo,autor,editorial) VALUES ('3FBD','F','Ficheros 3 y Bases de Datos',null,'Anaya');
INSERT INTO CLASELIBRO (clase,tiempo) VALUES ('F','15');

//6

UPDATE CLASELIBRO SET (tiempo) = tiempo + 15 where clase = 'B';

//7

DELETE FROM PRESTAMO WHERE cod = '1'
DELETE FROM USUARIO where cod = '1'; //eliminar primero prestamo ya que prestamo depende de usuario, pero usuario es independiente

//8

INSERT INTO USUARIO (cod,nombre,apellido,edad,SEXO) VALUES ('1','Rosa','Pérez',null,'M');
INSERT INTO USUARIO (cod,nombre,apellido,edad,SEXO) VALUES ('2','Lucas','Pérez',null,'V');
INSERT INTO USUARIO (cod,nombre,apellido,edad,SEXO) VALUES ('3','Antonio','Gómez',null,'V';)
INSERT INTO PRESTAMO (isbn,cod,fecha_ini,fecha_fin) VALUES ('1FBD','1','27/10/2020','11/11/2020');
INSERT INTO PRESTAMO (isbn,cod,fecha_ini,fecha_fin) VALUES ('2FBD','2','27/10/2020','11/11/2020');
INSERT INTO PRESTAMO (isbn,cod,fecha_ini,fecha_fin) VALUES ('3FBD','3','27/10/2020','11/11/2020');

//9

UPDATE PRESTAMO set fecha_fin = '07/11/2022' where cod = '1';
UPDATE PRESTAMO set fecha_fin = '07/11/2022' where cod = '2';
UPDATE PRESTAMO set fecha_fin = '07/11/2022' where cod = '3';

//10

DELETE FROM PRESTAMO where fecha_fin IS NOT NULL ;

//11
DELETE FROM USUARIO Where cod is not null; //antes se ha eliminado los prestamos
INSERT INTO USUARIO (cod,nombre,apellido,edad,SEXO) VALUES ('1','Alberto','Vizcay','22','V');
INSERT INTO USUARIO (cod,nombre,apellido,edad,SEXO) VALUES ('2','Pedro','Agos','23','V');
INSERT INTO USUARIO (cod,nombre,apellido,edad,SEXO) VALUES ('3','Luis','Alonso','22','V');
INSERT INTO USUARIO (cod,nombre,apellido,edad,SEXO) VALUES ('4','Juan','Arrondo','65','V');
INSERT INTO USUARIO (cod,nombre,apellido,edad,SEXO) VALUES ('5','Maria','Ayape','13','M'); //no te deja por la edad

//12

INSERT INTO LIBRO (isbn,clase,titulo,autor,editorial) VALUES ('5A','A','Álgebra 5',null,'mcgraw-hill');
INSERT INTO LIBRO (isbn,clase,titulo,autor,editorial) VALUES ('6C','B','Cálculo 1A',null,'mcgraw-hill');
INSERT INTO LIBRO (isbn,clase,titulo,autor,editorial) VALUES ('7A','C','Cálculo 2B',null,'mcgraw-hill');
INSERT INTO LIBRO (isbn,clase,titulo,autor,editorial) VALUES ('20A','B','Inteligencia Artificial',null,'mcgraw-hill');

//13

INSERT INTO PRESTAMO (isbn,cod,fecha_ini,fecha_fin) VALUES ('20A','2','15/04/2020',NULL);
INSERT INTO PRESTAMO (isbn,cod,fecha_ini,fecha_fin) VALUES ('19A','5','05/11/2020','10/11/2020'); //no se inserta porque no existe esa isbn
INSERT INTO PRESTAMO (isbn,cod,fecha_ini,fecha_fin) VALUES ('5A','1','06/09/2020','07/09/2020');
INSERT INTO PRESTAMO (isbn,cod,fecha_ini,fecha_fin) VALUES ('6C','2','07/09/2020',NULL);
INSERT INTO PRESTAMO (isbn,cod,fecha_ini,fecha_fin) VALUES ('7A','3','08/09/2020','11/09/2020');
INSERT INTO PRESTAMO (isbn,cod,fecha_ini,fecha_fin) VALUES ('5A','4','09/09/2020','13/09/2020');

//14

UPDATE PRESTAMO SET fecha_fin = SYSDATE where isbn = '20A' and cod = '2';  //sysdate = dia actual
UPDATE PRESTAMO SET fecha_fin = SYSDATE where isbn = '6C' and cod = '2';

//15

DELETE FROM PRESTAMO;
DELETE FROM LIBRO;
DELETE FROM CLASELIBRO;
DELETE FROM USUARIO;

SELECT *FROM CLASELIBRO;
SELECT *FROM LIBRO;
SELECT *FROM PRESTAMO;
SELECT *FROM USUARIO;