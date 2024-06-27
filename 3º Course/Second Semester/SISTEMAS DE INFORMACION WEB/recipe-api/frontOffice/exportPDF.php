<?php
require_once 'funcionalidadesUsuario.php';

session_start();

if (isset($_SESSION['user_name'])) {

    $nombreUsuario = $_SESSION['user_name'];
    $resumen = $_POST['resumen'];

    $titulo = $_POST['titulo'];
    $comida = $_POST['comida'];
    $imagen = $_POST['imagen'];
    $minutos = $_POST['minutos'];

    $carbohidratos = $_POST['carbohidratos'];
    $proteinas = $_POST['proteinas'];
    $grasas = $_POST['grasas'];
    $calorias = $_POST['calorias'];
    $colesterol = $_POST['colesterol'];
    $azucar = $_POST['azucar'];

    exportarInformacionPDF($nombreUsuario, $titulo, $comida, $imagen, $resumen, $minutos, $carbohidratos,
                        $proteinas, $grasas, $calorias, $colesterol, $azucar);
}
?>
