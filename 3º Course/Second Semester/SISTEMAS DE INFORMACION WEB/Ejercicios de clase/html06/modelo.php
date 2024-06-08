<?php

	include "basededatos.php";

	function mvalidaraltapersona() {
		$con = conexion();
		$nom = $_POST['nombre'];
		$ape1 = $_POST['apellido1'];
		$ape2 = $_POST['apellido2'];
		$consulta = "insert into personas (nombre, apellido1, apellido2) values ('$nom','$ape1','$ape2')";
		if ($con->query($consulta)) {
			return 1;
		} else {
			return -1;
		}
	}

	function mlistadopersonas() {
		$con = conexion();
		$consulta = "select * from personas order by nombre, apellido1, apellido2";
		if ($resultado = $con->query($consulta)) {
			return $resultado;
		} else {
			return -1;
		}
	}

	function mdatospersona() {
		$con = conexion();
		$idpersona = $_GET['idpersona'];
		$consulta = "select * from personas where idpersona = '$idpersona'";
		if ($resultado = $con->query($consulta)) {
			return $resultado;
		} else {
			return -1;
		}
	}
	
	function meliminar() {
		$con = conexion();
		$idpersona = $_POST['idpersona'];
		$consulta = "delete from personas where idpersona = '$idpersona'";
		return $con->query($consulta);
	}

	function mmodificar() {
		$con = conexion();
		$idpersona = $_POST['idpersona'];
		$nom = $_POST['nombre'];
		$ape1 = $_POST['apellido1'];
		$ape2 = $_POST['apellido2'];
		$consulta = "update personas set nombre = '$nom', apellido1 = '$ape1', apellido2 = '$ape2' where idpersona = '$idpersona'";
		return $con->query($consulta);
	}

	/*
	Función encargada de dar de alta una provincia
	Valores devueltos:
		1 --> Se ha añadido correctamente la provincia
		-1 --> La provincia ya existe en la base de datos
		-2 --> Error en la consulta contra la base de datos al insertar
	*/
	function mvalidaraltaprovincia() {
		$con = conexion();
		$provincia = $_POST['provincia'];

		$consulta = "select idprovincia from provincias where provincia = '$provincia'";
		$resultado = $con->query($consulta);
		if ($datos = $resultado->fetch_assoc()) {
			//Existe
			return -1;
		} else {
			//No existe
			$consulta = "insert into provincias (provincia) values ('$provincia')";
			if ($resultado = $con->query($consulta)) {
				//Se ha insertado correctamente
				return 1;
			} else {
				//Error al añadir la provincia
				return -2;
			}
		}
	}

	function mlistadoprovincias() {
		$con = conexion();
		$consulta = "select * from provincias order by provincia";
		if ($resultado = $con->query($consulta)) {
			return $resultado;
		} else {
			return -1;
		}
	}

	function mdatosprovincia() {
		$con = conexion();
		$idprovincia = $_GET['idprovincia'];
		$consulta = "select * from provincias where idprovincia = '$idprovincia'";
		if ($resultado = $con->query($consulta)) {
			return $resultado;
		} else {
			return -1;
		}
	}

?>







