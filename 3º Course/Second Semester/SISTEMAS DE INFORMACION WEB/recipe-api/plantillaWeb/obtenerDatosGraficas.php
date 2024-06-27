<?php
    include '../frontOffice/funcionalidadesAPI.php'; 

    header('Content-Type: application/json');

    $list_requerimientos = ['colesterol', 'azucar', 'calorias', 'grasas', 'proteinas', 'carbohidratos'];

    if (isset($_GET['index'])) {
        $index = intval($_GET['index']);
        $datosGraficas = recogerDatosGraficasAPI($list_requerimientos[$index]);
        echo json_encode($datosGraficas);
    } else {
        echo json_encode(['error' => 'No index provided']);
    }
?>
