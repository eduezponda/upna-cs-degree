<?php

	include "basededatos.php";

	/*
	Obtenemos el listado de comunidades.
	Devuelve un resultset con las comunidades
	Un -1 en caso de error en la consulta
	*/
	function listadocomunidades () {
		$con = conexion();

		$consulta = "select * from comunidadesautonomas order by ComunidadAutonoma";
		if ($resultado = $con->query($consulta)) {
			return $resultado;
		} else {
			return -1;
		}
	}

	function listadoprovincias() {
		$con = conexion();

		$idcomunidad = $_GET['idcomunidad'];
		$consulta = "SELECT * FROM provincias WHERE IDComunidad = $idcomunidad order by Provincia";
		if ($resultado = $con->query($consulta)) {
			return $resultado;
		} else {
			return -1;
		}		
	}

	function listadopoblaciones() {
		$con = conexion();

		$idprovincia = $_GET['idprovincia'];
		$buscar = $_GET['buscar'];
		$numerolineas = $_GET['numerolineas'] * 4;
		$consulta = "SELECT * FROM poblaciones WHERE IDProvincia = $idprovincia and Poblacion like '%$buscar%' order by Poblacion;
		if ($resultado = $con->query($consulta)) {
			$filastotales = ceil(mysqli_num_rows($resultado) / 4);
		} else {
			return -1;
		}		
		$consulta = "SELECT * FROM poblaciones WHERE IDProvincia = $idprovincia and Poblacion like '%$buscar%' order by Poblacion limit $numerolineas";
		if ($resultado = $con->query($consulta)) {
			return $resultado;
		} else {
			return -1;
		}		
	}




?>