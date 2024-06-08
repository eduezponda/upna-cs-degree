<?php 

	$nombre_1 = $_POST['nombre-1'];
	$ape1_1 = $_POST['apellido1-1'];
	$ape2_1 = $_POST['apellido2-1'];
	$nombre_2 = $_POST['nombre-2'];
	$ape1_2 = $_POST['apellido1-2'];
	$ape2_2 = $_POST['apellido2-2'];
	$num = $_POST['numero'];

	$cadena = file_get_contents("plantilla03.html");
	$trozos = explode("##fila##", $cadena);

	//echo "El tamaño del array es : " . count($trozos);

	$valor = $num / 2;
	echo $trozos[0];
	for ($i = 0; $i < $num; $i++) {
		$aux = $trozos[1];
		if ($i < $valor) {
			$aux = str_replace("##color##", "#ff0000", $aux);
			$aux = str_replace("##nombre##", $nombre_1, $aux);
			$aux = str_replace("##apellido1##", $ape1_1, $aux);
			$aux = str_replace("##apellido2##", $ape2_1, $aux);				
		} else {
			$aux = str_replace("##color##", "#00ff00", $aux);
			$aux = str_replace("##nombre##", $nombre_2, $aux);
			$aux = str_replace("##apellido1##", $ape1_2, $aux);
			$aux = str_replace("##apellido2##", $ape2_2, $aux);		
		}

		echo $aux;
	}
	
	echo $trozos[2];

 ?>