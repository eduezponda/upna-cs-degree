<?php
    include '../frontOffice/funcionalidadesUsuario.php'; 

    session_start();
    
    if (!isset($_SESSION['user_name'])) {
        header('Location: home.php');
        exit();
    }
    
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $username = $_POST['username'];
        $newPassword = $_POST['newPassword'];
        $verificateSamePassword = $_POST['verificateSamePassword'];

        if ($newPassword == '' || $verificateSamePassword == '' || strcmp($verificateSamePassword, $newPassword) !== 0){
            header('Location: user.php?result=-1');
            exit();
        }

        cambiarContrasena($username, $username, $newPassword);
    }

    exit();
?>
