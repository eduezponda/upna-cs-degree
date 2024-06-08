<?php

	include "basededatos.php";
	$con = conexion();

	$stmt = $con->prepare("insert into personas (nombre, apellido1, apellido2) values (?,?,?)");
	$stmt->bind_param("sss", $nom, $ape1, $ape2);

	$nom = $_POST['nombre'];
	$ape1 = $_POST['apellido1'];
	$ape2 = $_POST['apellido2'];

	$stmt->execute();

	if ($stmt->affected_rows) {
		echo "Se ha insertado correctamente<br>";
	} else {
		echo "No se ha insertado la persona<br>";
	}


?>