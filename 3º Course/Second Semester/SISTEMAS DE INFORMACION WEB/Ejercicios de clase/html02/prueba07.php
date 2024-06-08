<?php

	$cadena = file_get_contents("prueba07.html");

	$trozos = explode("##fila##", $cadena);

	$cuerpo = "";
	for($i = 0; $i < 20; $i++) {
		$aux = $trozos[1];
		$aux = str_replace("##frase##", "Esta es la frase", $aux);
		$cuerpo .= $aux;
	}

	echo $trozos[0] . $cuerpo . $trozos[2];

?>