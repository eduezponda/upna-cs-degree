<?php

namespace analytics\Services;

require_once __DIR__ . '/Database.php';

use analytics\Services\Database;
use PDO;

class TokenManagement extends Database
{
    public function existeTokenDB()
    {
        try {
            $stmt = $this->getPdo()->prepare("SELECT COUNT(*) FROM TOKEN");
            $stmt->execute();
            return ($stmt->fetchColumn() > 0);
        } catch (\PDOException $e) {
            error_log("Error al verificar si existe token en la base de datos: " . $e->getMessage());
            return false;
        }
    }

    public function getTokenDB()
    {
        try {
            $stmt = $this->getPdo()->query("SELECT token FROM TOKEN ORDER BY tokenId DESC LIMIT 1");
            $tokenData = $stmt->fetch(PDO::FETCH_ASSOC);
            return ($tokenData !== false) ? $tokenData['token'] : null;
        } catch (\PDOException $e) {
            error_log("Error al obtener token de la base de datos: " . $e->getMessage());
            return null;
        }
    }

    public function insertarToken($newToken)
    {
        try {
            $stmt = $this->getPdo()->prepare("INSERT INTO TOKEN (token) VALUES (?)");
            $stmt->execute([$newToken]);
        } catch (\PDOException $e) {
            error_log("Error al insertar token en la base de datos: " . $e->getMessage());
        }
    }
}
