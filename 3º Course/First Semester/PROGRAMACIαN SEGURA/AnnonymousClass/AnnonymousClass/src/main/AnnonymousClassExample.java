package main;
/**
 *
 * @author MAZ
 */
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
//
import toupper.ToUpper;
//
public final class AnnonymousClassExample {
  
  private static final Logger LOGGER = Logger.getLogger(AnnonymousClassExample.class.getName());
  
  public static void main (final String[] args) {
    
    if (Charset.defaultCharset().compareTo(Charset.forName("UTF-8")) != 0) {
      System.err.println("La aplicación opera en UTF-8");
      System.out.println("Ajusta el charset por defecto");
      return;
    }
    
    if (args.length < 1) {
      System.out.println("Uso: <cadena con la que operar: string>");
      return;
    }
    
    // Mensaje con el que operar.
    final String string = args[0];
    
    // Se instancia un servicio ejecutor.
    final ExecutorService executor = Executors.newSingleThreadExecutor();
    
    // Se crea la tarea a ejecutar.
    final ToUpper toUpper = new ToUpper(string);
    
    // Se asigna al ejecutor.
    final Future<String> future = executor.submit(toUpper);
    
    //final Futurue<String> future = executor.submit(new Callable()<String>{public String call() {return string.toUpperCase();} });
    //construir un nuevo objeto que cumple con la interfaz (para ello tiene que tener el método call (meter dentro de llaves)
    //abria que comentar la instancia de ToUpper y al abrir las llaves te saldria con la bombilla meter el metodo call vacío
    //De esta manera estariamos todo el rato en el mismo entorno y no tendriamos que instanciar nada
    
    //executor.submit( -> string.toUpperCase()); ->algo así tmb
    
    // Se obtiene resultado de ejecución.
    try {
      final String result = future.get();
      System.out.println("Cadena procesada: " + result);
    } catch (final InterruptedException ex) {
      LOGGER.log(Level.WARNING, "tarea interrumpida durante ejecución", ex);
    } catch (final ExecutionException ex) {
      LOGGER.log(Level.WARNING, "tarea abortada durante ejecución", ex);
    }
    
    // Se para el ejecutor; se debiera esperar a que terminasen todas
    // las tareas pendientes y en curso; en este ejemplo no es necesario.
    executor.shutdown();
    
  } 
  
}
