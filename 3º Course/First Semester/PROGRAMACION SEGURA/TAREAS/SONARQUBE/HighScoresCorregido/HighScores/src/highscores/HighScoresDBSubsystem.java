package highscores;
/**
 *
 * @author MAZ
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
//
public final class HighScoresDBSubsystem implements AutoCloseable {

  static private final Logger LOGGER = Logger.getLogger(HighScoresDBSubsystem.class.getName());

  static private final String DB_USER = "labops";
  //static private final String DB_PASSWORD = "trustnoone";

  private final MessageDigest md;
  private final Connection connection;

  public HighScoresDBSubsystem () throws SQLException, NoSuchAlgorithmException, IOException {

    // Servicio de resumen digital; se emplea para obtener una clave
    // única a partir del nombre del jugador o del nombre del juego.
    try {
      md = MessageDigest.getInstance("SHA-256");
    } catch (final NoSuchAlgorithmException ex) {
      LOGGER.info("algoritmo de resumen no provisto en la plataforma");
      LOGGER.info(ex.getMessage());
      throw new NoSuchAlgorithmException();
    }

    // Conexión con la BBDD; se mantiene abierta durante toda la ejecución;
    // por esa razón no se emplea try-con-recursos.
    try {
        
      EncryptPassword encryptPassword = new EncryptPassword();
      encryptPassword.doBytesEncryptedData();
      String password = decryptPassword(encryptPassword);
      

      final String db_URL = "jdbc:h2:file:" + System.getProperty("user.dir") +
                                                     File.separator + "data" +
                                                     File.separator + "database" +
                                                     File.separator + "highscoresDB";
      connection = DriverManager.getConnection(db_URL, DB_USER, password);

    } catch (final SQLTimeoutException ex) {
      LOGGER.info("timeout al intentar establecer la conexión.");
      LOGGER.info(ex.getMessage());
      LOGGER.log(Level.SEVERE,
                "problema al consultar nombres de jugadores");

      throw new SQLException();
    } catch (final SQLException ex) {
      LOGGER.info("error al abrir la conexión.");
        System.out.println(ex.getMessage());
      LOGGER.info(ex.getMessage());
      LOGGER.log(Level.SEVERE,
                "problema al consultar nombres de jugadores");
      throw new SQLException();
    }

  }

  @Override
  public void close () {
    try {
      connection.close();
    } catch (final SQLException ex) {
      LOGGER.info("error al cerrar la conexion");
      LOGGER.info(ex.getMessage());
    }
  }

  private String getID (final String data) {
    // Generación de una clave única a partir del string recibido.
    // La clave generada es una secuencia de 16 octetos. Para poder
    // emplear la clave generada en los strings de las sentencias,
    // hay que convertir esa secuencia de octetos en un string que codifique
    // la misma información. Con ese fin se emplea la codificación Base64.
    final byte[] _Id = md.digest(data.getBytes(Charset.forName("UTF-8")));
    final String  Id = Base64.getEncoder().encodeToString(_Id);
    return Id;
  }

public boolean newHighScore (final String[] data) throws SQLException {

    final String player = data[0];
    final String game = data[1];
    final String score = data[2];

    // Jugador y juego deben estar registrados
    if (!registeredPlayer(player) || !registeredGame(game)){
      return false;}

    // Por comodidad, para evitar tener que aplicar cast, los valores
      // de las claves son strings que representan 16 octetos en Base64.
    final String playerId = getID(player);
    final String   gameId = getID(game);

    final String insertScore =
            "INSERT INTO scores (playerID, gameID, score) VALUES (?, ?, ?)";

    // Se ha verificado que jugador y juego están registrados
    try (final PreparedStatement statement = connection.prepareStatement(insertScore)) {
      statement.setString(1, playerId);
      statement.setString(2, gameId);
      statement.setString(3, score);
      return statement.executeUpdate() == 1;

    } catch (final SQLException ex) {
      LOGGER.info("problema al realizar la inserción de nueva puntuación con");
      LOGGER.log(Level.WARNING, "jugador {0}, juego {1} y puntuación {2}", data);
      LOGGER.info(ex.getMessage());
    }

    return false;

  }

  public Map<String, Long> highScoresByPlayer (final String player){
    final Map<String, Long> scores = new LinkedHashMap<>();
    
    final String playerId = "SELECT playerID FROM Players WHERE name = ?";
    final String scoresQueryPart1 =
                    "SELECT Games.name, GS.score FROM Games INNER JOIN ";
    final String scoresQueryPart2 =
            "(SELECT gameID, score FROM Scores WHERE playerID = ?) GS ";
    final String scoresQueryPart3 =
            "ON Games.gameID = GS.gameID ORDER BY GS.score";
    final String scoresQuery = scoresQueryPart1 + scoresQueryPart2 + scoresQueryPart3;
    
    try (final PreparedStatement statement1 = connection.prepareStatement(playerId);
         final PreparedStatement statement2 = connection.prepareStatement(scoresQuery)){
        
        statement1.setString(1, player);
        final ResultSet rs = statement1.executeQuery();
        if (rs.next()){
            statement2.setString(1, rs.getString("playerID"));
            final ResultSet rs2 = statement2.executeQuery();

            while (rs2.next()){
                scores.put(rs2.getString("Games.name"), rs2.getLong("GS.score"));
            }

        }
        
    }catch (final SQLException ex){
        LOGGER.log(Level.WARNING, "problema al consultar puntuaciones de jugador {0}", player);
        LOGGER.info("error en select");
        LOGGER.info(ex.getMessage());
    }
    return scores;
}

  public Map<String, Long> highScoresByGame (final String game) {
    final Map<String, Long> scores = new LinkedHashMap<>();
    
    final String gameId =
        "SELECT gameID FROM Games WHERE name = ?";
    final String scoresQueryPart1 =
        "SELECT Players.name, PS.score FROM Players INNER JOIN ";
    final String scoresQueryPart2 =
            "(SELECT playerID, score FROM Scores WHERE gameID = ?) PS ";
    final String scoresQueryPart3 =
            "ON Players.playerID = PS.playerID ORDER BY PS.score";
    final String scoresQuery = scoresQueryPart1 + scoresQueryPart2 + scoresQueryPart3;
    
    try (final PreparedStatement statement1 = connection.prepareStatement(gameId);
         final PreparedStatement statement2 = connection.prepareStatement(scoresQuery)) {

      statement1.setString(1, game);
      final ResultSet rs = statement1.executeQuery();
      if (rs.next()){
            statement2.setString(1, rs.getString("gameID"));
            final ResultSet rs2 = statement2.executeQuery();

            while (rs2.next()){
                scores.put(rs2.getString("Players.name"), rs2.getLong("PS.score"));
            }

      }
    } catch (final SQLException ex) {
      LOGGER.log(Level.WARNING,
              "problema al consultar puntuaciones de juego {0}", game);
      LOGGER.info("error en select");
      LOGGER.info(ex.getMessage());
    }
    return scores;
  }

  public List<String> getPlayers () {
    final List<String> players = new ArrayList<>();
    final String playersQuery = "SELECT name FROM players";
    try (final PreparedStatement statement = connection.prepareStatement(playersQuery)) {
      final ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        players.add(rs.getString("name"));
      }
    } catch (final SQLException ex) {
      LOGGER.info("problema al consultar listado de jugadores");
      LOGGER.info(ex.getMessage());
      LOGGER.log(Level.WARNING,
                "problema al consultar nombre de jugadores");
    }
    return players;
  }

  public List<String> getGames () {
    final List<String> games = new ArrayList<>();
    final String gamesQuery = "SELECT name FROM games";
    try (final PreparedStatement statement = connection.prepareStatement(gamesQuery)) {
      final ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        games.add(rs.getString("name"));
      }
    } catch (final SQLException ex) {
      LOGGER.info("problema al consultar listado de juegos");
      LOGGER.info(ex.getMessage());
      LOGGER.log(Level.WARNING,
                "problema al consultar nombre de juegos");

    }
    return games;
  }

  public boolean newPlayer (final String player) throws SQLException {

    if (registeredPlayer(player)) // Jugador ya está registrado
      return false;

    // Jugador no registrado
   return insertPlayer(player);

  }

  public boolean newGame (final String game) throws SQLException {

    if (registeredGame(game)) // Juego ya está registrado
      return false;

    // Juego no registrado
    return insertGame(game);

  }

  private boolean registeredPlayer (final String player) throws SQLException {
    return getPlayerId(player).compareTo("") != 0;
  }

  private boolean registeredGame (final String game) throws SQLException {
    return getGameId(game).compareTo("") != 0;
  }
  
  private String getPlayerId (final String player) throws SQLException{    
      final String playerIDQuery = "SELECT playerID FROM Players WHERE name = ?";
      try(final PreparedStatement statement = connection.prepareStatement(playerIDQuery))
      {
        statement.setString(1,player);
        final ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            return rs.getString("playerID");
        }
      }
      catch (final SQLException ex) {
        LOGGER.log(Level.WARNING,
                "problema al consultar id de jugador {0}", player);
        LOGGER.info("error en select");
        LOGGER.info(ex.getMessage());
        }
      return "";
  }
  
  private String getGameId (final String game) throws SQLException{
      final String playerIDQuery = "SELECT gameId FROM games WHERE name = ?";
      try(final PreparedStatement statement = connection.prepareStatement(playerIDQuery))
      {
        statement.setString(1, game);
        final ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            return rs.getString("gameId");
        }
      }
      catch (final SQLException ex) {
        LOGGER.log(Level.WARNING,
                "problema al consultar id de juego {0}", game);
        LOGGER.info("error en select");
        LOGGER.info(ex.getMessage());
        }
      return "";
  }
  private boolean insertPlayer (final String player) throws SQLException{
      final String playerId = getID(player);
      final String playerInsert =
              "INSERT INTO players (playerID, name) VALUES (?,?)";
    try (final PreparedStatement statement = connection.prepareStatement(playerInsert)) {
      statement.setString(1,playerId);
      statement.setString(2,player);
      statement.executeUpdate();

      return true;

    } catch (final SQLException ex) {
      LOGGER.log(Level.WARNING,
              "problema al intentar inserción en tabla PLAYERS con jugador {0}", player);
      LOGGER.info("error en select");
      LOGGER.info(ex.getMessage());
      throw new SQLException();
    }
  }
  private boolean insertGame (final String game) throws SQLException{
      final String gameId = getID(game);
      final String gameInsert =
              "INSERT INTO games (gameID, name) VALUES (?,?)";
      try (final PreparedStatement statement = connection.prepareStatement(gameInsert)) {
      statement.setString(1,gameId);
      statement.setString(2,game);
      statement.executeUpdate();

      return true;

    } catch (final SQLException ex) {
      LOGGER.log(Level.WARNING,
              "problema al intentar inserción en tabla GAMES con juego {0}", game);
      LOGGER.info("error en select");
      LOGGER.info(ex.getMessage());
      throw new SQLException();
    }
  }

    private String decryptPassword(EncryptPassword encryptedPassword) {
      try {
          String key = encryptedPassword.getKey();
          AESCipherConfigurator cipherConfigurator = encryptedPassword.getCipher();
          final Cipher decrypter =
              cipherConfigurator.getDecrypter(key.toCharArray(), encryptedPassword.getParams());

          final byte[] decryptedData = decrypter.doFinal(encryptedPassword.getEncryptedData());
          return new String(decryptedData, Charset.forName("UTF-8"));
          
      } catch (GeneralSecurityException | IOException ex) {
          Logger.getLogger(HighScoresDBSubsystem.class.getName()).log(Level.SEVERE, null, ex);
          return null;
      }
    }
}
