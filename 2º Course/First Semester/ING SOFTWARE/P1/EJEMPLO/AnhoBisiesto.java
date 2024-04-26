public class AnhoBisiesto{
    public static void main(String[] args){
// Lee el primer argumento pasado por la línea de comandos
        int anho = Integer.parseInt(args[0]);
        boolean bisiesto;
        bisiesto = ((anho % 4 == 0) && (anho % 100 != 0))|| (anho % 400 == 0);
        if(bisiesto){
            System.out.println("El año " + anho + " es bisiesto");
        }
        else {
            System.out.println("El año " + anho + " no es bisiesto");
        }
    }
}
