<?php

	session_start();

	require_once("basededatos.php");
	require_once("comprobarusuario.php");

	if(!comprobarusuario()) {
		exit("fin");
	}
	
	$con = conexion();

	$id = $_POST['id'];

	$consulta = "delete from personas where idpersona = '$id'";

	if ($con->query($consulta)) {
		echo "Se ha eliminado correctamente";
	} else {
		echo "No se ha podido eliminar";
	}

	echo "El usuario es : " . $_SESSION['user'] . "<br>";
	echo "La contraseña es : " . $_SESSION['passwd'];



?>