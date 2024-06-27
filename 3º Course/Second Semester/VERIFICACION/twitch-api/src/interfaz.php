<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://apiv3-58258d1d2566.herokuapp.com/src/css/styles.css">
    <link rel="icon" href="https://apiv3-58258d1d2566.herokuapp.com/src/img/favicon.png" type="image/png">
    <title>GES03 — Home</title>
    <script>
        function redirectToStreams() {
            window.location.href = 'analytics/streams.php';
        }

        function redirectToUser() {
            const userId = prompt("Por favor, introduce el ID del usuario:");
            if(userId) {
                window.location.href = `analytics/users.php?id=${userId}`;
            }
        }
    </script>
</head>
<body>

    <div class="container">
        <h1>Twitch API</h1>
        <ul class="members-list">
            <li>Guillermo Azcona</li>
            <li>Eduardo Ezponda</li>
            <li>Santiago Ayechu</li>
        </ul>
        <button onclick="redirectToStreams()">Consultar streams</button>
        <button onclick="redirectToUser()">Consultar ID</button>
    </div>

</body>
</html>
