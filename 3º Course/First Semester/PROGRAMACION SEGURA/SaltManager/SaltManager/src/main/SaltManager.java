package main;
/**
 *
 * @author MAZ
 */
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;
import saltmanager.SaltChange;
import saltmanager.SaltRecovery;
//
public final class SaltManager {
    
  /**
   * @param args the command line arguments
   */
  public static void main (final String[] args) {
    
    final SaltRecovery recovery = new SaltRecovery();
    final SaltChange changer = new SaltChange();
    final Scanner scanner = new Scanner(System.in);
    int opcion; 
    do {
                   
      System.out.println("Opciones:");
      System.out.println("  1 - Consultar pizca de sal");      
      System.out.println("  2 - Cambiar pizca de sal");      
      System.out.println("  0 - Salir");
      System.out.print("Introduce opcion: ");
      try {  
        opcion = scanner.nextInt();
        scanner.nextLine();
      
        switch (opcion) {
          case 1:
            final byte[] saltData = recovery.recover();
            if (saltData.length > 0) {
              final BigInteger saltValue = new BigInteger(+1, saltData);
              System.out.println("Pizca de sal: " + saltValue.toString(16));
            } else {
              System.out.println("Problema al intentar recuperar la pizca de sal");
            }
            break;
          case 2:
            if (changer.change()) {
              System.out.println("Pizca de sal cambiada");
            } else {
              System.out.println("Problema al intentar cambiar la pizca de sal");
            }
            break;
          default:
        }

      } catch (final InputMismatchException ex) {
        scanner.nextLine();
        opcion = Integer.MAX_VALUE;
      }
      
    } while (opcion != 0); 
    
  }
  
}