package correduria;   //
import java.util.Scanner;

public class VistaConsola implements VistaAdmision{
    
    private final String ANSI_PURPLE = "\u001B[35m";
    private final String ANSI_RESET = "\u001B[0m";
    private final Scanner scanner = new Scanner(System.in);
    
    public Integer consultarAnnoNacimiento() {
        System.out.print(ANSI_PURPLE + "//AÃ±o de Nacimiento> " + ANSI_RESET);
        String respuesta = scanner.nextLine();
        return Integer.parseInt(respuesta);
    }
    public Integer consultarSalarioAnual() {
        System.out.print(ANSI_PURPLE + "//Salario Anual> " + ANSI_RESET);
        String respuesta = scanner.nextLine();
        return Integer.parseInt(respuesta);
    }
    public String consultarTipoDeBien(){
        System.out.print(ANSI_PURPLE + "//Tipo de bien> " + ANSI_RESET);
        String tipoDeBien = scanner.nextLine();
        return  tipoDeBien;
    }
    public Integer consultarValorDeBien() {
        System.out.print(ANSI_PURPLE + "//Valor de bien> " + ANSI_RESET);
        String valorDeBien = scanner.nextLine();
        return Integer.parseInt(valorDeBien);
    }
    public void printBienIncorrecto(){
        System.out.println("Escriba correctamente el valor y el tipo: ");
    }
}