package toupper;
/**
 *
 * @author MAZ
 */
import java.util.concurrent.Callable;
//
public final class ToUpper implements Callable<String> {
  
  private final String message;
  
  public ToUpper (final String message) {
    this.message = message;
  }

  @Override
  public String call () {
    return message.toUpperCase();
  }

}
