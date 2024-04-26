package xssattack1;
/**
 *
 * @author MAZ
 */
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//
final class UnsanitizedInput {
  
  // Ejemplo tomado de 
  // https://wiki.sei.cmu.edu/confluence/display/java/IDS01-J.+Normalize+strings+before+validating+them  RISK ASSESMENTS (coste y riesgo en caso de fallos)

  // An attacker can use XSS to send a malicious script to an unsuspecting user.
  // The end user’s browser has no way to know that the script should not be trusted,
  // and will execute the script. Because it thinks the script came from a trusted
  // source, the malicious script can access any cookies, session tokens, or other
  // sensitive information retained by the browser and used with that site.
  // These scripts can even rewrite the content of the HTML page.

  static private String browserInput () {
    // String s may be attacker controllable.
    // \uFE64 is normalized to < and \uFE65 is normalized to > using the NFKC normalization form
    final String s = "<" + "script" + ">" + "Malicious javascript" + "<" + "/script" + ">";
    //final String s = "\uFE64" + "script" + "\uFE65" + "Malicious javascript" + "\uFE64" + "/script" + "\uFE65"; //la entrada puede estar escondida
    return s;
  }

  static private String inputValidation (final String input) {
    // Validatation
    final Pattern pattern = Pattern.compile("[<>]");
    final Matcher matcher = pattern.matcher(input); // Check for angle brackets
    if (matcher.find()) {
      // Found black listed tag
      throw new IllegalStateException("XSS scripting flaw: " + input);
    } else {
      // Normalization
      return Normalizer.normalize(input, Form.NFKC);
    }
  }

  static public void main (final String[] args) {
    final String HTMLinput = browserInput();
    System.out.println("Input before validation: " + HTMLinput);
    try {
      final String validatedHTMLInput = inputValidation(HTMLinput);
      System.out.println("Input after validation:  " + validatedHTMLInput);
    } catch (final IllegalStateException ex) {
      System.err.println(ex.getMessage());
    }
  }

}