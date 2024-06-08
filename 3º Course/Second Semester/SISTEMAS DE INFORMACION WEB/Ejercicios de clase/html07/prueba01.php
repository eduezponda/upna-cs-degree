<?php 

	include("basededatos.php");

	$valor = $_GET['numero'];
	if ($valor == 1) {
		$cadena = file_get_contents("plantilla01.html");	
	} else {
		$cadena = file_get_contents("plantilla02.html");
	}
	
	$trozos = explode("##fila##", $cadena);

	$con = conexion();

	$consulta = "select * from personas";

	$resultado = $con->query($consulta);

	$cuerpo = "";
	while ($datos = $resultado->fetch_assoc()) {
		$aux = $trozos[1];
		$aux = str_replace("##nombre##", $datos["nombre"], $aux);
		$aux = str_replace("##apellido1##", $datos["apellido1"], $aux);
		$aux = str_replace("##apellido2##", $datos["apellido2"], $aux);
		$aux = str_replace("##idpersona##", $datos["idpersona"], $aux);
		$cuerpo .= $aux;
	}

	echo $trozos[0] . $cuerpo . $trozos[2];
?>



