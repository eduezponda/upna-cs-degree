<?php

namespace analytics\Services;

require_once __DIR__ . '/Database.php';

use analytics\Services\Database;
use PDO;

class UpdateTableManagement extends Database
{
    public function actualizarFechaJuego($gameId)
    {
        $sql = "UPDATE FECHACONSULTA 
            SET fecha = CURRENT_TIMESTAMP
            WHERE idFecha IN (SELECT idFecha FROM JUEGO WHERE gameId = :idGame)";

        $stmt = $this->getPdo()->prepare($sql);
        $stmt->bindParam(':idGame', $gameId, PDO::PARAM_INT);
        $stmt->execute();
    }

    public function updateTopGame($pos, $gameId, $name)
    {
        $sql = "UPDATE JUEGO SET gameId = ?, gameName = ? WHERE position = ?";

        $stmt = $this->getPdo()->prepare($sql);

        $stmt->bindParam(1, $gameId, PDO::PARAM_INT);
        $stmt->bindParam(2, $name, PDO::PARAM_STR);
        $stmt->bindParam(3, $pos, PDO::PARAM_INT);

        $stmt->execute();

        $this->actualizarFechaJuego($gameId);
    }
}
