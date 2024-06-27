<?php
    session_start();

    include_once "../../basededatosAPI/basededatos.php";
    include_once "../funcionalidadesAPI.php";

    if (!isset($_SESSION['admin'])) {
        header("Location: ../../plantillaWeb/home.php");
    }

    $resumenAPI = verResumenDatosAPI();
?>
<!doctype html>
<html lang="en">
 
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Mazapan Company-Admin</title>

    <link rel="icon" href="assets/images/logoMazapanWhite.png" type="image/png" />

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <link href="assets/vendor/fonts/circular-std/style.css" rel="stylesheet">
    <link rel="stylesheet" href="assets/libs/css/style.css">
    <link rel="stylesheet" href="assets/vendor/fonts/fontawesome/css/fontawesome-all.css">

    <script src="assets/vendor/jquery/jquery-3.3.1.min.js"></script>

    <!-- jQuery UI -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css">

    <script> 
        $(document).ready(function() {
            $('#user').on('keyup', function() {
                var username = $(this).val();
                $.ajax({
                    url: "../printTable.php",
                    method: "POST",
                    dataType: 'html',
                    data: { username: username },
                    success: function(data) {
                        $('#content_table').empty().append(data);
                    }
                });
            });
        });
    </script>
</head>

<body>
    <div class="dashboard-main-wrapper">
        <div class="dashboard-header">
            <nav class="navbar navbar-expand-lg bg-white fixed-top">
                <a class="navbar-brand" href="admin.php">MAZAPAN</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse " id="navbarSupportedContent">
                    <ul class="navbar-nav ml-auto navbar-right-top">
                        <li class="nav-item dropdown nav-user">
                            <a class="nav-link nav-user-img" href="#" id="navbarDropdownMenuLink2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="assets/images/admin.jpg" alt="" class="user-avatar-md rounded-circle"></a>
                            <div class="dropdown-menu dropdown-menu-right nav-user-dropdown" aria-labelledby="navbarDropdownMenuLink2">
                                <div class="nav-user-info">
                                    <h5 class="mb-0 text-white nav-user-name"><?php echo $_SESSION['user_name']; ?></h5>
                                </div>
                                <a class="dropdown-item" href="../../plantillaWeb/user.php"><i class="fas fa-user mr-2"></i>Account</a>
                                <a class="dropdown-item" href="../../frontOffice/logout.php"><i class="fas fa-power-off mr-2"></i>Logout</a>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        
        <div class="nav-left-sidebar sidebar-dark">
            <div class="menu-list">
                <nav class="navbar navbar-expand-lg navbar-light">
                    <ul>
                        <br><br><br><br><br>
                        <img
                            src="assets/images/logoMazapanWhite.png"
                            alt="Logo Mazapan"
                            style="max-width: 150px"
                        />
                    </ul>
                </nav>
            </div>
        </div>

        <div class="dashboard-wrapper">
            <div class="container-fluid  dashboard-content">
                <div class="row">
                    <div class="card" style="display: flex; justify-content: center; width: 100%;">
                        <div class="card-body" style="display: flex; justify-content: center; width: 100%;">
                            <a href="../formularioUpdateDDBB.php" class="btn btn-rounded btn-primary" style="flex: 1; margin: 0 250px;">UPDATE DATABASE</a>
                            <a href="../formularioEmptyDDBB.php" class="btn btn-rounded btn-danger" style="flex: 1; margin: 0 250px;">EMPTY DATABASE</a>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div class="card">
                            <h5 class="card-header">Users List</h5>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <?php
                                        $con = conexion();

                                        $stmt = $con->prepare("SELECT nombreUsuario, correo, claveIdioma FROM final_usuario WHERE nombreUsuario != 'admin'");
                                        $stmt->execute();
                                        $resultado = $stmt->get_result();

                                        $cadena = file_get_contents("../plantillaTabla.html");
                                        $trozos = explode("##fila##", $cadena);

                                        $cuerpo = "";
                                        while ($datos = $resultado->fetch_assoc()) {
                                            $aux = $trozos[1];
                                            $aux = str_replace("##nombre##", $datos["nombreUsuario"], $aux);
                                            $aux = str_replace("##correo##", $datos["correo"], $aux);
                                            $aux = str_replace("##idioma##", $datos["claveIdioma"], $aux);
                                            $cuerpo .= $aux;
                                        }

                                        echo $trozos[0] . $cuerpo . $trozos[2];

                                        $con->close();
                                    ?>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xl-6 col-lg-6 col-md-12 col-sm-12 col-12">
                        <div class="section-block">
                            <h5 class="section-title">API Summary</h5>
                        </div>
                        <div class="accrodion-regular" style="width: 100%;">
                            <div id="accordion3">
                                <div class="card">
                                    <div class="card-header" id="headingOne">
                                        <h5 class="mb-0">
                                            <button class="btn btn-link" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                                <span class="fas fa-angle-down mr-3"></span>Data summary
                                            </button>
                                        </h5>
                                    </div>
                                    <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion3">
                                        <div class="card-body">
                                            <p> <?php 
                                                echo "Number of recipes: " . $resumenAPI['numeroRecetas'] . "<br>
                                                Number of diets: " . $resumenAPI['numeroDietas'] . "<br>
                                                Number of country kitchens: " . $resumenAPI['numeroCocinas'] . "<br>
                                                Mean calories: " . $resumenAPI['caloriasMedias'] . "<br>
                                                Mean cooking time: " . $resumenAPI['duracionMediaComidas'] . "<br>
                                                ";?></p>
                                        </div>
                                    </div>
                                </div>
                                <div class="card mb-2">
                                    <div class="card-header" id="headingTwo">
                                        <h5 class="mb-0">
                                            <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                                <span class="fas fa-angle-down mr-3"></span>Types of kitchen
                                            </button>
                                        </h5>
                                    </div>
                                    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion3">
                                        <div class="card-body">
                                            <?php
                                                echo "<p class='lead'>Nutritional data for each kitchen</p>
                                                <p>";

                                                foreach($resumenAPI['valorNutricionalyDuracionMediaPorCocina'] as $row) {
                                                    echo $row['cocina'] . " => Mean cooking time: " . $row['Duracion_Media'] . ", carbs: " . $row['Media_Carbohidratos'] . ", proteines: " . $row['Media_Proteinas'] . ", fat: " . $row['Media_Grasas'] . ", calories: " . $row['Media_Calorias'] . "<br>";
                                                }
                                                
                                                echo "</p>";
                                            ?>
                                        </div>
                                    </div>
                                </div>
                                <div class="card mb-2">
                                    <div class="card-header" id="headingThree">
                                        <h5 class="mb-0">
                                            <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                                <span class="fas fa-angle-down mr-3"></span>Types of diets
                                            </button>
                                        </h5>
                                    </div>
                                    <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordion3">
                                        <div class="card-body">
                                            <?php 
                                                echo "<p class='lead'>Nutritional data for each diet</p>
                                                <p>";

                                                foreach($resumenAPI['valorNutricionalyDuracionMediaPorDieta'] as $row) {
                                                    echo $row['dieta'] . " => Mean cooking time: " . $row['Duracion_Media'] . ", carbs: " . $row['Media_Carbohidratos'] . ", proteines: " . $row['Media_Proteinas'] . ", fat: " . $row['Media_Grasas'] . ", calories: " . $row['Media_Calorias'] . "<br>";
                                                }
                                                
                                                echo "</p>";
                                            ?>
                                        </div>
                                    </div>
                                </div>
                                <div class="card mb-2">
                                    <div class="card-header" id="headingFour">
                                        <h5 class="mb-0">
                                            <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                                <span class="fas fa-angle-down mr-3"></span>Ingredient info
                                            </button>
                                        </h5>
                                    </div>
                                    <div id="collapseFour" class="collapse" aria-labelledby="headingFour" data-parent="#accordion3">
                                        <div class="card-body">
                                            <?php 
                                                echo "<p class='lead'>Most common ingredients</p>
                                                <p>";
                                                foreach($resumenAPI['ingredientesMasUsados'] as $row) {
                                                    echo $row['ingrediente'] . ": " . $row['cantidad'] . " times used <br>";
                                                }
                                                echo "</p>";

                                                echo "<p class='lead'>Least common ingredients</p>
                                                <p>";
                                                foreach($resumenAPI['ingredientesMenosUsados'] as $row) {
                                                    echo $row['ingrediente'] . ": " . $row['cantidad'] . " times used <br>";
                                                }
                                                echo "</p>";
                                            ?>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="assets/vendor/bootstrap/js/bootstrap.bundle.js"></script>
    <script src="assets/vendor/slimscroll/jquery.slimscroll.js"></script>
    <script src="assets/vendor/multi-select/js/jquery.multi-select.js"></script>
    <script src="assets/libs/js/main-js.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
    
</body>
 
</html>