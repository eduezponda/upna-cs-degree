/*Puebla las tablas con sufijo _E (EMPTY) que tenemos vacías en nuestra BBDD insertando en ellas los datos
que están disponibles en las tablas con prefijo BTT_MTT_ realizando lo indicado en los siguientes apartados.
Por ejemplo puebla BIKERS_E insertando los datos de la tabla BTT_MTT_BIKERS y así con el resto.
(Las tablas con prefijo BTT_MTT_ en nuestra BD simulan a tablas del esquema BTT_MTT. que tendríamos acceso)

a) primero deberás proporcionar un alias más corto y manejable para las tablas que comienzan por BTT_MTT
creando sinónimos sin prefijo, para utilizarlos para realizar las inserciones y que también puedes utilizar
durante el resto del examen. Por ejemplo, BIKERS será el sinónimo de BTT_MTT_BIKERS. Crea sinónimos para
todas y cada una de las tablas que comienzan por BTT_MTT_.*/

CREATE SYNONYM BIKERS FOR BTT_MTT_BIKERS;

CREATE SYNONYM COMUNIDADES FOR BTT_MTT_COMUNIDADES;

CREATE SYNONYM EQUIPOS FOR BTT_MTT_EQUIPOS;

CREATE SYNONYM PERTENENCIAS FOR BTT_MTT_PERTENENCIAS;

CREATE SYNONYM PARTICIPACIONES FOR BTT_MTT_PARTICIPACIONES;

CREATE SYNONYM TEMPORADAS FOR BTT_MTT_TEMPORADAS;

CREATE SYNONYM SEDES FOR BTT_MTT_SEDES;

CREATE SYNONYM CARRERAS FOR BTT_MTT_CARRERAS;


/*b) luego deberás crear varias secuencias que vamos a necesitar para poder insertar datos en las tablas vacías,
ya que hay 3 tablas de las vacías que cuentan con una clave sustituta o artifical que rellenaremos usando la
correspondiente secuencia:
- Crea una secuencia sin añadir cláusulas, con todos sus valores por defecto, para generar los COD_TEMP,
que usaremos al insertar las TEMPORADAS.
- Crea otra secuencia que se inicie en -1 sea descendente y no tenga límite inferior para generar los COD_PERTENEN,
que usaremos al insertar las PERTENENCIAS.
- Y crea otra secuencia que comience en 600, se incremente de 60 en 60, con valor máximo 6600 y que
continue generando valores cuando alcance el máximo para generar los COD_PARTICIPA, que usaremos al insertar
las PARTICIPACIONES.*/

CREATE SEQUENCE SEQ_TEMPORADAS;

CREATE SEQUENCE SEQ_PERTENENCIAS
START WITH -1
INCREMENT BY -1;

CREATE SEQUENCE SEQ_PARTICIPACIONES
START WITH 600
INCREMENT BY 60
MAXVALUE 6600
CYCLE;

/*c) finalmente puebla las tablas vacías _E, insertando los datos que tenemos en las tablas con prefijo BTT_MTT_
usando sus sinónimos. En los casos de las tablas que no tengas los correspondientes nuevos códigos a insertar
usa las secuencias creadas generando los códigos correspondientes.
Nota : Sigue el orden adecuado a la hora de rellenar las tablas para no violar las restricciones de
integridad referencial.*/

INSERT INTO COMUNIDADES_E SELECT * FROM COMUNIDADES;

INSERT INTO EQUIPOS_E SELECT * FROM EQUIPOS;

INSERT INTO BIKERS_E SELECT * FROM BIKERS;

INSERT INTO SEDES_E SELECT * FROM SEDES;

INSERT INTO TEMPORADAS_E SELECT SEQ_TEMPORADAS.NEXTVAL, ANO, SISTEMA_PUNTOS FROM TEMPORADAS;

INSERT INTO CARRERAS_E SELECT * FROM CARRERAS;

INSERT INTO PERTENENCIAS_E SELECT SEQ_PERTENENCIAS.NEXTVAL, ANO, COD_BIKER, CODIGO_EQ, ROL FROM PERTENENCIAS;

