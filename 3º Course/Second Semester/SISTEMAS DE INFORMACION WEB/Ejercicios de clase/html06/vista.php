<?php

	function vmostrarmensaje($titulo, $mensaje1, $mensaje2) {
		$cadena = file_get_contents("mensaje.html");
		$cadena = str_replace("##titulo##", $titulo, $cadena);
		$cadena = str_replace("##mensaje1##", $mensaje1, $cadena);
		$cadena = str_replace("##mensaje2##", $mensaje2, $cadena);
		echo $cadena;
	}

	function vmostrarmenu() {
		echo file_get_contents("menu.html");
	}

	function vmostraralta($resultado) {
		if (!is_object($resultado)) {
			vmostrarmensaje("Alta de persona", "Hay un problema con la base de datos", "Vuelva a intentarlo");
		} else {
			$cadena = file_get_contents("alta.html");
			$trozos = explode("##fila##", $cadena);

			$cuerpo = "";
			while ($datos = $resultado->fetch_assoc()) {
				$aux = $trozos[1];
				$aux = str_replace("##idprovincia##", $datos["idprovincia"], $aux);
				$aux = str_replace("##provincia##", $datos["provincia"], $aux);			
				$cuerpo .= $aux;
			}
			echo $trozos[0] . $cuerpo . $trozos[2];			
		}
	}

	function vresultadoalta($resultado) {
		if ($resultado == 1) {
			//Se ha dado de alta
			vmostrarmensaje("Alta de persona", "Se ha dado de alta correctamente", "");
		} else {
			//No se ha dado de alta
			vmostrarmensaje("Alta de persona", "No se ha dado de alta la persona", "Vuelva a intentarlo");	
		}
	}

	function vmostrarlistado($resultado, $tipo) {
		if (is_object($resultado)) {
			if ($tipo == "listado") {
				$cadena = file_get_contents("listado.html");	
			} else {
				$cadena = file_get_contents("listadobym.html");
			}
			
			$trozos = explode("##fila##", $cadena);
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
		} else {
			vmostrarmensaje("Listado de personas", "Se ha producido un error en el listado (Numero: 1489)", "Vuelva a intentarlo");
		}
	}

	function vmostrarmodificar($resultado, $tipo) {
		if (is_object($resultado)) {
			if ($tipo == "modificar") {
				$cadena = file_get_contents("modificar.html");	
			} else {
				$cadena = file_get_contents("eliminar.html");
			}
			
			$datos = $resultado->fetch_assoc();
			$cadena = str_replace("##nombre##", $datos["nombre"], $cadena);
			$cadena = str_replace("##apellido1##", $datos["apellido1"], $cadena);
			$cadena = str_replace("##apellido2##", $datos["apellido2"], $cadena);
			$cadena = str_replace("##idpersona##", $datos["idpersona"], $cadena);
			echo $cadena;
		} else {
			vmostrarmensaje("Modificación de persona", "Se ha producido un error", "Vuevla a intentarlo");
		}
	}

	function vresultadomodificar($resultado) {
		if ($resultado) {
			vmostrarmensaje("Modificación de persona", "Se ha modificado correctamente", "");
		} else {
			vmostrarmensaje("Modificación de persona", "No se ha podido modificar", "Vuelva a intentarlo");
		}
	}

	function vresultadoeliminar($resultado) {
		if ($resultado) {
			vmostrarmensaje("Eliminación de persona", "Se ha eliminado correctamente", "");
		} else {
			vmostrarmensaje("Eliminación de persona", "No se ha podido eliminar", "Vuelva a intentarlo");
		}
	}

	function vmostraraltapro() {
		echo file_get_contents("altaprovincia.html");
	}

	function vresultadoaltaprovincia($resultado) {
		if ($resultado == 1) {
			//Se ha dado de alta
			vmostrarmensaje("Alta de provincia", "Se ha dado de alta correctamente", "");
		} else {
			if ($resultado == -1) {
				//No se ha dado de alta
				vmostrarmensaje("Alta de provincia", "Ya existe la provincia indicada", "Vuelva a intentarlo");
			} else {
				//No se ha dado de alta
				vmostrarmensaje("Alta de provincia", "Se ha producido un error en la base de datos", "Vuelva a intentarlo");
			}
	
		}
	}

	function vmostrarlistadoprovincias($resultado, $tipo) {
		if (is_object($resultado)) {
			if ($tipo == "listado") {
				$cadena = file_get_contents("listadoprovincias.html");	
			} else {
				$cadena = file_get_contents("listadobymprovincias.html");
			}
			
			$trozos = explode("##fila##", $cadena);
			$cuerpo = "";
			while ($datos = $resultado->fetch_assoc()) {
				$aux = $trozos[1];
				$aux = str_replace("##provincia##", $datos["provincia"], $aux);
				$aux = str_replace("##idprovincia##", $datos["idprovincia"], $aux);
				$cuerpo .= $aux;								
			}
			echo $trozos[0] . $cuerpo . $trozos[2];
		} else {
			vmostrarmensaje("Listado de provincias", "Se ha producido un error en el listado", "Vuelva a intentarlo");
		}
	}

	function vmostrarmodificarprovincia($resultado, $tipo) {
		if (is_object($resultado)) {
			if ($tipo == "modificar") {
				$cadena = file_get_contents("modificarprovincia.html");	
			} else {
				$cadena = file_get_contents("eliminarprovincia.html");
			}
			
			$datos = $resultado->fetch_assoc();
			$cadena = str_replace("##provincia##", $datos["provincia"], $cadena);
			$cadena = str_replace("##idprovincia##", $datos["idprovincia"], $cadena);
			echo $cadena;
		} else {
			vmostrarmensaje("Modificación de provincia", "Se ha producido un error", "Vuevla a intentarlo");
		}
	}

?>




