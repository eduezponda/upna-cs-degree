<?php 

	include "basededatos.php";

	$con = conexion();

	$idcom = $_GET['idcomunidad']; 
 
	$consulta = "select * from provincias where idcomunidad = $idcom order by provincia";
	$resultado = $con->query($consulta);

	$cadena = file_get_contents("plantillaprovincias.html");
	$trozos = explode("##fila##", $cadena);

	$cuerpo = "";
	while ($datos = $resultado->fetch_assoc()) {
		$aux = $trozos[1];
		$aux = str_replace("##idprovincia##", $datos["IDProvincia"], $aux);
		$aux = str_replace("##provincia##", $datos["Provincia"], $aux);
		$cuerpo .= $aux;
	}

	echo $trozos[0] . $cuerpo . $trozos[2];






?>