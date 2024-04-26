CREATE TABLE usuario(
cod		VARCHAR2(10),
nombre		VARCHAR2(15),
apellido	VARCHAR2(15) NOT NULL,
edad		NUMBER(2) ,
SEXO		VARCHAR2(1) DEFAULT 'M',
CONSTRAINT PK_usuario PRIMARY KEY (cod),
CONSTRAINT CH_EDAD CHECK(EDAD>14), 
CONSTRAINT CH_SEXO CHECK(SEXO IN ('V','M')) 
);

CREATE TABLE claselibro(
clase		VARCHAR2(1),
tiempo		NUMBER(5),
CONSTRAINT CH_clases CHECK (clase IN('A','B','C','D','E') ),
CONSTRAINT PK_claselibro PRIMARY KEY (clase)
);

CREATE TABLE libro(
isbn		VARCHAR2(10),
clase		VARCHAR2(1),
titulo		VARCHAR2(35) NOT NULL,
autor		VARCHAR2(15),
editorial	VARCHAR2(25),
CONSTRAINT PK_libro PRIMARY KEY (isbn),
CONSTRAINT Unica_libro UNIQUE (titulo),
CONSTRAINT FK_libro FOREIGN KEY (clase) REFERENCES claselibro (clase)
);

CREATE TABLE prestamo(
isbn		VARCHAR2(10),
cod		VARCHAR2(10),
fecha_ini	DATE NOT NULL,
fecha_fin	DATE,
CONSTRAINT PK_prestamo PRIMARY KEY (isbn,cod,fecha_ini),
CONSTRAINT FK1_prestamo FOREIGN KEY (isbn) REFERENCES libro (isbn),
CONSTRAINT FK2_prestamo FOREIGN KEY (cod) REFERENCES usuario(cod),
CONSTRAINT CH_FECHA CHECK(FECHA_FIN>FECHA_INI)
);

// primary key = de donde sale la flecha
// foreign key = a donde llega la flecha
// puede haber distintas primary keys, pero todas en el mismo parentesis
// para borrar todos los datos de las tablas, primero borrar los de las foreign key