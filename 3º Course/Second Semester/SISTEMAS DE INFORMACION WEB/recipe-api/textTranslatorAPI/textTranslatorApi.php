<?php

    function getTranslateText($target_language, $text) {
        $curl = curl_init();

        curl_setopt_array($curl, [
            CURLOPT_URL => "https://microsoft-translator-text.p.rapidapi.com/translate?to%5B0%5D=" . urlencode($target_language) . "&api-version=3.0&profanityAction=NoAction&textType=plain",
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_ENCODING => "",
            CURLOPT_MAXREDIRS => 10,
            CURLOPT_TIMEOUT => 30,
            CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
            CURLOPT_CUSTOMREQUEST => "POST",
            CURLOPT_POSTFIELDS => json_encode([
                [
                        'Text' => $text
                ]
            ]),
            CURLOPT_HTTPHEADER => [
                "X-RapidAPI-Host: microsoft-translator-text.p.rapidapi.com",
                "X-RapidAPI-Key: 57cde7fd1fmsha9c569f721e685bp1927abjsne3aad9677af0",
                "content-type: application/json"
            ],
        ]);

        $response = curl_exec($curl);
        $err = curl_error($curl);

        curl_close($curl);

        $response = json_decode($response, true);

        if ($err) {
            return "cURL Error #:" . $err;
        }
        if (isset($response[0]['translations'][0]['text'])) {
            return $response[0]['translations'][0]['text'];
        }

        return "Error in translation access";
    }

    function getTranslatorLanguages() {
        $curl = curl_init();

        curl_setopt_array($curl, [
            CURLOPT_URL => "https://microsoft-translator-text.p.rapidapi.com/languages?api-version=3.0&scope=translation",
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_ENCODING => "",
            CURLOPT_MAXREDIRS => 10,
            CURLOPT_TIMEOUT => 30,
            CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
            CURLOPT_CUSTOMREQUEST => "GET",
            CURLOPT_HTTPHEADER => [
                "Accept-Language: en",
                "X-RapidAPI-Host: microsoft-translator-text.p.rapidapi.com",
                "X-RapidAPI-Key: 57cde7fd1fmsha9c569f721e685bp1927abjsne3aad9677af0"
            ],
        ]);
        
        $response = curl_exec($curl);
        $err = curl_error($curl);
        
        curl_close($curl);
        
        if ($err) {
            return "cURL Error #:" . $err;
        } 
        return $response;
    }
?>
