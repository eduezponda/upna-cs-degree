public class Amarre
{
	private Barco b0;
	private int numDiasAmarre;
    
    public Amarre (Barco pb0,  int pnumDiasAmarre) {
        b0 = pb0;
        numDiasAmarre = pnumDiasAmarre;
    }
    
    
    public int obtenerPrecio()
    {
    	return numDiasAmarre * b0.esObtenerPrecio();
    }
    
   
}
