<?php
  require_once "../frontOffice/funcionalidadesAPI.php";
  session_start();

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

    <title>Mazapan Company-Diet</title>

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

    <script src="vendor/jquery/jquery.min.js"></script>
    
    <!-- jQuery UI y su módulo de autocompletado -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css">

    <script>
        $(document).ready(function() {
            // Configurar autocompletado
            $('#diet').autocomplete({
                source: function(request, response) {
                    $.ajax({
                        url: "../frontOffice/searchQuery.php",
                        method: "POST",
                        dataType: 'json',
                        data: { diet: request.term },
                        success: function(data) {
                            response(data);
                        }
                    });
                },
                minLength: 1
            });

            $('#search').click(function () {
                var query = $('#diet').val();
                if (query != '') {
                    searchRecipes(query, 0);
                }
            });

            $('.pagination').on('click', 'a', function (e) {
                e.preventDefault();
                var page = $(this).data('page');
                var query = $('#diet').val();
                if (query != '') {
                    if ($(this).hasClass('special')) {
                        var lastPage = parseInt($('.pagination .special').data('page'));
                        $(this).data('page', lastPage+1);
                        searchRecipes(query, lastPage+1);
                    } else {
                        searchRecipes(query, page);
                    }
                }
            });
        });

        function searchRecipes(query, page) {
            $.ajax({
                url: "../frontOffice/searchQuery.php",
                method: "POST",
                dataType: 'json',
                data: {
                    dietSearch: query
                },
                success: function (data) {
                    try {
                        updateRecipeBlocks(data.data, page);
                        updatePagination(page, data.totalPages);
                      } catch (e) {
                          console.error(e);
                          $('#result').html('Error parsing response');
                      }
                },
                error: function(xhr, status, error) {
                    console.error("AJAX error:", status, error);
                    $('#result').html('Error in AJAX request');
                }
            });
        }

        function updateRecipeBlocks(idRecetas, page) {
            $('.properties-box').empty();
            
            for (var i = page*9; i < (page+1)*9 && i < idRecetas.length; i++) {
                $.ajax({
                    url: '../frontOffice/getRecipeDetails.php',
                    method: 'POST',
                    dataType: 'html',
                    data: { id: idRecetas[i], index: i-page*9 },
                    success: function(recipeDetails) {
                      $('.properties-box').append(recipeDetails);
                    },
                    error: function(xhr, status, error) {
                        console.error("Error al obtener los detalles de la receta: ", error);
                    }
                });
            }
        }

        function updatePagination(currentPage, totalPages) {
            var pagination = $('.pagination');
            pagination.empty();
            var maxPagesToShow = 3;

            var startPage = 0;
            var endPage = Math.min(totalPages - 1, startPage + maxPagesToShow - 1);

            for (var i = startPage; i <= endPage; i++) {
                var pageLink = $('<a href="#" data-page="' + i + '">' + (i + 1) + '</a>');
                if (i === currentPage) {
                    pageLink.addClass('is_active');
                }
                pagination.append($('<li></li>').append(pageLink));
            }

            if (endPage < totalPages - 1) {
                var lastPageLink;
                if (totalPages - 1 > currentPage) {
                    lastPageLink = $('<a href="#" class="special" data-page="' + Math.min(totalPages - 1, currentPage) + '">>></a>');
                    pagination.append($('<li></li>').append(lastPageLink));
                }
                else {
                    lastPageLink = $('<a href="#" data-page="' + (totalPages - 1) + '">Last</a>');
                    pagination.append($('<li></li>').append(lastPageLink));
                }
                if (currentPage >= maxPagesToShow){
                    lastPageLink.addClass('is_active');
                }
            }
        }
    </script>
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
                  <li><a href="searchByDiet.php" class="active">Diet</a></li>
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
              <span class="breadcrumb"><a href="#">Home</a> / Diet</span>
              <h3>Diet</h3>
            </div>
          </div>
        </div>
      </div>
      
      
      <div class="section properties">
        <div class="container">
          <ul class="properties-filter">
            <input id="diet" type="text" placeholder="Search by diet" value="">
            <li>
              <button id="search" class="is_active" data-filter="*">Search</button>
            </li>
          </ul>
          <div id="result"></div>
          <div class="row properties-box">
          </div>
          <div class=white>
            <?php for ($i = 0; $i < 80; $i++) {echo "<br>";} ?>
          </div>
          <div class="row">
            <div class="col-lg-12">
              <ul class="pagination">
                <li><a class="is_active" href="#" data-page="0">1</a></li>
              </ul>
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
    </div>

    <!-- Scripts -->
    <!-- Bootstrap core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/isotope.min.js"></script>
    <script src="assets/js/owl-carousel.js"></script>
    <script src="assets/js/counter.js"></script>
    <script src="assets/js/custom.js"></script>
  </body>
</html>
