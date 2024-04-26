public class Puerto{

    private Amarre[] plaza = new Amarre[4];
    
    public Puerto (Amarre a0,  Amarre a1, Amarre a2, Amarre a3) {
        plaza[0] = a0;
        plaza[1] = a1;
        plaza[2] = a2;
        plaza[3] = a3;
      
    }
    public int precio() {
    
    	int i,s;
    	s = 0;
        for (i = 0; i < 4; i = i + 1) {
           
           	s = s + plaza[i].obtenerPrecio();
           	
            
        }
        return s;
    }
        
}
