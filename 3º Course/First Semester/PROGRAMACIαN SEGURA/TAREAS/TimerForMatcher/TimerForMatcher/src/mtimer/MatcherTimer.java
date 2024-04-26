package mtimer;
/**
 *
 * @author MAZ
 */
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
//
public final class MatcherTimer {
  
  // Implementa el método timeoutWorker() como una alternativa
  // basada en los valores de la enumeración OPS, de posibles
  // operaciones.
  private static enum OPS {MATCHES, LOOKINGAT, FIND}  
  
  private static final Logger LOGGER = Logger.getLogger(MatcherTimer.class.getName());

  // Servicio ejecutor empleado en el método timeoutWorker().
  private final ExecutorService executor;
  
  public MatcherTimer () {
    this.executor = Executors.newSingleThreadExecutor();
  }
  
  public boolean matches (final Matcher matcher,
                          final long timeout,
                          final TimeUnit units) throws TimeoutException {
    
    return timeoutWorker(matcher, timeout, units, OPS.MATCHES);
    
  }
  
  public boolean lookingAt (final Matcher matcher,
                            final long timeout,
                            final TimeUnit units) throws TimeoutException {
    
    return timeoutWorker(matcher, timeout, units, OPS.LOOKINGAT);
    
  }
  
  public boolean find (final Matcher matcher,
                       final long timeout,
                       final TimeUnit units) throws TimeoutException {
    
    return timeoutWorker(matcher, timeout, units, OPS.FIND);
    
  }   
  
  private boolean timeoutWorker (final Matcher matcher,
                                 final long timeout,
                                 final TimeUnit units,
                                 final OPS operation) throws TimeoutException {
    
    // No puede lanzar otra excepción que la indicada.
    // La excepción lanzada debe portar el mensaje especificado en el enunciado.
    try {
            Future<Boolean> future = executor.submit(() -> {
                switch (operation){
                    case MATCHES:
                        return matcher.matches();
                    case LOOKINGAT:
                        return matcher.lookingAt();
                    default:
                        return matcher.find();                  
                } 
            });
        return future.get(timeout, units);
        
    } catch (TimeoutException ex) {
            matcher.reset();
            throw new TimeoutException("Cantidad de tiempo superada");
        } catch (CancellationException | InterruptedException | ExecutionException ex) {
            return false;
        }
     
  }
  
  public void close () throws InterruptedException {
    // Detener el ejecutor
    // Esperar hasta que terminen las tareas en curso y pendientes.
    executor.shutdown();   
    while (!executor.isTerminated()){
        Thread.sleep(1000);
    }
  }
  
}
