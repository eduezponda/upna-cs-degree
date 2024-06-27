<?php
    include_once 'funcionalidadesAdmin.php'; 

    session_start();
    
    if (!isset($_SESSION['admin'])) {
        header('Location: ../plantillaWeb/home.php');
        exit();
    }
    
    borrarDatosAPI();

    header('Location: PlantillaBackOffice/admin.php');
    exit();
?>
