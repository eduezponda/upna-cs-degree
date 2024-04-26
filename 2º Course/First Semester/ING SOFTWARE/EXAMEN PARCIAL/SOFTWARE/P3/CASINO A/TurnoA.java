public class Turno{
    
    private Dado dado1,dado2;
    private int suma;
    
    public void turno(){
    
        dado1 = new Dado();
        dado2 = new Dado();
        dado1.tirada();
        System.out.println("Resultado de la primera tirada: "+ dado1.resultado());
        dado2.tirada();
        System.out.println("Resultado de la segunda tirada: "+ dado2.resultado());
        suma = dado1.resultado() + dado2.resultado();
        if(suma == 2){
            System.out.println("¡Te has sacado la poya!");
        }
        
    }
    
    public int resultado(){
        return suma;
    }
    
}
