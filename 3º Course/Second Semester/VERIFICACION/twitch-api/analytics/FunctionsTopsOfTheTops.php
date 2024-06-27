<?php

namespace analytics;

use PDO;

class FunctionsTopsOfTheTops
{
    public function fetchInitialData($twitchApi, $dbInstanceInsert, $dbInstanceSelect)
    {
        $threeTopGames = $twitchApi->getTopGames();
        $dbInstanceInsert->insertarTopGames($threeTopGames);
        $results = [];

        foreach ($threeTopGames['data'] as $game) {
            $gameId = $game['id'];
            $gameName = $game['name'];
            $topVideosData = $twitchApi->getTop40VideosDadoUnGameId($gameId);
            $dbInstanceInsert->insertarVideos($topVideosData, $gameId);

            $stmtAtr = $dbInstanceSelect->obtenerAtributos($gameId);
            $row = $stmtAtr->fetch(PDO::FETCH_ASSOC);
            if ($row) {
                $result = [
                    'game_id' => strval($gameId),
                    'game_name' => $gameName,
                    'user_name' => $row['user_name'],
                    'total_videos' => strval($row['total_videos']),
                    'total_views' => strval($row['total_views']),
                    'most_viewed_title' => $row['most_viewed_title'],
                    'most_viewed_views' => strval($row['most_viewed_views']),
                    'most_viewed_duration' => $row['most_viewed_duration'],
                    'most_viewed_created_at' => $row['most_viewed_created_at']
                ];

                $results[] = $result;
            }
        }

        return $results;
    }

    public function shouldReviewEachTopGame($dbInstanceSelect, $since)
    {
        $lastUpdateTime = strtotime($dbInstanceSelect->getOldestUpdateDatetime()['fecha']);
        $currentTime = time();
        $maxTimeDifference = $currentTime - $lastUpdateTime;

        return $maxTimeDifference > $since;
    }

    public function reviewTopGames(
        $twitchApi,
        $dbInstanceSelect,
        $dbInstanceDelete,
        $dbInstanceInsert,
        $dbInstanceUpdate,
        $since
    ) {
        $threeTopGamesTwitch = $twitchApi->getTopGames();
        $threeTopGamesDB = $dbInstanceSelect->obtenerIdNombreFechadeJuegos();

        $gamesArray = [];
        $gamesArray[] = $threeTopGamesDB[0]['gamename'];
        $gamesArray[] = $threeTopGamesDB[1]['gamename'];
        $gamesArray[] = $threeTopGamesDB[2]['gamename'];

        foreach ($threeTopGamesTwitch['data'] as $index => $gameTwitch) {
            $fecha = $twitchApi->searchDate($threeTopGamesDB, $gameTwitch['id']);
            if ((in_array($gameTwitch['name'], $gamesArray)) && (time() - strtotime($fecha) > $since)) {
                $dbInstanceDelete->borrarVideosJuego($gameTwitch['id']);
                $dbInstanceInsert->insertarVideos(
                    $twitchApi->getTop40VideosDadoUnGameId($gameTwitch['id']),
                    $gameTwitch['id']
                );
                $dbInstanceUpdate->actualizarFechaJuego($gameTwitch['id']);
            } elseif (!(in_array($gameTwitch['name'], $gamesArray)) || !(time() - strtotime($fecha) > $since)) {
                $gameId = $dbInstanceSelect->obtenerGameIdporPosicion($index + 1);
                $dbInstanceDelete->borrarVideosJuego($gameId[0]['gameid']);
                $dbInstanceUpdate->updateTopGame($index + 1, $gameTwitch['id'], $gameTwitch['name']);
                $dbInstanceInsert->insertarVideos(
                    $twitchApi->getTop40VideosDadoUnGameId($gameTwitch['id']),
                    $gameTwitch['id']
                );
            }
        }
    }

    public function fetchTopGamesData($twitchApi, $dbInstanceSelect)
    {
        $threeTopGames = $twitchApi->getTopGames();
        $results = [];

        foreach ($threeTopGames['data'] as $game) {
            $gameId = $game['id'];
            $gameName = $game['name'];

            $stmtAtr = $dbInstanceSelect->obtenerAtributos($gameId);
            $row = $stmtAtr->fetch(PDO::FETCH_ASSOC);
            if ($row) {
                $result = [
                    'game_id' => strval($gameId),
                    'game_name' => $gameName,
                    'user_name' => $row['user_name'],
                    'total_videos' => strval($row['total_videos']),
                    'total_views' => strval($row['total_views']),
                    'most_viewed_title' => $row['most_viewed_title'],
                    'most_viewed_views' => strval($row['most_viewed_views']),
                    'most_viewed_duration' => $row['most_viewed_duration'],
                    'most_viewed_created_at' => $row['most_viewed_created_at']
                ];

                $results[] = $result;
            }
        }

        return $results;
    }
}