INSERT INTO PARTICIPACIONES_E SELECT SEQ_PARTICIPACIONES.NEXTVAL, COD_CARRERA, COD_BIKER, POSICION_LLEGADA, TIEMPO_LLEGADA FROM PARTICIPACIONES;


--EJERCICIO 2
/*a) Primero crea una vista CARRERAS_TIEMPO_PROMEDIO que para cada carrera muestre su nombre y el promedio de los tiempos
de llegadas entre todos sus bikers de sexo 'M' como TIEMPO_PROMEDIO.*/
CREATE OR REPLACE VIEW CARRERAS_TIEMPO_PROMEDIO AS 
SELECT CARRERAS.NOMBRE_CARRERA, AVG(TIEMPO_LLEGADA) AS TIEMPO_PROMEDIO
FROM CARRERAS
JOIN PARTICIPACIONES USING (COD_CARRERA)
JOIN BIKERS USING (COD_BIKER)
WHERE SEXO = 'M'
GROUP BY CARRERAS.NOMBRE_CARRERA;

SELECT * FROM CARRERAS_TIEMPO_PROMEDIO;

/*b) a continuación utiliza la vista CARRERAS_TIEMPO_PROMEDIO para mostrar los datos que nos devuelve la vista de la
carrera o carreras con el mayor TIEMPO_PROMEDIO.*/
SELECT * 
FROM CARRERAS_TIEMPO_PROMEDIO
WHERE TIEMPO_PROMEDIO IN (
SELECT MAX(TIEMPO_PROMEDIO)
FROM CARRERAS_TIEMPO_PROMEDIO);


--EJERCICIO 3
/*Para una integración entre dos aplicaciones se requiere cacheo, alta disponibilidad y externalización de la
lógica de negocio y se opta por compartir una tabla replicándola entre las bases de datos de forma síncrona.

A modo de entorno de pruebas prepararemos la replicación de una tabla en nuestra base de datos local, de
tal forma que cuando funcione se trasladará la solución para replicarla en la base de datos de la aplicación.

En concreto vamos a replicar la tabla BTT_MTT_SEDES, manteniendo sincronizada dicha réplica siguiendo estos pasos:

a) Crea la tabla de réplica REPLICA_SEDES a partir de la consulta de la tabla origen BTT_MTT_SEDES, añadiendo un
atributo USUARIO_REPLICA usando la función USER en la misma consulta y así comenzar con los datos inciales replicados,
tal como quede la tabla réplica (no es necesario añadir ninguna restricción de las que tiene la tabla origen).

En caso de no conseguir crea la réplica anterior del modo planteado, crea al memos la tabla
vacía ejecutando la siguiente sentencia, para poder crear el replicador:

CREATE TABLE REPLICA_SEDES (
COD_SEDE NUMBER,
NOM_SEDE VARCHAR2(8) NOT NULL,
LONGITUD NUMBER NOT NULL,
COD_COMU CHAR(2) NOT NULL,
CATEGORIA VARCHAR2(8) NOT NULL,
USUARIO_REPLICA VARCHAR2(30)
);*/

CREATE TABLE REPLICA_SEDES(
COD_SEDE NUMBER,
NOM_SEDE VARCHAR2(8) NOT NULL,
LONGITUD NUMBER NOT NULL,
COD_COMU CHAR(2) NOT NULL,
CATEGORIA VARCHAR2(8) NOT NULL,
USUARIO_REPLICA VARCHAR2(30) DEFAULT USER );

INSERT INTO REPLICA_SEDES(COD_SEDE, NOM_SEDE, LONGITUD, COD_COMU, CATEGORIA) SELECT * FROM SEDES;


/*b) Crea el replicador para que al manipular (insertar, actualizar o borrar) algún dato de la tabla origen
BTT_MTT_SEDES se produzca la misma manipulación en la tabla réplica REPLICA_SEDES, es decir:
En caso de inserción en la tabla origen BTT_MTT_SEDES: realizar la misma inserción incluyendo el usuario de la
réplica en la tabla réplica REPLICA_SEDES.
En caso de actualización en la tabla origen BTT_MTT_SEDES: realizar la misma actualización incluyendo el usuario de
la réplica en la tabla réplica REPLICA_SEDES.
En caso de eliminación en la tabla origen BTT_MTT_SEDES: realizar la misma eliminación en la tabla réplica REPLICA_SEDES.*/

