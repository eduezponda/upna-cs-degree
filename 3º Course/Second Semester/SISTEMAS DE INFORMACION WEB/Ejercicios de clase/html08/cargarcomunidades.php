<?php 

	include "basededatos.php";

	$con = conexion();

	$consulta = "select * from comunidadesautonomas order by comunidadautonoma";
	$resultado = $con->query($consulta);

	$cadena = file_get_contents("plantillacomunidades.html");
	$trozos = explode("##fila##", $cadena);

	$cuerpo = "";
	while ($datos = $resultado->fetch_assoc()) {
		$aux = $trozos[1];
		$aux = str_replace("##idcomunidad##", $datos["IDComunidad"], $aux);
		$aux = str_replace("##comunidad##", $datos["ComunidadAutonoma"], $aux);
		$cuerpo .= $aux;
	}

	echo $trozos[0] . $cuerpo . $trozos[2];






?>