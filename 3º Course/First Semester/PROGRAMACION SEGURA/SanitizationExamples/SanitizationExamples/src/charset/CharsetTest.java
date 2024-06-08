package charset;
/**
 *
 * @author MAZ
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
//
final class CharsetTest {

  static public void main (final String[] args) {
      
    if (Charset.defaultCharset().compareTo(Charset.forName("UTF-8")) != 0 ){
        System.out.println("Revise el charset por defecto");
        return;
    }
    
    //el primer metodo te devuelve el charset del proyecto. compareTo devuelve +1 si es mayor el de la izda, 0 si son iguales y -1 si es menor el de la izda*/

    final String path = System.getProperty("user.dir") + File.separator
                                              + "data" + File.separator;

    final String filename0 = args[0];
    final String filename1 = args[1];

    final File file0 = new File(path + filename0); //añade un file separator en el path al final para 
    final File file1 = new File(path + filename1); // luego poner el nombre del fichero al final y completar la ruta

    try (final FileReader is = new FileReader(file0);
         final FileWriter os = new FileWriter(file1))
        /*final FileWriter os = new FileWriter(file1),Charset.forName("UTF-16")))*/{ //habría tmb que poner el proyecto en properties como UTF 16 para poder ver renderizado el contenido de los files
        // is.getEncoding() -> formato de codificación que se esta utilizando (Ejemplo: UTF8) -> te devuelve en formato stream
        // es mejor comprobar el formato como en la parte de arriba con el compareTo
      final char[] buffer = new char[1000];
      for (int n = is.read(buffer); n > 0;) {
        System.out.println(new String(buffer, 0, n));
        os.write(buffer, 0, n);
        n = is.read(buffer);
      }

    } catch (final FileNotFoundException ex) {
      System.err.println("CharsetTest: fichero no encontrado");
    } catch (final IOException ex) {
      System.err.println("CharsetTest: problema de lectura/escritura");
    }

  }

}
