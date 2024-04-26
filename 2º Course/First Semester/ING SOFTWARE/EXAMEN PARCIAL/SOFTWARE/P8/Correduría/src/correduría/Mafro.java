package correduría;

/**
 *
 * @author Sol Blasco Di Rosa (blasco.138141)
 */
public class Mafro implements Aseguradora{
    
    private int importe;
    private Cliente cliente;
    private Bien bien;

    public Mafro(Cliente cliente, Bien bien){
        this.cliente = cliente;
        this.bien = bien;
    }
    @Override
    public Integer calcularImporte(Cliente cliente, Bien bien){
        if(bien.tipoBien.equals("vehiculo") && cliente.edad < 20){
            this.importe = Math.floorDiv(bien.valorBien * 5, 100);
        }
        else if(bien.tipoBien.equals("vivienda") &bien.valorBien > 200000 && cliente.salarioAnual < 20000){
            this.importe = Math.floorDiv(bien.valorBien * 2, 100);
        }
        else{
            this.importe = Math.floorDiv(bien.valorBien * 3, 100);
        }
        return importe;
    }
    
    @Override
    public Integer calcularComision(Cliente cliente, Bien bien){
        int comision = 0;
        if(this.importe <= 1000){ //No supera los 1000 -> es <= 1000
            comision = Math.floorDiv(this.importe * 1, 100);
        }
        else if(this.importe > 1000){
            comision = Math.floorDiv(this.importe * 3, 100);
        }
        return comision;
    }
}
