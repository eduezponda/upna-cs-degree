<?php
    include_once '../basededatosAPI/basededatos.php';

    function obtenerUltimaFechaDeActualizacion(){
        $con = conexion();

        $consulta = "SELECT MAX(fecha) AS ultima_fecha FROM final_fechaActualizacion";
        $resultado = $con->query($consulta);

        $con->close();

        $row = $resultado->fetch_assoc();

        return $row['ultima_fecha'];
    }

    function recogerDatosGraficasAPI ($requerimiento){

        $con = conexion();

        $consulta = "SELECT dieta, COUNT(id_receta) AS cantidad_recetas FROM final_dieta GROUP BY dieta";
        $resultado = $con->query($consulta);
        $datosDietas = [];
        while ($fila = $resultado->fetch_assoc()) {
            $datosDietas[] = $fila;
        }

        $consulta = "SELECT cocina, COUNT(id_receta) AS cantidad_recetas FROM final_cocina GROUP BY cocina HAVING COUNT(id_receta)>2";
        $resultado = $con->query($consulta);
        $datosCocinas = [];
        while ($fila = $resultado->fetch_assoc()) {
            $datosCocinas[] = $fila;
        }

        if (strcmp($requerimiento, 'colesterol') !== 0 && strcmp($requerimiento, 'azucar') !== 0) {
            $consulta = "SELECT r.$requerimiento, AVG(c.minutos) AS minutos
                        FROM final_requerimiento r
                        JOIN final_receta rec ON rec.id_requerimiento = r.id
                        JOIN final_comida c ON c.id = rec.id_comida
                        GROUP BY r.$requerimiento
                        ORDER BY r.$requerimiento";
        }
        else {
            $consulta = "SELECT co.$requerimiento, AVG(c.minutos) AS minutos
                        FROM final_composicion co
                        JOIN final_receta rec ON rec.id_composicion = co.id
                        JOIN final_comida c ON c.id = rec.id_comida
                        GROUP BY co.$requerimiento
                        ORDER BY co.$requerimiento";
        }
        $resultado = $con->query($consulta);
        $datosMinutos = [];
        while ($fila = $resultado->fetch_assoc()) {
            $datosMinutos[] = $fila;
        }

        $con->close();

        return [
            'dietas' => $datosDietas,
            'cocinas' => $datosCocinas,
            'minutos' => $datosMinutos
        ];
    }

    function obtenerDatosReceta($id_receta) {

        $con = conexion();

        $consulta = "SELECT 
                        r.id AS receta_id,
                        r.titulo,
                        r.imagen,
                        r.resumen,
                        c.query AS comida,
                        c.minutos,
                        req.carbohidratos,
                        req.proteinas,
                        req.grasas,
                        req.calorias,
                        comp.colesterol,
                        comp.azucar,
                        d.dieta
                    FROM 
                        final_receta AS r
                    LEFT JOIN 
                        final_comida AS c ON r.id_comida = c.id
                    LEFT JOIN 
                        final_requerimiento AS req ON r.id_requerimiento = req.id
                    LEFT JOIN 
                        final_composicion AS comp ON r.id_composicion = comp.id
                    LEFT JOIN 
                        final_dieta AS d ON r.id = d.id_receta
                    WHERE 
                        r.id = ?";

        if ($stmt = $con->prepare($consulta)) {
            $stmt->bind_param("i", $id_receta);
            $stmt->execute();
            $resultado = $stmt->get_result();
            
            $datos = [];
            
            while ($fila = $resultado->fetch_assoc()) {
                $datos[] = $fila;
            }
            
            $stmt->close();

            return $datos;
        } else {
            echo "Error al recoger los datos de la receta de la base de datos";
            return null;
        }
    }

    function obtenerCocinasReceta($id_receta) {

        $con = conexion();

        $consultaCocinas = "SELECT co.cocina FROM final_cocina co WHERE co.id_receta = ?";

        if ($stmt = $con->prepare($consultaCocinas)) {
            $stmt->bind_param("i", $id_receta);
            $stmt->execute();
            $resultado = $stmt->get_result();

            $datos = [];
            
            while ($fila = $resultado->fetch_assoc()) {
                $datos[] = $fila;
            }
            
            $stmt->close();

            return $datos;
        } else {
            echo "Error al recoger los datos de cocinas de la base de datos";
            return null;
        }
    }

    function obtenerIngredientesReceta($id_receta) {
        
        $con = conexion();

        $consulta = "SELECT * FROM final_ingrediente i WHERE i.id_receta = ?";

        if ($stmt = $con->prepare($consulta)) {
            $stmt->bind_param("i", $id_receta);
            $stmt->execute();
            $resultado = $stmt->get_result();
            
            $datos = [];
            
            while ($fila = $resultado->fetch_assoc()) {
                $datos[] = $fila;
            }
            
            $stmt->close();
            
            return $datos;
        } else {
            echo "Error al recoger los ingredientes de la receta de la base de datos";
            return null;
        }
    }

    function seleccionarIdReceta() {
        $con = conexion();

        $consulta = "SELECT r.id FROM final_receta r ORDER BY RAND() LIMIT 1";
        $resultado = $con->query($consulta);

        if ($resultado->num_rows > 0) {
            $fila = $resultado->fetch_assoc();
            $idReceta = $fila['id'];
        } else {
            $idReceta = 0;
        }

        $con->close();

        return $idReceta;
    }

    function obtenerIdiomas() {
        $con = conexion();

        $consulta = "SELECT * FROM final_idioma i";
        $resultado = $con->query($consulta);

        $datos = [];
            
        while ($fila = $resultado->fetch_assoc()) {
            $datos[] = $fila;
        }

        $con->close();

        return $datos;
    }
?>
