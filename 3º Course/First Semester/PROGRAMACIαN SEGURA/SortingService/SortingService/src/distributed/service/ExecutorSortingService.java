package distributed.service;
/**
 *
 * @author MAZ
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
//
final class ExecutorSortingService implements Runnable {
  
  static private final int PORT = 7777; 

  static private final String CLASS_NAME = ExecutorSortingService.class.getName();
  static private final Logger LOGGER = Logger.getLogger(CLASS_NAME);
  
  // Servicio ejecutor con hebras de trabajo
  final ExecutorService executorForSortingTasks;  
  
  ExecutorSortingService (final int numThreads) {
    if (numThreads < 0) {
      throw new IllegalArgumentException("Número de hebras de trabajo illegal: " + numThreads);
    }
    this.executorForSortingTasks = Executors.newFixedThreadPool(numThreads);
  }
    
  @Override
  public void run () {
                
    LOGGER.log(Level.INFO, "arrancando servicio (hebra {0})", Thread.currentThread().getId());

    // Arranque de bucle de escucha
    try (final ServerSocket serverSocket = new ServerSocket(PORT)) {
      
      LOGGER.log(Level.INFO, "servicio en escucha");
      
      // Servicio ejecutor para implementar timeout en transacciones entrantes
      final ExecutorService auxiliaryExecutor = Executors.newSingleThreadExecutor();
      
      int j = 0;
      
      do { // Bucle de escucha

        // Se reutiliza la tarea hasta que se complete
        final Future<Socket> future = auxiliaryExecutor.submit(serverSocket::accept);
        do {

          try {
            // Interrumpe la espera cada 10 milisegundos
            // para saber si se ha detenido el servicio.
            Socket socket = future.get(10, MILLISECONDS);
          } catch (final  ExecutionException ex) {
            // Ha ocurrido una excepción durante la ejecución del método accept().
            // Sería necesario considerar tomar medidas.
          } catch (final InterruptedException | TimeoutException ex) {
            // Aquí es obligado discriminar y tratar excepciones por separado.
            // - InterruptedException: le ejecuón del método accept() se ha
            //   completado en estos 10 milisegundos; ha llegado una nueva petición.
            //   La siguiente consulta de future.isDone() devuelve true.
            // - TimeoutException: han transcurrido los 10 milisegundos sin que
            //   haya llegado una nueva petición; la siguiente consulta de
            //   future.isDone() devuelve false.
          }

        } while (!executorForSortingTasks.isShutdown() && !future.isDone());
        
        // El servicio sigue activo y se ha recibido una nueva petición.
        if (!executorForSortingTasks.isShutdown() && future.isDone()) {
          try {
            final Socket socket = future.get();
            LOGGER.log(Level.INFO, "petición entrante {0} desde {1}", new Object[]{++j, socket.getInetAddress()});
            final SortingTask task = new SortingTask(socket);
            executorForSortingTasks.submit(task);
          } catch (final CancellationException | ExecutionException ex) {
            LOGGER.log(Level.INFO, "petición abortada", ex);
          } catch (final InterruptedException  ex) {
            LOGGER.log(Level.INFO, "problema de ejecución en bucle de escucha", ex);
          }

        }

      } while (!executorForSortingTasks.isShutdown());
      
      // Se detiene el ejecutor auxiliar, empleado para implementar
      // las interrupciones en el bucle de escucha.
      auxiliaryExecutor.shutdown();
      
      LOGGER.info("escucha finalizada");

      // Bucle que espera hasta terminar todas las tareas en curso
      // y la cola del ejecutor.
      do {
        try {
          // Consulta cada 1.5 segundos si todavía quedan tareas activas.
          final boolean x = executorForSortingTasks.awaitTermination(1500, MILLISECONDS);
        } catch (final InterruptedException ex) {
          LOGGER.log(Level.SEVERE, "{0}", ex);
          return;
        }
      } while (!executorForSortingTasks.isTerminated());

      LOGGER.info("tareas pendientes completadas");

    } catch (final IOException ex) {
      LOGGER.log(Level.WARNING, "problema al abrir el socket de servicio", ex.getMessage());
    }
    
    LOGGER.log(Level.INFO, "servicio finalizado (hebra {0})", Thread.currentThread().getId());

  }
  
  void stop () {
    this.executorForSortingTasks.shutdown();
    // Bucle que espera hasta terminar todas las tareas en curso y encoladas.
    // Evita que el proceso del lanzador del servicio finalice antes de que
    // se hayan atendido todas las tareas aceptadas.
    do {
    } while (!executorForSortingTasks.isTerminated());
  }

}
