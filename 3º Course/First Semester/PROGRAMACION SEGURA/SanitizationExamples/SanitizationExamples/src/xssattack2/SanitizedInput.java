package xssattack2;
/**
 *
 * @author MAZ
 */
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//
final class SanitizedInput {

  // An attacker can use XSS to send a malicious script to an unsuspecting user.
  // The end user’s browser has no way to know that the script should not be trusted,
  // and will execute the script. Because it thinks the script came from a trusted
  // source, the malicious script can access any cookies, session tokens, or other
  // sensitive information retained by the browser and used with that site.
  // These scripts can even rewrite the content of the HTML page.

  static private String browserInput () {
    // String s may be user controllable.
    // \uFE64 is normalized to < and \uFE65 is normalized to > using the NFKC normalization form
    // \uFDEF is a non character code point
    final String s = "\uFE64" + "scr" + "\uFDEF" + "ipt" + "\uFE65"
            + "Malicious javascript"
            + "\uFE64" + "/scrip" + "\uFDEF" + "t" + "\uFE65";
    return s;
  }

  // According to Unicode Technical Report #36, Unicode Security Considerations,
  // U+FFFD is usually unproblematic, because it is designed expressly for this
  // kind of purpose.
  // Because U+FFFD character doesn't have syntactic meaning in programming languages
  // or structured data, it will typically just cause a failure in parsing.
  // Where the output character set is not Unicode, though, this character may
  // not be available.
  static private String inputValidation (final String input) {
    // Normalization
    final String normalizedInput = Normalizer.normalize(input, Form.NFKC);
    // Non-ASCII characters substitution
    final String normalizedAndSanitizedInput = normalizedInput.replaceAll("[^\\p{ASCII}]", "\uFFFD"); //"\uFFFD" -> codigo que no tiene asignado ningun simbolo
    // Noncharacter code points substitution
    //final String normalizedAndSanitizedInput = normalizedInput.replaceAll("[\\p{Cn}]", "\uFFFD");
    // Validation
    final Pattern pattern = Pattern.compile("<script>");
    final Matcher matcher = pattern.matcher(normalizedAndSanitizedInput); // Check for angle brackets
    if (matcher.find()) {
      // Found black listed tag
      throw new IllegalStateException("XSS scripting flaw: " + normalizedAndSanitizedInput);
    } else {
      // Noncharacter code points deletion
      return normalizedAndSanitizedInput;
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