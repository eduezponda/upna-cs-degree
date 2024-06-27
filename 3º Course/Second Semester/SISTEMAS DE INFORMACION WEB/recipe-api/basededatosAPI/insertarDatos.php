<?php
    include_once 'basededatos.php';
    include_once '../recipeAPI/recipeFoodApi.php';
    include_once '../textTranslatorAPI/textTranslatorAPI.php';

    function insertarReceta($receta, $con, $query){
        $stmt = $con->prepare( "select id from final_comida where query = ? and minutos = ?");
        $stmt->bind_param("si", $query, $receta['readyInMinutes']);
        $stmt->execute();
        $idComida = $stmt->get_result();

        if($idComida->num_rows === 0) {
            $consulta = "insert into final_comida (query, minutos) values ('$query', " . $receta['readyInMinutes'] . ")";
            if (!$con->query($consulta)) {
                echo "Error al ejecutar la inserción de comida: " . $con->error . "<br>";
            }
            $con->query($consulta);
            $idComida = $con->insert_id;
        }
        else {
            $row = $idComida->fetch_assoc();
            $idComida = $row['id'];
        }

        $rangos = [25, 5, 5, 5, 10, 1];
        $valores = [$receta['nutrition']['nutrients'][0]['amount'], $receta['nutrition']['nutrients'][1]['amount'],
                    $receta['nutrition']['nutrients'][2]['amount'], $receta['nutrition']['nutrients'][3]['amount'],
                    $receta['nutrition']['nutrients'][4]['amount'], $receta['nutrition']['nutrients'][5]['amount'],
        ];
        $valoresMinimos = [0, 0, 0, 0, 0, 0];

        for ($i = 0; $i < count($rangos); $i++){
            $valoresMinimos[$i] = intdiv($valores[$i], $rangos[$i])*$rangos[$i];
        }

        $stmt = $con->prepare("select id from final_requerimiento where calorias = ? and proteinas = ? 
        and grasas = ? and carbohidratos = ?");
        $stmt->bind_param("dddd", $valoresMinimos[0], $valoresMinimos[1], $valoresMinimos[2], $valoresMinimos[3]);
        $stmt->execute();
        $idRequerimiento = $stmt->get_result();

        if ($idRequerimiento->num_rows === 0){
            $consulta = "insert into final_requerimiento (calorias, proteinas, grasas, carbohidratos) values 
                                ($valoresMinimos[0], $valoresMinimos[1], $valoresMinimos[2], $valoresMinimos[3])";
            if (!$con->query($consulta)) {
                echo "Error al ejecutar la inserción de requerimiento: " . $con->error . "<br>";
            }
            $idRequerimiento = $con->insert_id;
        }
        else {
            $row = $idRequerimiento->fetch_assoc();
            $idRequerimiento = $row['id'];
        }

        $stmt = $con->prepare("select id from final_composicion where azucar = ? and colesterol = ?");
        $stmt->bind_param("dd", $valoresMinimos[5], $valoresMinimos[4]);
        $stmt->execute();
        $idComposicion = $stmt->get_result();

        if ($idComposicion->num_rows === 0){
            $consulta = "insert into final_composicion (colesterol, azucar) values ($valoresMinimos[4], $valoresMinimos[5])";
            if (!$con->query($consulta)) {
                echo "Error al ejecutar la inserción de composición: " . $con->error . "<br>";
            }
            $idComposicion = $con->insert_id;
        }
        else {
            $row = $idComposicion->fetch_assoc();
            $idComposicion = $row['id'];
        }

        $summaryWithoutElementsB = preg_replace_callback('/<b>(.*?)<\/b>/', function($matches) {
            return $matches[1];
        }, $receta['summary']);
        
        $summaryWithoutElementsBandA = preg_replace('/<a.*?>(.*?)<\/a>/', '$1', $summaryWithoutElementsB);
        
        $consulta = "insert into final_receta (titulo, imagen, resumen, id_requerimiento, id_composicion, id_comida) values
                                        ('" . str_replace("'", " ", $receta['title']) . "','" . $receta['image'] . "','" 
                                        . str_replace("'", " ", $summaryWithoutElementsBandA) . "',$idRequerimiento, $idComposicion, $idComida)";
        if (!$con->query($consulta)) {
            echo "Error al ejecutar la inserción de receta: " . $con->error . "<br>";
        }

        $idReceta = $con->insert_id;

        foreach ($receta['cuisines'] as $cuisine) {
            $consulta = "insert into final_cocina (id_receta, cocina) values ($idReceta, '$cuisine')";
            if (!$con->query($consulta)) {
                echo "Error al ejecutar la inserción de cocina: " . $con->error . "<br>";
            }
        }

        foreach ($receta['diets'] as $diet) {
            $consulta = "insert into final_dieta (id_receta, dieta) values ($idReceta, '$diet')";
            if (!$con->query($consulta)) {
                echo "Error al ejecutar la inserción de dieta: " . $con->error . "<br>";
            }
        }


        foreach ($receta['extendedIngredients'] as $infoIngrediente) {
            $ingrediente = str_replace("'", " ", $infoIngrediente['name']);
            
            $cantidad = $infoIngrediente['measures']['metric']['amount'];
            $medidaCantidad = $infoIngrediente['measures']['metric']['unitLong'];

            $stmt = $con->prepare("select * from final_ingrediente where id_receta = ? and ingrediente = ?");
            $stmt->bind_param("is", $idReceta, $ingrediente);
            $stmt->execute();
            $resultQuery = $stmt->get_result();

            if ($resultQuery->num_rows === 0){
                $consulta = "insert into final_ingrediente (id_receta, ingrediente, cantidad, medidaCantidad) values ($idReceta, '$ingrediente', $cantidad, '$medidaCantidad')";
                if (!$con->query($consulta)) {
                    echo "Error al ejecutar la inserción de ingrediente: " . $con->error . "<br>";
                }
            }
        }
    }

    /*$con = conexion();

    $comidas = [
        'pasta', 'rice', 'chicken', 'vegetable', 'salad', 'pizza', 'burguer', 'tacos', 'sushi',
        'omelette', 'hot dog', 'paella', 'fish', 'meat', 'lasagna', 'meatballs', 'apple pie',
        'kebab', 'steak', 'tomato', 'bb', 'spaghetti', 'chiles', 'empanada', 'lentil', 'spring rolls',
        'beef', 'pork', 'soup', 'spinach', 'potato', 'calamari', 'fajitas', 'toast', 'burrito', 'biscuit',
        'cake', 'pie'
    ];

    foreach($comidas as $comida) {
        $data = json_decode(getRecipes($comida), true);

        if(isset($data['results'])) 
        {
            foreach($data['results'] as $receta) 
            {
                insertarReceta($receta, $con, $comida);
            }
        } 
        else 
        {
            echo "No results found.";
        }
    }

    echo "Datos insertados en las tablas correctamente";

    if (isset($con)) {
        $con->close();
    }*/
?>
