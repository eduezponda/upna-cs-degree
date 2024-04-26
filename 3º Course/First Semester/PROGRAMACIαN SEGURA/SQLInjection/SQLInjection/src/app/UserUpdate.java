package app;
/**
 *
 * @author MAZ
 */
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;
//
import update.BatchedPreparedUpdate;
import update.BatchedUpdate;
//
public class UserUpdate {

  public static void main (final String[] args) {
    
    final Scanner scanner = new Scanner(System.in);
    
    final String[] values = new String[3];
    System.out.print("Introduzca nombre: ");
    values[0] = scanner.nextLine();    

    System.out.print("Introduzca apellido: ");
    values[1] = scanner.nextLine();
    
    System.out.print("Introduzca dirección: ");
    values[2] = scanner.nextLine();     

    try {

      final BatchedUpdate update = new BatchedUpdate();
      //final BatchedPreparedUpdate update = new BatchedPreparedUpdate();
      if (update.update(values)) {
        System.out.println("Insertado nuevo estudiante");
      } else {
        System.out.println("Nuevo estudiante no insertado");
      }

    } catch (final SQLException ex) {
      System.err.println("UserUpdate: problema en acceso a base de datos");
    } catch (final NoSuchAlgorithmException ex) {
      System.err.println("UserUpdate: algoritmo de resumen digital no instalado");
    }

  }

}