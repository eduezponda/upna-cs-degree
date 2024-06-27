<?php
    function getRecipes($query) {

        $curl = curl_init();

        curl_setopt_array($curl, [
            CURLOPT_URL => "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/complexSearch?query=" . urlencode($query) . "&addRecipeInformation=true&fillIngredients=True&minCarbs=1&minProtein=1&minCalories=1&minFat=1&minCholesterol=1&minSugar=1&number=27",
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_ENCODING => "",
            CURLOPT_MAXREDIRS => 10,
            CURLOPT_TIMEOUT => 30,
            CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
            CURLOPT_CUSTOMREQUEST => "GET",
            CURLOPT_HTTPHEADER => [
                "X-RapidAPI-Host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com",
                "X-RapidAPI-Key: 57cde7fd1fmsha9c569f721e685bp1927abjsne3aad9677af0"
            ],
        ]);

        $response = curl_exec($curl);
        $err = curl_error($curl);

        curl_close($curl);

        if ($err) {
            echo "cURL Error #:" . $err;
            return -1;
        } else {
            return $response;
        }
    }
?>
