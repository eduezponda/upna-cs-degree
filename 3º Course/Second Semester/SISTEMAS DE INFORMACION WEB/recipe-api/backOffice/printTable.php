<?php

    require_once "../basededatosAPI/basededatos.php";

    $con = conexion();

    if (isset($_POST['username'])) {
        $username = $_POST['username'] . '%';

        $stmt = $con->prepare("SELECT nombreUsuario, correo, claveIdioma FROM final_usuario WHERE nombreUsuario LIKE ? AND nombreUsuario != 'admin'");
        $stmt->bind_param("s", $username);
        $stmt->execute();
        $resultado = $stmt->get_result();

        $cadena = file_get_contents("plantillaTabla.html");
        $trozos = explode("##fila##", $cadena);

        $cuerpo = "";
        while ($datos = $resultado->fetch_assoc()) {
            $aux = $trozos[1];
            $aux = str_replace("##nombre##", $datos["nombreUsuario"], $aux);
            $aux = str_replace("##correo##", $datos["correo"], $aux);
            $aux = str_replace("##idioma##", $datos["claveIdioma"], $aux);
            $cuerpo .= $aux;
        }

        echo $cuerpo;

        $con->close();

        exit();
    }
?>