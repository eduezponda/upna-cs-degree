<?php
    session_start();

    if (!isset($_SESSION['admin'])) {
        header("Location: ../../plantillaWeb/home.php");
        exit();
    }

    $username = "";
    if (isset($_GET['username'])){
        $username = $_GET['username'];
    }
?>

<html lang="en">
 
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Mazapan Company-Change Password</title>

    <link rel="icon" href="assets/images/logoMazapanWhite.png" type="image/png" />
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <link href="assets/vendor/fonts/circular-std/style.css" rel="stylesheet">
    <link rel="stylesheet" href="assets/libs/css/style.css">
    <link rel="stylesheet" href="assets/vendor/fonts/fontawesome/css/fontawesome-all.css">
    <style>
    html,
    body {
        height: 100%;
    }

    body {
        display: -ms-flexbox;
        display: flex;
        -ms-flex-align: center;
        align-items: center;
        padding-top: 40px;
        padding-bottom: 40px;
    }
    </style>
</head>

<body>
    <!-- ============================================================== -->
    <!-- login page  -->
    <!-- ============================================================== -->
    <div class="splash-container">
        <div class="card ">
            <div class="card-header text-center"><a href="admin.php"><img class="logo-img" src="assets/images/mazapanTitle.png" alt="logo"></a><span class="splash-description">Confirm user delete</span></div>
            <div class="card-body">
                <form action="../formularioeliminarUsuario.php" method="POST">
                    <input name="username" value="<?php echo $username; ?>" hidden>
                    <button type="submit" class="btn btn-primary btn-lg btn-block">Delete</button>
                </form>
            </div>
        </div>
    </div>
  
    <!-- ============================================================== -->
    <!-- end login page  -->
    <!-- ============================================================== -->
    <!-- Optional JavaScript -->
    <script src="assets/vendor/jquery/jquery-3.3.1.min.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.bundle.js"></script>
</body>
 
</html>