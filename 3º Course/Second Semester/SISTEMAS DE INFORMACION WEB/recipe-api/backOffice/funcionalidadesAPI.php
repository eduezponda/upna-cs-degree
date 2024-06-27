<?php
    include_once '../../basededatosAPI/basededatos.php';

    function obtenerUltimaFechaDeActualizacion(){
        $con = conexion();

        $consulta = "SELECT MAX(fecha) AS ultima_fecha FROM final_fechaActualizacion";
        $resultado = $con->query($consulta);

        $con->close();

        $row = $resultado->fetch_assoc();

        return $row['ultima_fecha'];
    }

    function verResumenDatosAPI(){
        $con = conexion();

        $consulta = "SELECT AVG(calorias) AS Calorias_Medias FROM final_requerimiento;";
        
        $resultado = $con->query($consulta);

        if ($row = $resultado->fetch_assoc()) {
            $caloriasMedias = intval($row['Calorias_Medias']);
        }

        $consulta = "SELECT COUNT(*) AS Numero_Recetas FROM final_receta;";
        
        $resultado = $con->query($consulta);

        if ($row = $resultado->fetch_assoc()) {
            $numeroRecetas = $row['Numero_Recetas'];
        }

        $consulta = "SELECT COUNT(DISTINCT dieta) AS Numero_Dietas FROM final_dieta;";
        
        $resultado = $con->query($consulta);

        if ($row = $resultado->fetch_assoc()) {
            $numeroDietas = $row['Numero_Dietas'];
        }

        $consulta = "SELECT COUNT(DISTINCT cocina) AS Numero_Cocinas FROM final_cocina;";
        
        $resultado = $con->query($consulta);

        if ($row = $resultado->fetch_assoc()) {
            $numeroCocinas = $row['Numero_Cocinas'];
        }

        $consulta = "SELECT AVG(minutos) AS Duracion_Media FROM final_comida;
        ";
        $resultado = $con->query($consulta);


        if ($row = $resultado->fetch_assoc()) {
            $duracionMediaComidas = intval($row['Duracion_Media']);
        }

        $consulta = "SELECT ingrediente, COUNT(*) AS cantidad
                     FROM final_ingrediente
                     GROUP BY ingrediente
                     ORDER BY Cantidad DESC
                     LIMIT 15;
        ";
        $resultado = $con->query($consulta);

        $ingredientesMasUsados = [];
        while ($row = $resultado->fetch_assoc()) {
            $ingredientesMasUsados[] = $row;
        }

        $consulta = "SELECT ingrediente, COUNT(*) AS cantidad
                     FROM final_ingrediente
                     GROUP BY ingrediente
                     ORDER BY Cantidad ASC
                     LIMIT 15;
        ";
        $resultado = $con->query($consulta);

        $ingredientesMenosUsados = [];
        while ($row = $resultado->fetch_assoc()) {
            $ingredientesMenosUsados[] = $row;
        }

        $consulta = "SELECT c.cocina,
                            CAST(ROUND(AVG(com.minutos)) AS UNSIGNED) AS Duracion_Media,
                            CAST(ROUND(AVG(r.carbohidratos)) AS UNSIGNED) AS Media_Carbohidratos,
                            CAST(ROUND(AVG(r.proteinas)) AS UNSIGNED) AS Media_Proteinas,
                            CAST(ROUND(AVG(r.grasas)) AS UNSIGNED) AS Media_Grasas,
                            CAST(ROUND(AVG(r.calorias)) AS UNSIGNED) AS Media_Calorias
                     FROM final_cocina AS c
                     JOIN final_receta AS rec ON c.id_receta = rec.id
                     JOIN final_requerimiento AS r ON rec.id_requerimiento = r.id
                     JOIN final_comida AS com ON rec.id_comida = com.id
                     GROUP BY c.cocina;
        ";
        $resultado = $con->query($consulta);

        $valorNutricionalyDuracionMediaPorCocina = [];
        while ($row = $resultado->fetch_assoc()) {
            $valorNutricionalyDuracionMediaPorCocina[] = $row;
        }

        $consulta = "SELECT d.dieta,
                            CAST(ROUND(AVG(com.minutos)) AS UNSIGNED) AS Duracion_Media,
                            CAST(ROUND(AVG(r.carbohidratos)) AS UNSIGNED) AS Media_Carbohidratos,
                            CAST(ROUND(AVG(r.proteinas)) AS UNSIGNED) AS Media_Proteinas,
                            CAST(ROUND(AVG(r.grasas)) AS UNSIGNED) AS Media_Grasas,
                            CAST(ROUND(AVG(r.calorias)) AS UNSIGNED) AS Media_Calorias
                     FROM final_dieta AS d
                     JOIN final_receta AS rec ON d.id_receta = rec.id
                     JOIN final_requerimiento AS r ON rec.id_requerimiento = r.id
                     JOIN final_comida AS com ON rec.id_comida = com.id
                     GROUP BY d.dieta;
        ";
        $resultado = $con->query($consulta);

        $valorNutricionalyDuracionMediaPorDieta = [];
        while ($row = $resultado->fetch_assoc()) {
            $valorNutricionalyDuracionMediaPorDieta[] = $row;
        }

        $con->close();

        return [
            'caloriasMedias' => $caloriasMedias,
            'numeroRecetas' => $numeroRecetas,
            'numeroCocinas' => $numeroCocinas,
            'numeroDietas' => $numeroDietas,
            'duracionMediaComidas' => $duracionMediaComidas,
            'ingredientesMasUsados' => $ingredientesMasUsados,
            'ingredientesMenosUsados' => $ingredientesMenosUsados,
            'valorNutricionalyDuracionMediaPorCocina' => $valorNutricionalyDuracionMediaPorCocina,
            'valorNutricionalyDuracionMediaPorDieta' => $valorNutricionalyDuracionMediaPorDieta
        ];
    }
?>
