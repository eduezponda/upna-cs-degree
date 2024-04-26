public class Puerto{

    private Amarre[] plaza = new Amarre[3];
    
    public Puerto (Amarre a0,  Amarre a1, Amarre a2) {
        plaza[0] = a0;
        plaza[1] = a1;
        plaza[2] = a2;
      
    }
    public int precio() {
    
    	int i,s;
    	s = 0;
        for (i = 0; i < 3; i = i + 1) {
           
           	s = s + plaza[i].obtenerDias()*(10*plaza[i].esObtenerMetros()+5*plaza[i].esObtenerCamarotes());
            
        }
        return s;
    }
        
}
