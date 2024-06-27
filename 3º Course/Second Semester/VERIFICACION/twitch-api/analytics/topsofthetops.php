<?php

require_once __DIR__ . '/Services/TwitchApi.php';
require_once __DIR__ . '/Services/VerificateManagement.php';
require_once __DIR__ . '/Services/DeleteTableManagement.php';
require_once __DIR__ . '/Services/InsertTableManagement.php';
require_once __DIR__ . '/Services/SelectTableManagement.php';
require_once __DIR__ . '/Services/UpdateTableManagement.php';
require_once __DIR__ . '/FunctionsTopsOfTheTops.php';

use analytics\Services\TwitchApi;
use analytics\Services\VerificateManagement;
use analytics\Services\DeleteTableManagement;
use analytics\Services\InsertTableManagement;
use analytics\Services\SelectTableManagement;
use analytics\Services\UpdateTableManagement;
use analytics\FunctionsTopsOfTheTops;

$dbInstanceVerificate = new VerificateManagement();
$dbInstanceDelete = new DeleteTableManagement();
$dbInstanceInsert = new InsertTableManagement();
$dbInstanceSelect = new SelectTableManagement();
$dbInstanceUpdate = new UpdateTableManagement();
$topsOfTheTopsInstance = new FunctionsTopsOfTheTops();

$client_id = '970almy6xw98ruyojcwqpop0p0o5a2';
$client_secret = 'yl0nqzjjnadd8wl7zilpr9pzuh979j';
$twitchApi = new TwitchApi($client_id, $client_secret);
$results = [];

$since = $_GET['since'] ?? null;
$since = $since ?? (10 * 60);

if (!$dbInstanceVerificate->isLoadedDatabase()) {
    $results = $topsOfTheTopsInstance->fetchInitialData($twitchApi, $dbInstanceInsert, $dbInstanceSelect);
} elseif ($topsOfTheTopsInstance->shouldReviewEachTopGame($dbInstanceSelect, $since)) {
    $topsOfTheTopsInstance->reviewTopGames(
        $twitchApi,
        $dbInstanceSelect,
        $dbInstanceDelete,
        $dbInstanceInsert,
        $dbInstanceUpdate,
        $since
    );
}

$results = $topsOfTheTopsInstance->fetchTopGamesData($twitchApi, $dbInstanceSelect);

$jsonResult = json_encode($results, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
$twitchApi->mostrarRespuestaJson($jsonResult);