CREATE OR REPLACE TRIGGER TRIGGER_SEDES
    AFTER INSERT OR DELETE OR UPDATE ON BTT_MTT_SEDES
    FOR EACH ROW
    BEGIN
        CASE
            WHEN INSERTING THEN
                INSERT INTO REPLICA_SEDES VALUES (:NEW.COD_SEDE, :NEW.NOM_SEDE, :NEW.LONGITUD, :NEW.COD_COMU, :NEW.CATEGORIA, USER);
            WHEN UPDATING THEN
                UPDATE REPLICA_SEDES SET COD_SEDE = :NEW.COD_SEDE, NOM_SEDE = :NEW.NOM_SEDE, LONGITUD = :NEW.LONGITUD, COD_COMU = :NEW.COD_COMU, CATEGORIA = :NEW.CATEGORIA, USUARIO_REPLICA = USER
                WHERE COD_SEDE = :NEW.COD_SEDE;
            WHEN DELETING THEN
                DELETE FROM REPLICA_SEDES WHERE COD_SEDE = :OLD.COD_SEDE;
        END CASE;
    END;

/*c) Comprueba que el replicador funciona correctamente con las 3 manipulaciones(insertando, actualizando y
eliminando) sobre la tabla origen BTT_MTT_SEDES. Y comprobando que se producen las mismas manipulaciones en la
tabla réplica REPLICA_SEDES.
Escribe el código que utilizas para realizar las manipulaciones y comprobaciones ya que también se evalua.

Nota: Si consigues emplear un único disparador obtendrás 0,20 ptos extra para la nota final de la prueba,
estos enlaces pueden ser de ayuda en caso de intentarlo:
https://oracle-base.com/articles/misc/database-triggers-overview
https://stackoverflow.com/questions/2965521/oracle-and-triggers-inserted-updated-deleted*/
INSERT INTO BTT_MTT_SEDES (COD_SEDE, NOM_SEDE, LONGITUD, COD_COMU, CATEGORIA) VALUES ('6', 'PAMPLONA','7000', 'NA', 'GENERAL');
UPDATE BTT_MTT_SEDES SET LONGITUD = '7500' WHERE LONGITUD = '7000';
DELETE FROM BTT_MTT_SEDES WHERE NOM_SEDE = 'PAMPLONA';


--EJERCICIO 4
/*Obtén un listado en el que aparezcan todas las sedes de la base de datos, mostrando para cada una de ellas
el nombre, la categoría y el número de las participaciones con tiempos de llegada menor que 4708
en carreras de cada sede como PARTICIPACIONES_CARRERAS.
Ordenado por PARTICIPACIONES_CARRERAS ascendentemente.
Nota: Si alguna de las sedes no tuviera participaciones que cumplan esos criterios debe aparecer en el listado con 0.*/

SELECT SEDES.NOM_SEDE, SEDES.CATEGORIA, COUNT(COD_BIKER) AS PARTICIPACIONES_CARRERAS
FROM SEDES
LEFT JOIN CARRERAS USING (COD_SEDE)
LEFT JOIN PARTICIPACIONES ON (CARRERAS.COD_CARRERA=PARTICIPACIONES.COD_CARRERA AND PARTICIPACIONES.TIEMPO_LLEGADA < '4708')
GROUP BY SEDES.NOM_SEDE, SEDES.CATEGORIA
ORDER BY PARTICIPACIONES_CARRERAS ASC;


--EJERCICIO 5
/*Muestra el código de la comunidad y el nombre en euskera de las comunidades que tengan
algún equipo dentro de las categorías ('MA-30','ELITE','F-M30') añadiendo a este primer conjunto las comunidades que
coincida que tengan alguna sede con longitud menor a 6800 metros y
algún biker nacido despúes del 02/03/82 en la misma comunidad.
Ordena el resultado por el código de la comunidad descendentemente y por el nombre en euskera ascendentemente

Nota: la resolución de este ejercicio debe incluir el uso de al menos dos operadores conjuntistas.*/

