<?php

	require_once("basededatos.php");
	require_once("comprobarusuario.php");

	if(!comprobarusuario()) {
		exit("fin");
	}

	$con = conexion();

	$nombre = $_POST['nombre'];
	$ape1 = $_POST['apellido1'];
	$ape2 = $_POST['apellido2'];
	$id = $_POST['id'];

	$consulta = "update personas set nombre = '$nombre', apellido1 = '$ape1', apellido2 = '$ape2' where idpersona = '$id'";

	echo $consulta;

	if ($con->query($consulta)) {
		echo "Se ha modificado correctamente";
	} else {
		echo "No se ha modificado la persona";
	}
?>