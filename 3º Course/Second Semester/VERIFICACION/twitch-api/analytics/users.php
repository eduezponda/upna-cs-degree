<?php

require_once __DIR__ . '/Services/TwitchApi.php';

use analytics\Services\TwitchApi;

if (isset($_GET["id"])) {
    $userId = htmlspecialchars($_GET["id"]);

    $client_id = '970almy6xw98ruyojcwqpop0p0o5a2';
    $client_secret = 'yl0nqzjjnadd8wl7zilpr9pzuh979j';
    $twitchApi = new TwitchApi($client_id, $client_secret);
    $twitchApi->getInfoUser($userId);
} else {
    echo "<p>No user ID provided in the URL.</p>";
}
