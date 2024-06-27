<?php
  require_once "../frontOffice/funcionalidadesAPI.php";
  require_once "../frontOffice/funcionalidadesUsuario.php";
  require_once "../textTranslatorAPI/textTranslatorApi.php";
  session_start();

  if (isset($_GET['id'])) {
    $idReceta = $_GET['id'];
  }
  else {
    $idReceta = seleccionarIdReceta();
  }

  $idioma = "";
  if (isset($_SESSION['user_name'])) {
    $idioma = obtenerIdiomaUsuario($_SESSION['user_name']);
  }

  $datosReceta = obtenerDatosReceta($idReceta);
  $datosIngredientes = obtenerIngredientesReceta($idReceta);
  $datosCocinas = obtenerCocinasReceta($idReceta);

  $resumen = "";
  if ($idioma == "") {$resumen = $datosReceta[0]['resumen'];}
  else {$resumen = getTranslateText($idioma, $datosReceta[0]['resumen']);}
  
?>


<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <link
      href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
      rel="stylesheet"
    />

    <title>Mazapan Company-Recipe Detail</title>

    <link rel="icon" href="assets/images/logoMazapan.png" type="image/png" />

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="assets/css/fontawesome.css" />
    <link rel="stylesheet" href="assets/css/templatemo-villa-agency.css" />
    <link rel="stylesheet" href="assets/css/owl.css" />
    <link rel="stylesheet" href="assets/css/animate.css" />
    <link
      rel="stylesheet"
      href="https://unpkg.com/swiper@7/swiper-bundle.min.css"
    />
    <style>
      .export-pdf-container {
        display: flex;
        justify-content: center;
        margin-top: 40px;
      }
  </style>

  </head>

  <body>
    <!-- ***** Preloader Start ***** -->
    <div id="js-preloader" class="js-preloader">
      <div class="preloader-inner">
        <span class="dot"></span>
        <div class="dots">
          <span></span>
          <span></span>
          <span></span>
        </div>
      </div>
    </div>
    <!-- ***** Preloader End ***** -->

    <div class="sub-header">
      <div class="container">
        <div class="row">
          <div class="col-lg-8 col-md-8">
            <ul class="info">
              <li><i class="fa fa-envelope"></i> mazapan@company.com</li>
              <li>
                <i class="fa-regular fa-calendar"></i> Last update:
                <?php echo obtenerUltimaFechaDeActualizacion(); ?>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <!-- ***** Header Area Start ***** -->
    <header class="header-area header-sticky">
      <div class="container">
        <div class="row">
          <div class="col-12">
            <nav class="main-nav">
              <!-- ***** Logo Start ***** -->
              <a href="home.php" class="logo">
                <h1>Mazapan</h1>
              </a>
              <!-- ***** Logo End ***** -->
              <!-- ***** Menu Start ***** -->
              <ul class="nav">
                <li><a href="home.php">Home</a></li>
                <li><a href="searchByFood.php">Food</a></li>
                <li><a href="searchByDiet.php">Diet</a></li>
                <li><a href="searchByKitchen.php">Kitchen</a></li>
                <!-- ***** Add new Tab ***** -->
                <?php 
                  if (isset($_SESSION['user_name'])) {
                    echo "<li>
                      <a href='user.php'
                        ><i class='fa-solid fa-user'></i>{$_SESSION['user_name']}</a>
                    </li>";
                  }
                  else {
                    echo "<li>
                      <a href='signUp.php?result=0'
                        ><i class='fa-solid fa-lock'></i>Sign Up</a
                      >
                    </li>";
                  }
                ?>
              </ul>
            </nav>
          </div>
        </div>
      </div>
    </header>
    <!-- ***** Header Area End ***** -->

    <div class="page-heading header-text">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <span class="breadcrumb"
              ><a href="#">Home</a> / Recipe details</span
            >
            <h3><?php if (!empty($datosReceta)) {echo $datosReceta[0]['titulo'];} ?></h3>
          </div>
        </div>
      </div>
    </div>
    <?php
        if (isset($_SESSION['user_name'])){
          echo '<div class="export-pdf-container">
                <form id="export-pdf-form" action="../frontOffice/exportPDF.php" method="POST" target="_blank">
                    <input type="hidden" name="resumen" value="' . $resumen . '">
                    <input type="hidden" name="titulo" value="' . $datosReceta[0]['titulo'] . '">
                    <input type="hidden" name="comida" value="' . $datosReceta[0]['comida'] . '">
                    <input type="hidden" name="imagen" value="' . $datosReceta[0]['imagen'] . '">
                    <input type="hidden" name="minutos" value="' . $datosReceta[0]['minutos'] . '">
                    <input type="hidden" name="carbohidratos" value="' . $datosReceta[0]['carbohidratos'] . '">
                    <input type="hidden" name="proteinas" value="' . $datosReceta[0]['proteinas'] . '">
                    <input type="hidden" name="grasas" value="' . $datosReceta[0]['grasas'] . '">
                    <input type="hidden" name="calorias" value="' . $datosReceta[0]['calorias'] . '">
                    <input type="hidden" name="colesterol" value="' . $datosReceta[0]['colesterol'] . '">
                    <input type="hidden" name="azucar" value="' . $datosReceta[0]['azucar'] . '">
                    <button type="submit" class="btn btn-primary">Export to PDF</button>
                </form>
            </div>';
        }
    ?>
    <div class="single-property section">
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <div class="main-image">
              <img
                src="<?php if (!empty($datosReceta)) {echo $datosReceta[0]['imagen'];} ?>"
                alt=""
              />
            </div>
            <div class="main-content">
              <span class="category"><?php if (!empty($datosReceta)) {echo $datosReceta[0]['minutos'] . 'min';} ?></span>
              <h4><?php if (!empty($datosReceta)) {echo $datosReceta[0]['titulo'];} ?></h4>
              <p>
                <?php
                  if (!empty($datosReceta)) {
                    echo $resumen;
                  } 
                ?>
              </p>
            </div>
            <div class="accordion" id="accordionExample">
              <div class="accordion-item">
                <h2 class="accordion-header" id="headingOne">
                  <button
                    class="accordion-button"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#collapseOne"
                    aria-expanded="false"
                    aria-controls="collapseOne"
                  >
                    Nutritional information
                  </button>
                </h2>
                <div
                  id="collapseOne"
                  class="accordion-collapse collapse show"
                  aria-labelledby="headingOne"
                  data-bs-parent="#accordionExample"
                >
                  <div class="accordion-body">
                    <?php
                        if (!empty($datosReceta)) {
                          echo "Amount of carbohydrates: " . $datosReceta[0]['carbohidratos'] . "</br>";
                          echo "Amount of proteins: " . $datosReceta[0]['proteinas'] . "</br>";
                          echo "Amount of fats: " . $datosReceta[0]['grasas'] . "</br>";
                          echo "Amount of calories: " . $datosReceta[0]['calorias'] . "</br>";
                          echo "Amount of cholesterol: " . $datosReceta[0]['colesterol'] . "</br>";
                          echo "Amount of sugar: " . $datosReceta[0]['azucar'] . "</br>";    
                        }
                      ?>
                  </div>
                </div>
              </div>
              <div class="accordion-item">
                <h2 class="accordion-header" id="headingTwo">
                  <button
                    class="accordion-button collapsed"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#collapseTwo"
                    aria-expanded="false"
                    aria-controls="collapseTwo"
                  >
                    Ingredients needed
                  </button>
                </h2>
                <div
                  id="collapseTwo"
                  class="accordion-collapse collapse"
                  aria-labelledby="headingTwo"
                  data-bs-parent="#accordionExample"
                >
                  <div class="accordion-body">
                    <?php 
                      if (!empty($datosIngredientes)) {
                        foreach ($datosIngredientes as $fila) {
                          echo "- " . $fila['ingrediente'] . ": " . $fila['cantidad'] . " " . $fila['medidaCantidad'] . ".</br>";
                        }
                      }
                    ?>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-4">
            <div class="info-table">
              <ul>
                <li>
                  <img
                    src="assets/images/LogoComida.png"
                    alt=""
                    style="max-width: 52px"
                  />
                  <h4><?php if (!empty($datosReceta)) {echo $datosReceta[0]['comida'];} ?><br /><span>Base meal</span></h4>
                </li>
                <li>
                  <img
                    src="assets/images/LogoTiempoPreparacion.png"
                    alt=""
                    style="max-width: 52px"
                  />
                  <h4><?php if (!empty($datosReceta)) {echo $datosReceta[0]['minutos'] . 'min';} ?><br /><span>Preparation time</span></h4>
                </li>
                <li>
                  <img
                    src="assets/images/LogoPaisTradicional.png"
                    alt=""
                    style="max-width: 52px"
                  />
                  <h4><?php 
                    $todas_cocinas = '';
                    $datosCocinas = obtenerCocinasReceta($idReceta);

                    if (!empty($datosCocinas)) {
                      foreach ($datosCocinas as $fila) {
                          $todas_cocinas .= ", " . $fila['cocina'];
                      }
                      echo substr($todas_cocinas, 2); 
                    }
                  ?><br /><span>Kitchen type</span></h4>
                </li>
                <li>
                  <img
                    src="assets/images/LogoDieta.png"
                    alt=""
                    style="max-width: 52px"
                  />
                  <h4><?php 
                    $todas_dieta = '';

                    if (!empty($datosReceta)) {
                      foreach ($datosReceta as $fila) {
                          $todas_dieta .= ", " . $fila['dieta'];
                      }
                      echo substr($todas_dieta, 2); 
                    }
                  ?><br /><span>Compatible diet</span></h4>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>

    <footer>
      <div class="container">
        <div class="col-lg-8 footer-content">
          <img
            src="assets/images/logoMazapanWhite.png"
            alt="Logo Mazapan"
            style="max-width: 80px"
          />
          <p>Mazapan Corporate</p>
        </div>
      </div>
    </footer>

    <!-- Scripts -->
    <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/isotope.min.js"></script>
    <script src="assets/js/owl-carousel.js"></script>
    <script src="assets/js/counter.js"></script>
    <script src="assets/js/custom.js"></script>
  </body>
</html>
