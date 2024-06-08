<?php

	function mostrarmensaje($titulo, $mensaje1, $mensaje2) {
		$cadena = file_get_contents("mensaje.html");

		$cadena = str_replace("##titulo##", $titulo, $cadena);
		$cadena = str_replace("##mensaje1##", $mensaje1, $cadena);
		$cadena = str_replace("##mensaje2##", $mensaje2, $cadena);				
		echo $cadena;
	}

	function montarlistadocomunidades($resultado) {
		if (!is_object($resultado)) {
			//Tenemos un error. Mostramos un mensaje
			mostrarmensaje("Listado de comunidades", "Se ha producido un error", "Contacta con el administrador");
		} else {
			//Mostrar el listado de provincias
			$listado = file_get_contents("listado.html");
			$trozos = explode("##fila##", $listado);

			$cuerpo = "";
			while ($datos = $resultado->fetch_assoc()) {
				$aux = $trozos[1];
				$aux = str_replace("##comunidad##", $datos["ComunidadAutonoma"], $aux);
				$aux = str_replace("##idcomunidad##", $datos["IDComunidad"], $aux);

				$cuerpo .= $aux;
			}

			echo $trozos[0] . $cuerpo . $trozos[2];
		}
		
	}

	function montarlistadoprovincias ($resultado) {
		if (!is_object($resultado)) {
			//Tenemos un error. Mostramos un mensaje
			mostrarmensaje("Listado de provincias", "Se ha producido un error", "Contacta con el administrador");
		} else {
			$cadena = file_get_contents("plantillaprovincias.html");

			$cuerpo = "";
			while ($datos = $resultado->fetch_assoc()) {
				$aux = $cadena;

				$aux = str_replace("##provincia##", $datos["Provincia"], $aux);
				$aux = str_replace("##idprovincia##", $datos["IDProvincia"], $aux);

				$cuerpo .= $aux;
			}

			echo $cuerpo;
		}
	}

	function montarlistadopoblaciones($resultado) {
		if (!is_object($resultado)) {
			//Tenemos un error. Mostramos un mensaje
			mostrarmensaje("Listado de poblaciones", "Se ha producido un error", "Contacta con el administrador");
		} else {
			$poblacionestotales = mysqli_num_rows($resultado);
			$lineastotales = $poblacionestotales / 4;
			$cadena = file_get_contents("plantillapoblaciones.html");

			$cadena = str_replace("##paginafinal##", $poblacionestotales, $cadena);

			$idprovincia = $_GET['idprovincia'];
			$cadena = str_replace("##idprovincia##", $idprovincia, $cadena);

			//Vamos a completar el desplegable
			$numerolineas = $_GET['numerolineas'];
			if ($numerolineas == 10) {
				$cadena = str_replace("##selected10##", "selected", $cadena);
			}
			if ($numerolineas == 20) {
				$cadena = str_replace("##selected20##", "selected", $cadena);
			}
			if ($numerolineas == 30) {
				$cadena = str_replace("##selected30##", "selected", $cadena);
			}
			$cadena = str_replace("##selected10##", "", $cadena);
			$cadena = str_replace("##selected20##", "", $cadena);
			$cadena = str_replace("##selected30##", "", $cadena);


			$trozos = explode("##fila##", $cadena);

			$cuerpo = "";
			$posicion = 1;
			$linea = 0;
			while ($datos = $resultado->fetch_assoc()) {
				if ($posicion == 1) {
					$aux = $trozos[1];
				}
				
				$aux = str_replace("##poblacion" .  $posicion . "##", $datos["Poblacion"], $aux);

				$posicion++;
				if ($posicion == 5) {
					$cuerpo .= $aux;
					$posicion = 1;
					$linea++;
				}
				
				if ($linea % 2 == 0) {
					//Gris
					$aux = str_replace("##color##", "#aaaaaa", $aux);
				} else {
					//Blanco
					$aux = str_replace("##color##", "#FFFFFF", $aux);
				}
			}
			if ($posicion != 1) {
				$aux = str_replace("##poblacion1##", "", $aux);
				$aux = str_replace("##poblacion2##", "", $aux);
				$aux = str_replace("##poblacion3##", "", $aux);
				$aux = str_replace("##poblacion4##", "", $aux);
				$cuerpo .= $aux;	
			}

			echo $trozos[0] . $cuerpo . $trozos[2];

		}
	}


?>