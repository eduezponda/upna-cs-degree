package query;
/**
 *
 * @author MAZ
 */
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
//
public final class Query {

  static private final Logger LOGGER = Logger.getLogger(Query.class.getName());

  static private final String DB_USER = "labops";
  static private final String DB_PASSWORD = "trustnoone";

  public List<String> query (final String lastName) throws SQLTimeoutException, SQLException {

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

      final String selectStatement
              = "SELECT ADDRESS FROM STUDENTS WHERE lastname = '" + lastName + "'";

      final ResultSet rs = statement.executeQuery(selectStatement);

      final ArrayList<String> result = new ArrayList<>();
      while (rs.next()) {
        result.add(rs.getString("ADDRESS").trim());
      }
      return result;

    } catch (final SQLException ex) {
      LOGGER.info("error al realizar consulta");
      LOGGER.info(ex.getMessage());
      throw ex;
    }


  }

}