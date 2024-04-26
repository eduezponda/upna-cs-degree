import java.io.*;
public class Cubo{
    public static void main(String[] args) throws IOException {

        InputStreamReader flujo;
        BufferedReader teclado;
        int num;
        String textoNum;

        flujo = new InputStreamReader(System.in);
        teclado = new BufferedReader(flujo);

        System.out.print("Escribe un número entero positivo: ");
        textoNum = teclado.readLine();
        Integer intNum = Integer.valueOf (textoNum);
        num = intNum.intValue();

        System.out.println("El cubo de " + num + " es " + num * num * num);



    }
}
