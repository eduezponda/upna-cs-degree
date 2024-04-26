public class Barco
{
    private int metrosEslora;
    private int camarote;
    
    
    public Barco (int pmetrosEslora, int pcamarote)
    {
    	metrosEslora = pmetrosEslora;
    	camarote = pcamarote;
    	
    }
    public int ObtenerMetros()
    {
    	return metrosEslora;
    }
    public int ObtenerCamarotes()
    {
    	return camarote;
    }
}
