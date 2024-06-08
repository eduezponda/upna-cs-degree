<?php

	include "vista.php";
	include "modelo.php";

	if (isset($_GET['accion'])) {
		$accion = $_GET['accion'];
	} else {
		$accion = "inicio";
	}

	if (isset($_GET['id'])) {
		$id = $_GET['id'];
	} else {
		$id = 1;
	}

	if ($accion == "inicio") {
		switch ($id) {
			case 1:
				montarlistadocomunidades(listadocomunidades());
				break;
		}
	}

	if ($accion == "listadoprovincias") {
		switch ($id) {
			case 1:
				montarlistadoprovincias(listadoprovincias());
				break;
		}
	}

	if ($accion == "listadopoblaciones") {
		switch ($id) {
			case 1:
				montarlistadopoblaciones(listadopoblaciones());
				break;
		}
	}



?>