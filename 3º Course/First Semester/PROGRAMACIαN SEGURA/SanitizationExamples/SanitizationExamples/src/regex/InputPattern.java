package regex;
/**
 *
 * @author MAZ
 */
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;
//
final class InputPattern {

  // Expresiones regulares básicas
  static private final String REGEX0 = "^a*$"; // ^...$ -> tiene que cumplir toda la estructura. Si no, podría solo una parte. Ej: aaa
  static private final String REGEX1 = "^a{1,5}$"; 
  static private final String REGEX2 = "^a{1,5}|b{2,4}$";
  static private final String REGEX3 = "^[ab]+$";
  static private final String REGEX4 = "^(?=.*a)(?=.*b).+$";

  // POSIX characters classes (only ASCII)
  // [:upper:]  -> uppercase letters
  // [:lower:]  -> lowercase letters
  // [:alpha:]  -> upper- and lowercase letters
  // [:digit:]  -> digits (\d)
  // [:xdigit:] -> hexadecimal digits
  // [:alnum:]  -> digits, upper- and lowercase letters
  // [:punct:]  -> punctuation (all graphic characters except letters and digits)
  // [:blank:]  -> space and TAB characters only
  // [:space:]  -> blank (whitespace) characters
  // [:cntrl:]  -> control characters
  // [:graph:]  -> graphic characters (all characters which have graphic representation)
  // [:print:]  -> graphic characters and space

  // https://wiki.owasp.org/index.php/OWASP_Validation_Regex_Repository

  static private final String TEXT = "^[a-zA-Z0-9 ¡!¿?,;.-]+$";
  static private final String PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,10}$";
  static private final String IP = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
  //direccion ip puede comenzar con un 25 y luego un digito entre 0 y 5 o, puede comenzar con un 2 seguido de un digito entre 0 y 4 y despues seguido de un digito entre 0 y 9
  //  // -> descripcion de la siguiente parte de la cadena
  static private final String EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,15}$";
  //asteriscos y demas caracteres pierden su caracter especial al estar dentro de un conjunto
  //debe haber al menos un @ en EMAIL

  // También se puede explorar la biblioteca https://regexlib.com . Son expresiones
  // regulares preparadas por entusiastas del tema; algunas son mejorables.

  // Expresiones regulares cuya estructura puede hacer que el tiempo de proceso
  // llegue a ser exponencial en la longitud de la entrada examinada. Expresiones dañinas
  static private final String EVIL0 = "^(a+)+$";
  static private final String EVIL1 = "^((a+)+)+$";
  static private final String EVIL2 = "^((ba+b+)+)+$";

  // En https://owasp.org/www-community/attacks/Regular_expression_Denial_of_Service_-_ReDoS
  // se muestran ejemplos de expresiones regulares de uso corriente (EMAIL, por
  // ejemplo) cuya estructura incluye construcciones como la mostrada en EVIL1.
  // Por tanto, son expresiones regulares vulnerables a entradas específicamente
  // preparadas para forzar que el algoritmo de exploración opere en tiempo exponencial.

  static public void main (final String[] args) {

    try (final Scanner scanner = new Scanner(System.in).useDelimiter("\n")) {

      //final String regex = EVIL1;
      final String regex = REGEX0;
      final Pattern pattern = Pattern.compile(regex);

      try {
        System.out.println("Patrón: " + regex);
        System.out.print("Introduce una entrada que cumpla con ese patrón: ");
        final String input = scanner.next(pattern);
        System.out.println("Entrada válida");
      } catch (final NoSuchElementException ex) {
        System.err.println("Entrada no válida");
      }

    }

  }

}
