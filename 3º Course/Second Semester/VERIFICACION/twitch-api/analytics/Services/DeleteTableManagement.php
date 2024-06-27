<?php

namespace analytics\Services;

require_once __DIR__ . '/Database.php';

use analytics\Services\Database;
use PDO;

class DeleteTableManagement extends Database
{
    public function borrarVideosJuego($gameId)
    {
        $sql = "DELETE FROM VIDEO WHERE gameId = :gameId";
        $stmt = $this->getPdo()->prepare($sql);
        $stmt->bindParam(':gameId', $gameId, PDO::PARAM_INT);
        $stmt->execute();
    }
}
