<?php
    include_once 'funcionalidadesAdmin.php'; 

    session_start();
    
    if (!isset($_SESSION['admin'])) {
        header('Location: ../plantillaWeb/home.php');
        exit();
    }
    
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $username = $_POST['username'];

        eliminarUsuario($username);
    }

    header('Location: PlantillaBackOffice/admin.php');
    exit();
?>
