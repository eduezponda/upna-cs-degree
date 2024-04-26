package correduria;

public class Oferta{

    public Aseguradora aseguradora;
    public int importe;
    public int comision;

    public Oferta(Aseguradora aseguradora){
        this.aseguradora = aseguradora;
    }
    public void calcularOferta(Bien bien,Cliente cliente){
        this.importe = aseguradora.calcularImporte(bien, cliente);
        this.comision = aseguradora.calcularComision();
    }
    
}