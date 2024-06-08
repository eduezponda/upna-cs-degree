package concurrent;
/**
 *
 * @author MAZ
 */
import java.util.Arrays;
//
final class Sorter implements Runnable {
  
  private float[] data;
  
  private float[] doWork (final float[] data) {
    Arrays.sort(data);
    return data;
  }

  public void setData (final float[] data) {
    this.data = data;
  }
  
  @Override
  public void run () {
    System.err.println("Hebra de trabajo: " + Thread.currentThread().getId());
    doWork(data);
  }
  
}
