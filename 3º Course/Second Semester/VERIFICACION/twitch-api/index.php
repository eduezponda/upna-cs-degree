<?php

// Get the requested URL path
//$requestPath = $_SERVER['REQUEST_URI'];
$requestPath = strtok($_SERVER['REQUEST_URI'], '?');

// Define your routes
$routes = [
    '/' => 'src/interfaz.php',
    '/analytics/streams' => 'analytics/streams.php',
    '/analytics/users'   => 'analytics/users.php',
    '/analytics/topsofthetops' => 'analytics/topsofthetops.php',
];

// Check if the requested path is in the defined routes
if (array_key_exists($requestPath, $routes)) {
    if ($requestPath === '/analytics/users') {
        // Extract user ID from the query parameters
        $userId = $_GET['id'] ?? null;

        if ($userId !== null) {
            // Include the users.php script and pass the user ID
            include 'analytics/users.php';
        } else {
            // Handle missing user ID
            http_response_code(400);
            echo '400 Bad Request - User ID is missing';
        }
    } else {
        // Include the PHP script for other routes
        include $routes[$requestPath];
    }
} else {
    // Handle 404 Not Found
    echo $requestPath;
    http_response_code(404);
    echo '404 No encontrado';
}
