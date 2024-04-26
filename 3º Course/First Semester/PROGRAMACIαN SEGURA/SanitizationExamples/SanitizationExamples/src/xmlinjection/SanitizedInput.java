package xmlinjection;
/**
 *
 * @author MAZ
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.Normalizer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
//
final class SanitizedInput {

  static private final Logger LOGGER = Logger.getLogger(SanitizedInput.class.getName());

  static private String userInteraction (final InputStream is) throws NumberFormatException {

    final Scanner scanner = new Scanner(is);

    // La interacción con el usuario consiste en que este elije un artículo
    // por medio de la interfaz de uso de la aplicación; una vez confirmada
    // la elección, la aplicación opera con el identificador del artículo elegido.
    // El precio es seleccionado por la aplicación en función del artículo elegido;
    // la cantidad es el valor introducido por el cliente.
    final String articleId = "XYZ012345";
    final String price = "500.0";
    //
    // Para simplificar el ejemplo, se hace abstracción de la selección del artículo
    // y de la selección de su precio por unidad. Solo se solicita la cantidad.
    final int quantity = inputQuantity(scanner);
    //
    // Resumen:
    //
    System.out.println("Resumen para usuario");
    System.out.println("\tArtículo elegido: " + articleId);
    System.out.println("\tPrecio por unidad: " + price);
    System.out.println("\tCantidad: " + quantity);
    //
    return generateXMLRecord(articleId, price, quantity);

  }

  static private int inputQuantity (final Scanner scanner) {
    System.out.print("Introduzca cantidad: ");
    int quantity = 0;
    boolean isValid;
    do {
      final String input = scanner.nextLine();
      final String _quantity = Normalizer.normalize(input, Normalizer.Form.NFKC);
      isValid = true;
      try {
        quantity = Integer.parseUnsignedInt(_quantity);
        if (quantity == 0) {
          System.out.println("Cantidad no permitida (" + _quantity + ")");
          System.out.print("Por favor, vuelva a introducir la cantidad: ");
          isValid = false;
        }
      } catch (final NumberFormatException ex) {
        System.out.println("Cantidad errónea (" + _quantity + ")");
        System.out.print("Por favor, vuelva a introducir la cantidad: ");
        isValid = false;
      }
    } while (!isValid);
    return quantity;
  }

  static private String generateXMLRecord (final String articleId,
                                           final String price,
                                           final int quantity) {
    final String xmlRecord = "<item>\n" +
            "<articleId>" + articleId + "</articleId>\n" +
            "<quantity>" + quantity + "</quantity>\n" +
            "<price>" + price + "</price>\n" + "</item>";
    return xmlRecord;
  }

  static private void generateDDBBRecord (final String xmlRecord) {

    try (final StringReader is = new StringReader(xmlRecord)) {

      final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      final DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
      final Document doc = docBuilder.parse(new InputSource(is));

      final NodeList items = doc.getElementsByTagName("item");
      final Node node = items.item(0);
      final Element element = (Element) node;

      final String articleId =
              element.getElementsByTagName("articleId").item(0).getTextContent();
      final String quantity =
              element.getElementsByTagName("quantity").item(0).getTextContent();
      final String price =
              element.getElementsByTagName("price").item(0).getTextContent();

    // Es responsabilidad de este método extraer los datos del registro XML
    // y crear una entrada en la base de datos con el pedido del cliente.
    // Por simplificar el ejemplo, simplemente se extraen y muestran los datos
    // del registro XML.

    //
    // Resumen de datos extraídos:
    //
    System.out.println("Datos que van a la base de datos de pedidos");
    System.out.println("\tArtículo comprado: " + articleId);
    System.out.println("\tPrecio por unidad: " + price);
    System.out.println("\tCantidad: " + quantity);
    
    System.out.println("Total: " + (Integer.parseInt(quantity) * Float.parseFloat(price)));

    } catch (final SAXException ex) {
      LOGGER.log(Level.SEVERE, "unexpected record format\n {0}", xmlRecord);
    } catch (final IOException ex) {
      LOGGER.log(Level.SEVERE, "record processing error\n {0}", xmlRecord);
    } catch (final ParserConfigurationException ex) {
      LOGGER.log(Level.SEVERE, "parser exception", ex);
    }

  }

  static public void main (final String[] args) {
    final String xmlRecord = userInteraction(System.in);
    generateDDBBRecord(xmlRecord);
  }

}