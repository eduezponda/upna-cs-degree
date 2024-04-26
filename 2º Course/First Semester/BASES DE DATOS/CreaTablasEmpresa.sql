--primero creamos la tabla departamento ya q en empleado se referencia
PROMPT creando tabla departamento
CREATE TABLE departamento(
	cod_dep		CHAR(3),
	nom_dep		VARCHAR2(15),
	jefe		CHAR(4),
	fecha_jefe	DATE,
CONSTRAINT PK_departamento PRIMARY KEY (cod_dep),
CONSTRAINT unique_nomdep UNIQUE(nom_dep)
);

PROMPT creando tabla empleado
CREATE TABLE empleado(
	cod_emp		CHAR(4),
	supervisor	CHAR(4),
	nombre		VARCHAR2(10),
	apellido	VARCHAR2(10),
	fecha_ncto	DATE,
	genero		VARCHAR2(1),
	salario		NUMBER(6),
	cod_dep		CHAR(3),
CONSTRAINT PK_empleado PRIMARY KEY (cod_emp),
CONSTRAINT FK_emp_cod_dep FOREIGN KEY (cod_dep) REFERENCES departamento (cod_dep) ON DELETE CASCADE, --supongo q si desapare un departamento es porq los empleados seran despedidos--
CONSTRAINT FK_emp_sup FOREIGN KEY (supervisor) REFERENCES empleado (cod_emp) ON DELETE SET NULL, --supongo que si se elimina un supervisor los supervisados se qedan sin supervisor--
CONSTRAINT CH_genero CHECK(genero IN ('V','M')));

--ahora añadimos la restriccion de foreign key de jefe de departamento una vez tnemos las dos tablas, sino no podremos crearlas--
PROMPT añadiendo Constraint a departamento
ALTER TABLE departamento ADD (CONSTRAINT FK_dep_jefe FOREIGN KEY (jefe) REFERENCES empleado (cod_emp) ON DELETE SET NULL);
 --supongo q si desapare un jefe de los empleados el departamento pierde el jefe--
 
PROMPT creando tabla dependiente
CREATE TABLE DEPENDIENTE
(
	nombre		VARCHAR2(25),
	cod_emp		CHAR(4),
	fecha_ncto	DATE NOT NULL,
	parentesco	VARCHAR2(25),
	genero		VARCHAR2(1),
CONSTRAINT PK_dependiente PRIMARY KEY (cod_emp, nombre),
CONSTRAINT CH_dependiente_genero CHECK (genero IN ('V','M')),
CONSTRAINT FK_dependiente_empleado FOREIGN KEY (cod_emp) REFERENCES empleado (cod_emp) ON DELETE CASCADE);
--si desaparece el empleado desapacen sus parientes

PROMPT creando tabla localizaciones
CREATE TABLE localizaciones(
	cod_dep		CHAR(3),
	localiza	VARCHAR2(1),
CONSTRAINT PK_localizaciones PRIMARY KEY (localiza, cod_dep),
CONSTRAINT FK_loc_cod_dep FOREIGN KEY (cod_dep) REFERENCES departamento (cod_dep) ON DELETE CASCADE);
--supongo q si desapare una departamento desaparece la localizacion--

PROMPT creando tabla proyecto
CREATE TABLE proyecto(
	num_proy	NUMBER(2),
	nom_proy	VARCHAR2(6),
	cod_dep		CHAR(3),
	localiza	VARCHAR2(1),
CONSTRAINT PK_proyecto PRIMARY KEY (num_proy),
CONSTRAINT unique_nomproy UNIQUE(nom_proy),

CONSTRAINT FK_proy_cod_dep FOREIGN KEY (cod_dep) REFERENCES departamento (cod_dep) ON DELETE CASCADE);
--supongo q si desapare una departamento desaparece sus proyectos--

PROMPT creando tabla trabaja
CREATE TABLE trabaja(
	cod_emp		CHAR(4),
	num_proy	NUMBER(2),
	horas		NUMBER(3),
CONSTRAINT PK_trabaja PRIMARY KEY (cod_emp, num_proy),
CONSTRAINT FK_trabaja_cod_emp FOREIGN KEY (cod_emp) REFERENCES empleado (cod_emp) ON DELETE CASCADE, --Supongo q si eliminamos un trbajador nos qeuremos olvidar de todos sus trabajos--
CONSTRAINT FK_trabaja_num_proy FOREIGN KEY (num_proy) REFERENCES proyecto (num_proy) ON DELETE CASCADE);
--Supongo que si un proyecto se elimina (se da por terminado) se quiere eliminar toda su informacion, para dejar sitio a nuevos proyectos--
