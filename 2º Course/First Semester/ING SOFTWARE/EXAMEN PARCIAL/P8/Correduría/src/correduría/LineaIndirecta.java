package correduría;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sol Blasco Di Rosa (blasco.138141)
 */
public class LineaIndirecta implements Aseguradora{
    private int importe;
    private Cliente cliente;
    private Bien bien;
    
    public LineaIndirecta(Cliente cliente, Bien bien){
        this.cliente = cliente;
        this.bien = bien;
    }
    
    @Override
    
    public Integer calcularImporte(Cliente cliente, Bien bien){
        if((bien.tipoBien.equals("vehiculo") && bien.valorBien <= 20000) || (bien.tipoBien.equals("vivienda") && bien.valorBien <= 150000)){
            this.importe = Math.floorDiv(bien.valorBien * 4, 100);
        }
        else if(bien.tipoBien.equals("vehiculo") && cliente.edad >= 60){
            this.importe = Math.floorDiv(bien.valorBien * 6, 100);
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
            comision = Math.floorDiv(this.importe * 4, 100);
        }
        return comision;
    }
}
