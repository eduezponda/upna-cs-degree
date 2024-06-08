package app;
/**
 *
 * @author MAZ
 */
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import query.Query;
//
public class UserQuery {

  public static void main (final String[] args) throws IOException {

    final Scanner scanner = new Scanner(System.in);
    System.out.print("Introduzca apellido: ");
    final String name = scanner.nextLine();

    try {

      final Query query = new Query();
      //final PreparedQuery query = new PreparedQuery();
      final List<String> result = query.query(name);
      if (!result.isEmpty()) {
        System.out.println("Dirección:");
        for (int j = 0; j < result.size(); ++j) {
          System.out.println("\t" + result.get(j));
        }
      } else {
        System.out.println("Estudiante desconocido");
      }

    } catch (final SQLException ex) {
      System.err.println("UserQuery: problema en acceso a base de datos");
    }

  }

}