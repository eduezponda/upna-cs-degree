package main;
/**
 *
 * @author MAZ
 * 
 * Esta clase está cerrada a modificaciones.
 * 
 */
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mtimer.MatcherTimer;
//
public final class TimeoutRegexpTest {
  
  // Expresiones regulares cuya estructura puede hacer que el tiempo de proceso
  // llegue a ser exponencial en la longitud de la entrada examinada.
  static private final String EVIL0 = "(a+)+";
  static private final String EVIL1 = "((a+)+)+";
  static private final String EVIL2 = "((ba+b+)+)+";

  // En https://owasp.org/www-community/attacks/Regular_expression_Denial_of_Service_-_ReDoS
  // se muestran ejemplos de expresiones regulares de uso corriente (EMAIL, por
  // ejemplo) cuya estructura incluye construcciones como la mostrada en EVIL1.
  // Por tanto, son expresiones regulares vulnerables a entradas específicamente
  // preparadas para forzar que el algoritmo de exploración opere en tiempo exponencial.

  static public void main (final String[] args) {
    
    if (Charset.defaultCharset().compareTo(Charset.forName("UTF-8")) != 0) {
      System.err.println("La aplicación solo opera en UTF-8.");
      System.err.println("Ajusta charset por defecto.");
      return;
    }

    try (final Scanner scanner = new Scanner(System.in).useDelimiter("\n")) {
 
      final String regex = EVIL1;
      final Pattern pattern = Pattern.compile(regex);
      final MatcherTimer tmatcher = new MatcherTimer();     
      
      int opcion;
      do {

        System.out.println("Opciones:");
        System.out.println("  1 - Método matches()");
        System.out.println("  2 - Método lookAt()");
        System.out.println("  3 - Método find()");
        System.out.println("  0 - Salir");
        System.out.print("Introduzca opción: ");

        try {
          opcion = scanner.nextInt();
          scanner.nextLine();

          switch (opcion) {
            case 1:
            case 2:
            case 3: {
              final String input = getInputData(scanner);
              final long timeout = getTime(scanner);
              final TimeUnit unit = getTimeUnit(scanner);
              try {
                final Matcher matcher = pattern.matcher(input);
                switch (opcion) {
                  case 1:
                    {
                      final boolean result = tmatcher.matches(matcher, timeout, unit);
                      System.out.println();
                      System.out.println("La cadena introducida " + (result ? "" : "no ")
                              + "cumple con el patrón");
                      System.out.println();
                      break;
                    }
                  case 2:
                    {
                      final boolean result = tmatcher.lookingAt(matcher, timeout, unit);
                      System.out.println();
                      System.out.print("La cadena introducida ");
                      if (result)
                        System.out.println("comienza con una subcadena que cumple con el patrón");
                      else
                        System.out.println("no comienza con una subcadena que cumpla con el patrón");
                      System.out.println();
                      break;
                    }
                  case 3:
                    {
                      final boolean result = tmatcher.find(matcher, timeout, unit);
                      System.out.println();
                      System.out.print("En la cadena introducida ");
                      if (result)
                        System.out.println("hay una subcadena que cumple con el patrón");
                      else
                        System.out.println("no hay ninguna subcadena que cumpla con el patrón");
                      System.out.println();
                      break;
                    }
                  default:
                }
              } catch (final TimeoutException ex) {
                System.out.println();
                System.out.println("Entrada inválida: " + ex.getMessage());
                System.out.println();  
              }

            }
            default:

          }

        } catch (final InputMismatchException ex) {
          scanner.nextLine();
          opcion = Integer.MAX_VALUE;
        }

      } while (opcion != 0);
      
      tmatcher.close();
    
    }
        
  }
  
  private static String getInputData (final Scanner scanner) {
    System.out.print("Introduzca la cadena a examinar: ");
    final String _input = scanner.nextLine().trim();
    return Normalizer.normalize(_input, Normalizer.Form.NFKD);
  }
  
  private static int getTime (final Scanner scanner) {
    System.out.print("Introduzca valor de timeout (entero positivo): ");
    int timeout = scanner.nextInt();
    while (timeout <= 0) {
      System.out.println("Valor inválido para timeout: " + timeout);
      System.out.print("Introduzca valor de timeout (entero positivo): ");
      timeout = scanner.nextInt();
    }
    return timeout;
  }
  
  private static TimeUnit getTimeUnit (final Scanner scanner) {
    
    int opcion;
    do {
      System.out.println("Unidades de tiempo:");
      System.out.println("  1 - Microsegundos");
      System.out.println("  2 - Milisegundos");
      System.out.println("  3 - Segundos");
      System.out.print("Introduzca opción: ");

      try {
        opcion = scanner.nextInt();
        scanner.nextLine();
      } catch (final InputMismatchException ex) {
        scanner.nextLine();
        opcion = Integer.MAX_VALUE;
      }

    } while ((opcion != 1) && (opcion != 2) && (opcion != 3));
    
    return (opcion == 1) ? MICROSECONDS : (opcion == 2) ? MILLISECONDS : SECONDS;
          
  }
    
//        System.out.println("Patrón: " + pattern.pattern());
//        System.out.print("Introduce una entrada que cumpla con ese patrón: ");
//        final String input = scanner.nextLine();
//        final Matcher matcher = pattern.matcher(input);
//        if (tmatcher.matches(matcher, 3, SECONDS))
//          System.out.println("Entrada válida");
//        else
//          System.out.println("Entrada no válida");
//              
//      } catch (final NoSuchElementException ex) {
//        System.out.println("Entrada no válida");
//      } catch (final TimeoutException ex) {
//        System.out.println("Entrada inválida: " + ex.getMessage());
//      }
//      
//      tmatcher.close();  
//
//    }
//
//  }
  
}
