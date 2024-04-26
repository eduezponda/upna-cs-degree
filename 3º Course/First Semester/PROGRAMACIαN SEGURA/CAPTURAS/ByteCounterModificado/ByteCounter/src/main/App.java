package main;
/**
 *
 * @author MAZ
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
//
import bytecounter.ByteCounter;
import java.text.Normalizer;
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
            final String _filename = scanner.next().trim();
            final String filename = Normalizer.normalize(_filename, Normalizer.Form.NFKD);
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