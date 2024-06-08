<?php 

	include "basededatos.php";

	$con = conexion();

	$idprov = $_GET['idprovincia']; 
	$texto = $_GET['texto'];
 
	$consulta = "select * from poblaciones where idprovincia = $idprov and poblacion like '%$texto%' order by poblacion";
	$resultado = $con->query($consulta);

	$cadena = file_get_contents("plantillapoblaciones.html");
	$trozos = explode("##fila##", $cadena);

	$cuerpo = "";
	while ($datos = $resultado->fetch_assoc()) {
		$aux = $trozos[1];
		$aux = str_replace("##idpoblacion##", $datos["IDPoblacion"], $aux);
		$aux = str_replace("##poblacion##", $datos["Poblacion"], $aux);
		$cuerpo .= $aux;
	}

	echo $trozos[0] . $cuerpo . $trozos[2];






?>