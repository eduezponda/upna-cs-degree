<?php

	$cadena = file_get_contents("prueba05.html");

	$cadena = str_replace("##frase##", $_GET["frase"], $cadena);
	$cadena = str_replace("##frase2##", "Esta es la segunda frase", $cadena);

	echo $cadena;

?>