package correduria;

public interface Aseguradora{
    int calcularImporte(Bien bien,Cliente cliente);
    int calcularComision();
    public String nombre();
}