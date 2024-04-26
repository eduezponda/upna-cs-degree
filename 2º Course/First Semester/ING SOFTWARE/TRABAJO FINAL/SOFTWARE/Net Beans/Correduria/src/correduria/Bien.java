package correduria;

public class Bien{

    public String tipoDeBien;
    public int valorDeBien;

    public Bien(){
        this.tipoDeBien = "";
        this.valorDeBien = 0;
    }
    public Bien(String tipoDeBien,int valorDeBien) {
        this.tipoDeBien = tipoDeBien;
        this.valorDeBien = valorDeBien;
    }
    public boolean bienCorrecto(String tipo,int valor){
        boolean correcto= true;
        if(!tipo.equals("vehiculo") && !tipo.equals("vivienda")){
            correcto = false;
        }
        if((tipo.equals("vehiculo")&& valor>50000) | (tipo.equals("vivienda")&& valor<50000)){
            correcto = false;
        }
        return correcto;
    }
}