<?php
  require_once "../frontOffice/funcionalidadesAPI.php";
  session_start();

  $result = 0;

  if (isset($_GET['result'])) {
    $result = $_GET['result'];
  }

  if (isset($_SESSION['user_name'])) {
    header('Location: home.php');
    exit();
  }
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

    <title>Mazapan Company-Log In</title>

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
                <li><a href="searchByFood.php">Search by Food</a></li>
                <li><a href="searchByDiet.php">Search by Diet</a></li>
                <li><a href="searchByKitchen.php">Search by Kitchen</a></li>
                <!-- ***** Add new Tab ***** -->
                <li>
                  <a href='signUp.php?result=0'
                    ><i class='fa-solid fa-lock'></i>Sign Up</a
                  >
                </li>
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
            <span class="breadcrumb"><a href="#">Sign Up</a> / Log In</span>
            <h3>Log In</h3>
          </div>
        </div>
      </div>
    </div>

    <div class="contact-page section">
      <div class="container">
        <div class="row">
          <div class="col-lg-6">
            <form id="contact-form" action="formularioLogin.php" method="post">
              <div class="row">
                <div class="col-lg-12">
                  <fieldset>
                    <label for="name">Username</label>
                    <input
                      type="name"
                      name="name"
                      id="name"
                      placeholder="Your Username..."
                      autocomplete="on"
                      required
                    />
                    <?php
                      if ($result == -1)
                      {
                        echo '<p style="color: red;">The username is not correct</p>';
                      }
                    ?>
                  </fieldset>
                </div>
                <div class="col-lg-12">
                  <fieldset>
                    <label for="password">Password</label>
                    <input
                      type="password"
                      name="password"
                      id="password"
                      placeholder="Your password..."
                      required
                    />
                    <?php
                      if ($result == -2)
                      {
                        echo '<p style="color: red;">The password is not correct</p>';
                      }
                    ?>
                  </fieldset>
                </div>
              </div>
              <button type="submit" class="btn">Submit</button>
              <?php
                  if ($result == -3)
                  {
                    echo '<p style="color: red;">Fill all the gaps</p>';
                  }
              ?>
            </form>
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
