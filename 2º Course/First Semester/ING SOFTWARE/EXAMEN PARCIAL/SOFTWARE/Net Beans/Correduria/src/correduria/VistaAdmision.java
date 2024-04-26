package correduria;

public interface VistaAdmision {
    Integer consultarAnnoNacimiento();
    Integer consultarSalarioAnual();
    String consultarTipoDeBien();
    Integer consultarValorDeBien();
    void printBienIncorrecto();
}