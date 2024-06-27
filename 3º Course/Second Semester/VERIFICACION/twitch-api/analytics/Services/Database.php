<?php

namespace analytics\Services;

use PDO;
use PDOException;

class Database
{
    private $host = 'ec2-52-215-160-108.eu-west-1.compute.amazonaws.com';
    private $dbname = 'd9ier7lbhilrf2';
    private $username = 'swwaugnxnsewjs';
    private $password = '8234b8e138effcddce3c96b8258503f4cb856643b5b8852cdcae7b2ba3ee065d';
    private $dsn;
    private $pdo;

    public function __construct()
    {
        $this->dsn = "pgsql:host=$this->host;port=5432;dbname=$this->dbname";
        try {
            $this->pdo = new PDO($this->dsn, $this->username, $this->password);
            $this->pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $e) {
            echo $e;
        }
    }

    public function getPdo()
    {
        return $this->pdo;
    }
}
