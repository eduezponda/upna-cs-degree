package correduria;

public class LineaIndirecta implements Aseguradora{

    private int poliza;
    
    @Override
    public int calcularImporte(Bien bien,Cliente cliente){
        if ((bien.tipoDeBien.equals("vehiculo") && bien.valorDeBien<20000) || (bien.tipoDeBien.equals("vivienda") && bien.valorDeBien<150000)){
            this.poliza = Math.floorDiv(bien.valorDeBien*4,100);
        }
        else if (bien.tipoDeBien.equals("vehiculo") && cliente.Edad()>60){
            this.poliza = Math.floorDiv(bien.valorDeBien*6,100);
        }
        else{
            this.poliza = Math.floorDiv(bien.valorDeBien*3,100);
        }
        return(this.poliza);
    }
    @Override
    public int calcularComision(){
        if(this.poliza<=1000)
            return(Math.floorDiv(this.poliza*1,100));
        else
            return(Math.floorDiv(this.poliza*4,100));
    }
    @Override
    public String nombre(){
        return("LINEA INDIRECTA");
    }
}