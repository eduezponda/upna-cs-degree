package app;
/**
 *
 * @author MAZ
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
//
final class Populate {

  static private final String CLASS_NAME = Populate.class.getName();
  static private final Logger LOGGER = Logger.getLogger(CLASS_NAME);

  public static void main (final String[] args) {

    final String DB_USER = "labops";
    final String DB_PASSWORD = "trustnoone";

    final MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA-1");
    } catch (final NoSuchAlgorithmException ex) {
      System.err.println("algoritmo de resumen digital no provisto");
      return;
    }
    
    final String path = System.getProperty("user.dir") + File.separator
                                              + "data" + File.separator;
    final File file = new File(path + args[0]);

    // La contraseña de acceso a la base de datos no tendría que estar guardada
    // como atributo estático de clase. Pero incluir un método que implemente un
    // mecanismo para obtener esa contraseña de forma segura (lectura de contraseña
    // almacenada en un fichero, por ejemplo) reduce la claridad y sencillez que
    // se busca al preparar este proyecto.

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
      return;
    } catch (final SQLException ex) {
      LOGGER.info("error al abrir la conexion");
      LOGGER.info(ex.getMessage());
      return;
    }

    final String insertSQL = "INSERT INTO STUDENTS(id, name, lastname, address) VALUES (?, ?, ?, ?)";

    try (final PreparedStatement statement
            = connection.prepareStatement(insertSQL)) {
      
      try (final Scanner is = new Scanner(file)) {
        
        final Base64.Encoder encoder = Base64.getEncoder();

        while (is.hasNext()) {

          final String name = is.next();
          final String lastname = is.next();
          final String address = is.next();
          final byte[] _id = md.digest((name + lastname + address).getBytes());
          final String id = encoder.encodeToString(_id);

          System.out.println(id + ":" + name + ":" + lastname + ":" + address);

          statement.setString(1, id);
          statement.setString(2, name);
          statement.setString(3, lastname);
          statement.setString(4, address);

          statement.executeUpdate();

        }

      } catch (FileNotFoundException ex) {
        System.out.println("Fichero no encontrado: " + file.getAbsolutePath());
      }

    } catch (final SQLException ex) {
      LOGGER.log(Level.WARNING, "error al insertar registro {0}", ex.getMessage());
    }

  }

}