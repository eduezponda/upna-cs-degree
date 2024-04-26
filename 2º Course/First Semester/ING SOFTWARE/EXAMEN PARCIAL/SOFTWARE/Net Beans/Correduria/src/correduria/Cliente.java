package correduria;
import java.time.LocalDate;

public class Cliente{

    public int annoNacimiento;
    public int salarioAnual;

    public Cliente() {
        this.annoNacimiento = 0;
        this.salarioAnual = 0;
    }
    public Cliente(int annoNacimiento,int salarioAnual) {
        this.annoNacimiento = annoNacimiento;
        this.salarioAnual = salarioAnual;
    }
    public int Edad(){
        Integer annoActual = LocalDate.now().getYear();             
        int edad = annoActual - this.annoNacimiento;
        return edad;
    }
}