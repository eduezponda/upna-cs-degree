public class Yate extends Barco
{
	public Yate (int metrosEslora, int camarote)
	{
		super(metrosEslora,camarote);
	}
	
    public int esObtenerPrecio()
    {
    	return 10*metrosEslora + 5*camarote + 10000;
    }
}
