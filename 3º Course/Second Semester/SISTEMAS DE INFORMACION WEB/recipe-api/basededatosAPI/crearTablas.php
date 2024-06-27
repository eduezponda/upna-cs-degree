<?php
    include 'basededatos.php';

    $con = conexion();

    $consulta = "DROP TABLE IF EXISTS final_cocina, final_dieta, final_receta, final_requerimiento, final_composicion, final_comida, final_ingrediente;";
    $resultado = $con->query($consulta);

    $consulta = "CREATE TABLE final_comida (
        id INT AUTO_INCREMENT PRIMARY KEY,
        query VARCHAR(50),
        minutos INT
    );
    ";
    $resultado = $con->query($consulta);

    $consulta = "CREATE TABLE final_requerimiento (
        id INT AUTO_INCREMENT PRIMARY KEY,
        carbohidratos INT,
        proteinas INT,
        grasas INT,
        calorias INT
    );
    ";
    $resultado = $con->query($consulta);

    $consulta = "CREATE TABLE final_composicion (
        id INT AUTO_INCREMENT PRIMARY KEY,
        colesterol INT,
        azucar INT
    );
    ";
    $resultado = $con->query($consulta);

    $consulta = "CREATE TABLE final_receta (
        id INT AUTO_INCREMENT PRIMARY KEY,
        titulo VARCHAR(100) NOT NULL,
        imagen VARCHAR(255),
        resumen VARCHAR(10000),
        id_requerimiento INT,
        id_composicion INT,
        id_comida INT,
        FOREIGN KEY (id_requerimiento) REFERENCES final_requerimiento(id),
        FOREIGN KEY (id_composicion) REFERENCES final_composicion(id),
        FOREIGN KEY (id_comida) REFERENCES final_comida(id)
    );
    ";
    $resultado = $con->query($consulta);

    $consulta = "CREATE TABLE final_cocina (
        id_receta INT,
        cocina VARCHAR(50),
        PRIMARY KEY (id_receta, cocina),
        FOREIGN KEY (id_receta) REFERENCES final_receta(id)
    );
    ";
    $resultado = $con->query($consulta);

    $consulta = "CREATE TABLE final_dieta (
        id_receta INT,
        dieta VARCHAR(50),
        PRIMARY KEY (id_receta, dieta),
        FOREIGN KEY (id_receta) REFERENCES final_receta(id)
    );
    ";
    $resultado = $con->query($consulta);

    $consulta = "CREATE TABLE final_ingrediente (
        id_receta INT,
        ingrediente VARCHAR(75),
        cantidad FLOAT,
        medidaCantidad VARCHAR(15),
        PRIMARY KEY (id_receta, ingrediente),
        FOREIGN KEY (id_receta) REFERENCES final_receta(id)
    );
    ";
    $resultado = $con->query($consulta);

    echo 'Tablas creadas correctamente';

    $con->close();
?>
