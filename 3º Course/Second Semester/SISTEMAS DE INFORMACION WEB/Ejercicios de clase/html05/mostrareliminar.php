<?php

	include "basededatos.php";
	require_once("comprobarusuario.php");

	if(!comprobarusuario()) {
		exit("fin");
	}

	$id = $_GET['id'];

	$cadena = file_get_contents("eliminar.html");

	$con = conexion();

	$consulta = "select * from personas where idpersona = '$id'";
	$resultado = $con->query($consulta);
	$datos = $resultado->fetch_assoc();

	$cadena = str_replace("##nombre##", $datos["nombre"], $cadena);
	$cadena = str_replace("##apellido1##", $datos["apellido1"], $cadena);
	$cadena = str_replace("##apellido2##", $datos["apellido2"], $cadena);
	$cadena = str_replace("##id##", $datos["idpersona"], $cadena);

	echo $cadena;


?>