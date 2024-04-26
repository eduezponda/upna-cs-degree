public class EjecutorLibreria{
	public static void main(String[] args){
	
		Libro l1 = new Libro("Clean Code", "Ensayo", 210, 20);
        Libro l2 = new Libro("The Pragmatic Programmer", "Ensayo", 100, 40);
        Libro l3 = new Libro("Código Sostenible", "Guía Técnica", 250, 30);
        Libro l4 = new Libro("The Mythical Mouth-Man", "Ensayo", 50, 25);
        Libro l5 = new Libro("Extreme Programming", "Ensayo", 80, 19);

        Libreria libreria = new Libreria(l1, l2, l3, l4, l5);
        
        libreria.destacado();
	}
}
