package main;
/**
 *
 * @author MAZ
 */
import bytecounter.ByteCounter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
//
public final class App {

  /**
   * @param args the command line arguments
   */
  public static void main (final String[] args) {
    
    final ByteCounter byteCounter = new ByteCounter();
    final Scanner scanner = new Scanner(System.in);
    int opcion; 
    do {
                   
      System.out.println("Opciones:");
      System.out.println("  1 - Contar octetos");
      System.out.println("  0 - Salir");
      System.out.print("Introduzca opcion: ");
      try {  
        opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
          case 1:
            System.out.print("Introduzca nombre de fichero: ");
            final String filename = scanner.next().trim();
            // Aquí hace falta validar la entrada tecleada.
            
            try {
              final int bytes = byteCounter.count(filename);
              System.out.println("Fichero " + filename + ": " + bytes + " octetos");
            } catch (final FileNotFoundException ex) {
              System.err.println("Fichero " + filename + " no encontrado");
            } catch (final IOException ex) {
              System.err.println("Problema al operar con fichero");
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