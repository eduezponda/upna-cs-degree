<?php 

	$nombre = $_POST['nombre'];
	$ape1 = $_POST['apellido1'];
	$ape2 = $_POST['apellido2'];
	$num = $_POST['numero'];

	$cadena = file_get_contents("plantilla02.html");
	$trozos = explode("##fila##", $cadena);

	//echo "El tamaño del array es : " . count($trozos);

echo $trozos[0];
	for ($i = 0; $i < $num; $i++) {
		
		$trozos[1] = str_replace("##nombre##", $nombre, $trozos[1]);
		$trozos[1] = str_replace("##apellido1##", $ape1, $trozos[1]);
		$trozos[1] = str_replace("##apellido2##", $ape2, $trozos[1]);	

		echo $trozos[1];
	}
	
	echo $trozos[2];

 ?>