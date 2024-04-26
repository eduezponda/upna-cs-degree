package distributed.client;
/**
 *
 * @author MAZ
 */
import java.util.Random;
//
public final class BadClient {
  
  static public void main (final String[] args) {

    // Se fija tamaño de la colección de datos.
    final int n = 100000;
    
    // Preproceso: se obtienen los datos con los que operar.
    final float[] data = new float[n];
    final Random rg = new Random(System.nanoTime());
    for (int j = 0; j < n; ++j) {
      data[j] = rg.nextFloat();
    }
    
    // Se intenta saturar la capacidad del servicio (ataque DenialOfService
    final ClientTask client = new ClientTask(data);
    for (int j = 1; j <= 50000; ++j) {
      // Cada petición se realiza concurrentemente: no interesa esperar
      // al resultado, sino saturar la capacidad del servicio con gran
      // cantidad de peticiones casi simultáneas.
      new Thread(client).start();
      if ((j % 1000) == 0)
        System.out.println(j);
    }

  }
  
}
