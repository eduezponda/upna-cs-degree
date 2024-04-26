package correduria;

public class Mafro implements Aseguradora{

    private int poliza;
    
    public int calcularImporte(Bien bien,Cliente cliente){
        if (bien.tipoDeBien.equals("vehiculo") && cliente.Edad()<20){
            this.poliza = Math.floorDiv(bien.valorDeBien*5,100);
        }
        else if (bien.tipoDeBien.equals("vivienda") && bien.valorDeBien>200000 && cliente.salarioAnual<20000){
            this.poliza = Math.floorDiv(bien.valorDeBien*2,100);
        }
        else{
            this.poliza = Math.floorDiv(bien.valorDeBien*3,100);
        }
        return(this.poliza);
        
    }
    public int calcularComision(){
        if(this.poliza<=1000)
            return(Math.floorDiv(this.poliza*1,100));
        else
            return(Math.floorDiv(this.poliza*3,100));
    }
    public String nombre(){
        return("MAFRO");
    }
}