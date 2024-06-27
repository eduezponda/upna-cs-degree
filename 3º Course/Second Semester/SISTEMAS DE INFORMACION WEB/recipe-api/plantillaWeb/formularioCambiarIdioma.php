<?php
    include '../frontOffice/funcionalidadesUsuario.php';

    session_start();
    
    if (!isset($_SESSION['user_name'])) {
        header('Location: home.php');
        exit();
    }
    
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $username = $_POST['username'];
        $language = $_POST['language'];

        cambiarIdioma($username, $language);
    }

    exit();
?>
