<?php
    include '../frontOffice/funcionalidadesUsuario.php'; 

    session_start();

    if (isset($_SESSION['user_name'])) {
        header('Location: home.php');
        exit();
    }
    
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $username = $_POST['name'];
        $password = $_POST['password'];

        if ($username == '' || $password == '') {
            header("Location: LogIn.php?result=-3");
            exit();
        }

        $result = login($username, $password);

        header("Location: logIn.php?result=" . $result);
    }
?>
