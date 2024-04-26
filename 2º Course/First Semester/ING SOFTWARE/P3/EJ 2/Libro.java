
public class Libro{

	private String nombre;
	private String tipo;
	private int paginas;
	private float precio;
	
	//constructor
	public Libro(String pnombre, String ptipo, int ppaginas, float pprecio)
	{
		nombre = pnombre;
		tipo = ptipo;
		paginas = ppaginas;
		precio = pprecio;
	}
	public String obtenerTitulo() {
	
		return nombre;
	}
	public Boolean esDestacado(){
	
		return ((tipo == "Guía Técnica" && paginas > 200) || (tipo == "Ensayo" && precio <= 20));
	}
}
