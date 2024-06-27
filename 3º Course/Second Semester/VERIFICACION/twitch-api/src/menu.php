<?php

require_once __DIR__ . '/../analytics/Database.php';
$db = new \AnalyticsD\Database();

while(true) {
    echo "Escoge una opcion: \n";
    echo "1. Borrar tablas DB \n";
    echo "2. Crear tablas \n";
    echo "3. Salir \n";
    echo "opcion: ";

    $option = trim(fgets(STDIN));

    switch($option) {
        case 1:
            $db->borrarTablas();
            break;
        case 2:
            $db->crearTablas();
            break;
        case 3:
            echo "\nSaliendo..\n";
            exit;
            break;
        default:
            echo "Opción no válida \n";
    }
}
