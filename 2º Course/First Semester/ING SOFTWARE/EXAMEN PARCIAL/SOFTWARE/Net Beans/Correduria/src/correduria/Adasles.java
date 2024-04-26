package correduria;

public class Adasles implements Aseguradora{

    private int poliza;
    
    @Override
    public int calcularImporte(Bien bien,Cliente cliente){
        if (bien.tipoDeBien.equals("vehiculo") && (cliente.Edad()<20 || cliente.Edad()>60)){
            this.poliza = Math.floorDiv(bien.valorDeBien*6,100);
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
            return(Math.floorDiv(this.poliza*5,100));
    }
    @Override
    public String nombre(){
        return("ADASLES");
    }
}