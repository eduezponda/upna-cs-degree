<?php

	include("vista.php");
	include("modelo.php");

	//Coger variables accion e id
	if(isset($_GET['accion'])) {
		$accion = $_GET['accion'];
	} else {
		if (isset($_POST['accion'])) {
			$accion = $_POST['accion'];
		} else {
			$accion = "menu";	
		}
	}

	if(isset($_GET['id'])) {
		$id = $_GET['id'];
	} else {
		if (isset($_POST['id'])) {
			$id = $_POST['id'];
		} else {
			$id = 1;	
		}
	}

	if ($accion == "menu") {
		if ($id == 1) {
			//Mostramos el menú principal
			vmostrarmenu();
		}
	}

	if ($accion == "alta") {
		switch ($id) {
			case 1:
				// Mostramos el alta de persona
				vmostraralta(mlistadoprovincias());
				break;
			case 2:
				// Validamos el alta de persona
				vresultadoalta(mvalidaraltapersona());
				break;
		}
	}

	if ($accion == "bym") {
		switch ($id) {
			case 1: 
				//Mostramos el listado de baja y modificación
				vmostrarlistado(mlistadopersonas(), "bym");
				break;
			case 2: 
				//Mostramos modificar persona
				vmostrarmodificar(mdatospersona(), "modificar");
				break;
			case 3: 
				//Validamos la modificación de persona
				vresultadomodificar(mmodificar());
				break;
			case 4: 
				//Mostramos eliminar persona
				vmostrarmodificar(mdatospersona(), "eliminar");
				break;
			case 5: 
				//Validamos eliminar persona
				vresultadoeliminar(meliminar());
				break;
		}
	}

	if ($accion == "listado") {
		if ($id == 1) {
			//Mostramos el listado de personas
			vmostrarlistado(mlistadopersonas(), "listado");
		}
	}

	if ($accion == "altapro") {
		switch ($id) {
			case 1:
				//Mostramos el alta de provincias
				vmostraraltapro();
				break;
			
			case 2:
				//Validar alta de provincia
				vresultadoaltaprovincia(mvalidaraltaprovincia());
				break;
		}
	}

	if ($accion == "bympro") {
		switch ($id) {
			case 1: 
				vmostrarlistadoprovincias(mlistadoprovincias(), "bym");
				break;
			case 2: 
				vmostrarmodificarprovincia(mdatosprovincia(), "modificar");
				break;
			case 3: 
				break;
			case 4: 
				vmostrarmodificarprovincia(mdatosprovincia(), "eliminar");
				break;
			case 5: 
				break;
		}
	}

	if ($accion == "listadopro1") {
		if ($id == 1) {
			vmostrarlistadoprovincias(mlistadoprovincias(), "listado");
		}
	}

	if ($accion == "listadopro2") {
		if ($id == 1) {
		}
	}

?>