SELECT COMUNIDADES.COD_COMU, COMUNIDADES.NOMBRE_EUS
FROM COMUNIDADES
INNER JOIN EQUIPOS ON (COMUNIDADES.COD_COMU=EQUIPOS.COD_COMU)
WHERE EQUIPOS.NOMBRE_EQ IN ('MA-30','ELITE','F-M30')
UNION (
SELECT COMUNIDADES.COD_COMU, COMUNIDADES.NOMBRE_EUS
FROM COMUNIDADES
INNER JOIN SEDES ON (COMUNIDADES.COD_COMU=SEDES.COD_COMU)
WHERE SEDES.LONGITUD < 6800
INTERSECT 
SELECT COMUNIDADES.COD_COMU, COMUNIDADES.NOMBRE_EUS
FROM COMUNIDADES
INNER JOIN BIKERS ON (COMUNIDADES.COD_COMU=BIKERS.COD_COMU)
WHERE FECHA_NACIMIENTO > '02/03/82')
ORDER BY COD_COMU DESC, NOMBRE_EUS ASC;


--EJERCICIO 6
/* Queremos almacenar el número de carreras de cada temporada. Para ello, ejecuta la siguiente sentencia
ALTER TABLE BTT_MTT_TEMPORADAS ADD (num_carreras NUMBER DEFAULT 7 NOT NULL); para crear un campo num_carreras en la
tabla BTT_MTT_TEMPORADAS que sea obligatorio y con el valor 7 por defecto.
a) Actualiza el número de carreras de cada temporada con los datos que hay en este momento en la base de datos.
(Si lo haces con una sola sentencia de actualización obtendrás la máxima puntuación en este subapartado).*/

ALTER TABLE BTT_MTT_TEMPORADAS ADD (num_carreras NUMBER DEFAULT 7 NOT NULL);

UPDATE TEMPORADAS SET NUM_CARRERAS = (SELECT NUM_CARRERAS FROM
(SELECT ANO, COUNT(COD_CARRERA) AS NUM_CARRERAS
FROM TEMPORADAS FULL JOIN CARRERAS USING (ANO)
GROUP BY ANO) AUXILIAR
WHERE TEMPORADAS.ANO = AUXILIAR.ANO);

UPDATE TEMPORADAS T SET NUM_CARRERAS = (SELECT COUNT(COD_CARRERA)
										FROM TEMPORADAS
										JOIN CARRERAS USING (ANO)
										WHERE T.ANO = TEMPORADAS.ANO
										GROUP BY ANO);


/*b) Aplica una solución para que el número de carreras se actualice automáticamente para cualquier manipulación
de carreras (ya sea una inserción, una eliminación o una actualización).*/

CREATE OR REPLACE TRIGGER ACTUALIZAR
    AFTER UPDATE OR INSERT OR DELETE ON CARRERAS
    BEGIN
        UPDATE TEMPORADAS SET NUM_CARRERAS =(SELECT NUM_CARRERAS FROM
            (SELECT ANO, COUNT(COD_CARRERA)AS NUM_CARRERAS
            FROM TEMPORADAS FULL JOIN CARRERAS USING (ANO)
            GROUP BY ANO) AUXILIAR
            WHERE TEMPORADAS.ANO = AUXILIAR.ANO);
    END;
    
    
/*c) Inserta una nueva carrera y comprueba que el número de carreras de temporada se incrementa.
Cambia esa misma carrera de temporada y comprueba cómo el número de carreras de cada respectiva temporada se actualiza.
Elimina esa misma carrera a continuación y comprueba cómo el número de carreras de temporada se reduce. */

INSERT INTO CARRERAS (COD_CARRERA, ANO, FECHA_CARRERA, NOMBRE_CARRERA, VUELTAS, COD_SEDE) VALUES (6, 2021, '19/07/2021', 'Clasica Villa de Arguedas', 5, 1);
UPDATE CARRERAS SET ANO = 2020 WHERE COD_CARRERA = 6;
DELETE FROM CARRERAS WHERE COD_CARRERA = 6;
