<?php
    include 'basededatos.php';

    $con = conexion();

    $consulta = "DROP TABLE IF EXISTS final_usuario, final_idioma, final_fechaActualizacion;";
    $resultado = $con->query($consulta);

    $consulta = "CREATE TABLE final_idioma (
        idioma VARCHAR(25),
        clave VARCHAR(8) PRIMARY KEY
    );
    ";
    $resultado = $con->query($consulta);

    $consulta = "CREATE TABLE final_usuario (
        nombreUsuario VARCHAR(25) PRIMARY KEY,
        tipo VARCHAR(5) NOT NULL,
        passwordHash VARCHAR(255) NOT NULL,
        correo VARCHAR(50) UNIQUE,
        claveIdioma VARCHAR(8),
        FOREIGN KEY (claveIdioma) REFERENCES final_idioma(clave)
    );
    ";
    $resultado = $con->query($consulta);

    $consulta = "CREATE TABLE final_fechaActualizacion (
        fecha DATETIME PRIMARY KEY,
        numeroRecetas INT
    );
    ";
    $resultado = $con->query($consulta);

    echo "Creadas tablas usuario, idioma y fecha correctamente";

    $con->close();
?>