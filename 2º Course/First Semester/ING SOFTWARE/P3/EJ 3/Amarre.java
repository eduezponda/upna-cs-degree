public class Amarre
{
	private Barco b0;
	private int numDiasAmarre;
    
    public Amarre (Barco pb0,  int pnumDiasAmarre) {
        b0 = pb0;
        numDiasAmarre = pnumDiasAmarre;
    }
    public int obtenerDias()
    {
    	return numDiasAmarre;
    }
    public int esObtenerMetros()
    {
    	return b0.ObtenerMetros();
    }
    public int esObtenerCamarotes()
    {
    	return b0.ObtenerCamarotes();
    }
    
   
}
