<?php

	if (isset($_GET["frase1"]) && isset($_GET["frase2"]) && isset($_GET["frase3"])) {
		$cadena = file_get_contents("prueba08.html");

		$trozos = explode("##fila##", $cadena);

		$cuerpo = "";
		for($i = 0; $i < 20; $i++) {
			$aux = $trozos[1];
			$aux = str_replace("##numero##", $i, $aux);
			$aux = str_replace("##frase1##", $_GET["frase1"], $aux);
			$aux = str_replace("##frase2##", $_GET["frase2"], $aux);
			$aux = str_replace("##frase3##", $_GET["frase3"], $aux);
			$cuerpo .= $aux;
		}

		echo $trozos[0] . $cuerpo . $trozos[2];		
	} else {
		echo file_get_contents("error08.html");
	}


?>