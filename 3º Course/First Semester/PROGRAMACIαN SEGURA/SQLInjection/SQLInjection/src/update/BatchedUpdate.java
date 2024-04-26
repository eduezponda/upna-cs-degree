package update;
/**
 *
 * @author MAZ
 */
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Base64;
import java.util.logging.Logger;
//
public final class BatchedUpdate {

  static private final Logger LOGGER = Logger.getLogger(BatchedUpdate.class.getName());

  static private final String DB_USER = "labops";
  static private final String DB_PASSWORD = "trustnoone";
  
  private final MessageDigest md;
  
  public BatchedUpdate () throws NoSuchAlgorithmException {
    try {
      md = MessageDigest.getInstance("SHA-1");
    } catch (final NoSuchAlgorithmException ex) {
      System.err.println("algoritmo de resumen no provisto");
      throw ex;
    }
  }

  public boolean update (final String[] values) throws SQLTimeoutException, SQLException {

    final String db_URL = "jdbc:h2:file:" + System.getProperty("user.dir") +
                           File.separator + "data" +
                           File.separator + "database" +
                           File.separator + "studentsDB";

    final Connection connection;
    try {
     connection = DriverManager.getConnection(db_URL, DB_USER, DB_PASSWORD);
    } catch (final SQLTimeoutException ex) {
      LOGGER.info("timeout al abrir la conexion");
      LOGGER.info(ex.getMessage());
      throw ex;
    } catch (final SQLException ex) {
      LOGGER.info("error al abrir la conexion");
      LOGGER.info(ex.getMessage());
      throw ex;
    }

    try (final Statement statement = connection.createStatement()) {

      final String name = values[0];
      final String lastname = values[1];
      final String address = values[2];
      final byte[] _id = md.digest((name + lastname + address).getBytes());
      final String id = Base64.getEncoder().encodeToString(_id);

      System.out.println(id + ":" + name + ":" + lastname + ":" + address);

      final String insertStatement
              = "INSERT INTO STUDENTS (id, name, lastname, address) VALUES ('"
              + id + "','" + name + "','" + lastname + "','" + address + "')";

      statement.addBatch(insertStatement);

      final int[] rs = statement.executeBatch();

      return (rs[0] == 1);

    } catch (final SQLException ex) {
      LOGGER.info("error al realizar inserción");
      LOGGER.info(ex.getMessage());
      throw ex;
    }

  }

}