public class Barco
{
    protected int metrosEslora;
    protected int camarote;
    
    
    public Barco (int pmetrosEslora, int pcamarote)
    {
    	metrosEslora = pmetrosEslora;
    	camarote = pcamarote;
    	
    }
   
    public int esObtenerPrecio()
    {
    	return 10*metrosEslora + 5*camarote;
    }
}
