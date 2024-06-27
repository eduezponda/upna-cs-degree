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
        $email = $_POST['email'];
        $language = $_POST['language'];

        if ($username == '' || $password == '') {
            header("Location: signUp.php?result=-3");
            exit();
        }

        $result = signUp($username, $email, $language, $password);

        header("Location: signUp.php?result=" . $result);
    }
?>
