<?php

namespace analytics\Services;

require_once __DIR__ . '/Database.php';

use analytics\Services\Database;
use PDO;

class VerificateManagement extends Database
{
    public function comprobarIdUsuarioEnDB($userId)
    {
        $stmt = $this->getPdo()->prepare("SELECT COUNT(*) FROM USUARIO WHERE ID = (?)");
        $stmt->execute([$userId]);
        return ($stmt->fetchColumn() > 0);
    }

    public function isLoadedDatabase()
    {
        $stmt = $this->getPdo()->prepare("SELECT COUNT(*) FROM JUEGO");
        $stmt->execute();
        return ($stmt->fetchColumn() > 0);
    }
}
