public class Velero extends Barco
{
	protected int mastiles;
	
	public Velero (int metrosEslora, int camarote, int nmastiles)
	{
		super(metrosEslora,camarote);
		mastiles = nmastiles;
	}
	
    public int esObtenerPrecio()
    {
    	return 10*metrosEslora + 5*camarote + 15 * mastiles; // necesito que sean protected en barco para poder acceder a metros eslora y camarote desde velero
    }
	
}


