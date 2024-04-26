public class EjecutorPuerto{
	public static void main(String[] args){
	
		Barco b1 = new Barco(20,1);
        Barco b2 = new Barco(25,1);
        Barco b3 = new Barco(40,2);
      
		Amarre a1 = new Amarre(b1,10);
		Amarre a2 = new Amarre(b2,15);
		Amarre a3 = new Amarre(b3,25);
		
		Puerto puerto = new Puerto(a1,a2,a3);
        
        int ganancias = puerto.precio();
        
        System.out.println("Las ganancias son : " + ganancias);
	}
}
