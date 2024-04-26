public class EjecutorPuerto{
	public static void main(String[] args){
	
		Barco b1 = new Barco(20,1);
        Velero v1 = new Velero(25,1,2);
        Yate y1 = new Yate(40,2);
        Barco b2 = new Barco(10,1);
        
      
		Amarre a1 = new Amarre(b1,10);
		Amarre a2 = new Amarre(v1,15);
		Amarre a3 = new Amarre(y1,25);
		Amarre a4 = new Amarre(b2,5);
		
		Puerto puerto = new Puerto(a1,a2,a3,a4);
        
        int ganancias = puerto.precio();
        
        System.out.println("Las ganancias son : " + ganancias);
	}
}
