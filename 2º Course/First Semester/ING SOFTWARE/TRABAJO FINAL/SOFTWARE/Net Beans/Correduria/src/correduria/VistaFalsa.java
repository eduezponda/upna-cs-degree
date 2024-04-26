package correduria; //

public class VistaFalsa implements VistaAdmision{

    private Integer annoNacimiento;
    private Integer SalarioAnual;
    private String tipoDeBien;
    private Integer valorDeBien;
    public static String Linea;

    public VistaFalsa(Integer annoNacimiento,Integer SalarioAnual,String tipoDeBien,Integer valorDeBien){
       this.annoNacimiento = annoNacimiento;
       this.SalarioAnual = SalarioAnual;
       this.tipoDeBien = tipoDeBien;
       this.valorDeBien = valorDeBien;
    }
    @Override
    public Integer consultarAnnoNacimiento() {
        return this.annoNacimiento;
    }
    @Override
    public Integer consultarSalarioAnual() {
        return this.SalarioAnual;
    }
    @Override
    public String consultarTipoDeBien(){
        return tipoDeBien;
    }
    @Override
    public Integer consultarValorDeBien() {
        return this.valorDeBien;
    }
    public void printBienIncorrecto(){
        System.out.println("Algo ha ido mal en la introducciÃ³n de datos, reintroduzca el valor y el tipo: ");
    }
}