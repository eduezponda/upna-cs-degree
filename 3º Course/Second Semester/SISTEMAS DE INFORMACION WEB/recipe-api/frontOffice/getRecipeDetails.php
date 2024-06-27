<?php
    require_once "../frontOffice/funcionalidadesAPI.php";

    if (isset($_POST['id'])) {
        $idReceta = $_POST['id'];
        $index = $_POST['index'];

        $finales = ["adv", "str", "adv rac", "str", "rac str", "rac adv", "rac str", "rac adv", "rac adv"];
        $datosReceta = obtenerDatosReceta($idReceta);

        echo '<div
            class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6 ' . $finales[$index] . '">
            <div class="item">
                <a href="recipe-details.php?id=' . $idReceta . '">
                <img src="' . $datosReceta[0]["imagen"] . '" alt="" width=350px height=260px></a>
                <span class="category">' . $datosReceta[0]["comida"] . '</span>
                <h6>' . $datosReceta[0]["minutos"] . ' min</h6>
                <h4><a href="recipe-details.php?id=' . $idReceta . '">' . $datosReceta[0]["titulo"] . '</a></h4>
                <ul>
                    <li>Proteins: <span>' . $datosReceta[0]["proteinas"] . '</span></li>
                    <li>Fats: <span>' . $datosReceta[0]["grasas"] . '</span></li>
                    <li>Calories: <span>' . $datosReceta[0]["calorias"] . '</span></li>
                    <li>Carbohydrates: <span>' . $datosReceta[0]["carbohidratos"] . '</span></li>
                </ul>
                <div class="main-button">
                    <a href="recipe-details.php?id=' . $idReceta . '">More information: </a>
                </div>
            </div>
        </div>';
    }

?>
