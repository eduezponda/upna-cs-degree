package correduria;

public class CatalanaOriente implements Aseguradora{

    private int poliza;
    
    @Override
    public int calcularImporte(Bien bien,Cliente cliente){
        if (bien.tipoDeBien.equals("vehiculo") && cliente.Edad()<20){
            this.poliza = Math.floorDiv(bien.valorDeBien*7,100);
        }
        else{
            this.poliza = Math.floorDiv(bien.valorDeBien*2,100);
        }
        return(this.poliza);
        
    }
    @Override
    public int calcularComision(){
        if(this.poliza<=1000)
            return(Math.floorDiv(this.poliza*1,100));
        else
            return(Math.floorDiv(this.poliza*6,100));
    }
    @Override
    public String nombre(){
        return("CATALANA ORIENTE");
    }
}