<?php

	session_start();

	include "basededatos.php";

	$user = $_POST['usuario'];
	$passwd = md5($_POST['password']);

	$_SESSION['user'] = $user;
	$_SESSION['passwd'] = $passwd;
	$_SESSION['tiempo'] = time();

	$con = conexion();

	$consulta = "select * from usuarios where usuario = '$user'";
	$resultado = $con->query($consulta);
	$datos = $resultado->fetch_assoc();

	if ($datos['password'] == $passwd) {
		$aux = file_get_contents("entrada.html");
		$trozos = explode("##fila##", $aux);

		$consulta = "select * from personas";
		$resultado = $con->query($consulta);

		$centro = "";
		while ($datos = $resultado->fetch_assoc()) {
			$aux2 = $trozos[1];
			$aux2 = str_replace("##nombre##", $datos["nombre"], $aux2);
			$aux2 = str_replace("##apellido1##", $datos["apellido1"], $aux2);
			$aux2 = str_replace("##apellido2##", $datos["apellido2"], $aux2);
			$aux2 = str_replace("##idpersona##", $datos["idpersona"], $aux2);			
			$centro .= $aux2;
		}

		echo $trozos[0] . $centro . $trozos[2];
	} else {
		$aux = file_get_contents("mensaje.html");
		$aux = str_replace("##titulo##", "Login", $aux);
		$aux = str_replace("##mensaje##", "Error de login", $aux);
		echo $aux;
	}

?>



