package distributed.service;
/**
 *
 * @author MAZ
 */
import java.util.InputMismatchException;
import java.util.Scanner;
//
public final class SortingServiceLauncher {

  static public void main (final String[] args) {

    //final SortingService service = new SortingService();
    final ExecutorSortingService service = new ExecutorSortingService(5);

    // El servicio se ejecuta sobre una hebra distinta a la hebra
    // del lanzador; desde esta hebra continua la ejecucion del menú.
    final Thread serviceThread = new Thread(service);
    serviceThread.start();

    // Se presenta la interfaz de control de servicio.
    menu();

    // Parada del servicio; antes de finalizar se debería esperar a que todas
    // las tareas en ejecución fuesen atendidas, pero ya no se aceptan más tareas.
    service.stop();
    
  }
  
  static private void menu () {
    
    final Scanner scanner = new Scanner(System.in);      
        
    int opcion; 
    do {
                   
      System.out.println("Opciones:");    
      System.out.println("  0 - Salir");
      System.out.print("Introduce opción: ");

      try {
        opcion = scanner.nextInt();
        scanner.nextLine();
      } catch (final InputMismatchException ex) {
        scanner.nextLine();
        opcion = Integer.MAX_VALUE;
      }
      
    } while (opcion != 0);
    
  }  
  
}
