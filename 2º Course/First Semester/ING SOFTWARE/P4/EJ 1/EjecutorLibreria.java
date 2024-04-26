import java.util.Scanner;

public class EjecutorLibreria {
    public static void main (String[] args) {

        Libro[] libreria = new Libro[5];

        libreria[0] = new Ensayo("Clean Code", "Ensayo", 210, 20);
        libreria[1] = new Ensayo("The Pragmatic Programmer", "Ensayo", 100, 40);
        libreria[2] = new GuiaTecnica("Código Sostenible", "Guía Técnica", 250, 30);
        libreria[3] = new Ensayo("The Mythical Mouth-Man", "Ensayo", 50, 25);
        libreria[4] = new Ensayo("Extreme Programming", "Ensayo", 80, 19);

        Libreria estante = new Libreria(libreria);

        estante.destacado();
    }
}
