package concurrent;
/**
 *
 * @author MAZ
 */
import java.util.Random;
//
public final class Client {
  
  static public void main (final String[] args) {
    
    // Se procesan los argumentos recibidos por línea de órdenes.
    if (args.length < 1) {
      System.out.println("Argumentos de empleo: <n: entero positivo>");
      return;
    }
    
    final int n;
    try {
      n = Integer.parseInt(args[0]);
      if ((n <= 0) || (n > 100)) {
        System.out.println("Tamaño debe estar en el rango 1-100: " + n);
        return;
      }
    } catch (final NumberFormatException ex) {
      System.out.println("Argumentos de empleo: <n: entero positivo>");
      return;
    }
    
    // Preproceso: se obtienen los datos con los que operar.
    final float[] data = new float[n];
    final Random rg = new Random(System.nanoTime());
    for (int j = 0; j < n; ++j) {
      data[j] = rg.nextFloat();
    }
    
    System.err.println("Hebra cliente: " + Thread.currentThread().getId());
     
    // Se prepara una instancia del servicio.
    final Sorter service = new Sorter();
    
    // Proceso: se ejecuta el servicio.
    service.setData(data);    
    final Thread serviceThread = new Thread(service);
    serviceThread.start();
    do {
    } while(serviceThread.isAlive());
    
    // Postproceso: se emplea la información devuelta por el servicio.
    if (verify(data)) {
      System.out.println("Array correctamente ordenado");
    } else {
      System.out.println("Corrige el método de ordenación");
    }
    
  }
  
  static private boolean verify (final float[] data) {
    final int n = data.length;
    for (int j = 1; j < n; ++j) {
     if (data[j - 1] > data[j])
       return false;
    }
    return true;
  }
  
}
