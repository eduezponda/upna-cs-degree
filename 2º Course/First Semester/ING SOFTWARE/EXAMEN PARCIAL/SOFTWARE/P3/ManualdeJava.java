GUÍA DE JAVA

//Compilar un documento:
javac HolaMundo.java

//Ejecutar un programa:
java HolaMundo

//Main importante en todo programa
public static void main(String[] args) {}

//Cabeceras importantes:
import java.lang.*               //Para usar System.out, string...
import java.lang.String;        //Para el tipo string que puede almacenar cadenas de caracteres entre "..."
import java.util.Scanner;      //Para leer datos
import java.lang.Enum;        //Para el tipo enum

//Crear una clase
class hola
{

//VARIABLES
    //Tipos
        //byte<short<int<long
        //float<double
        //boolean
        //char
        
    //Declarar modificadores (tipo nombre = valor inicial)
        int x = 0;
        
    //Pedir valor
        Scanner reader = new Scanner(System.in);
        int valor = 0;
        valor = reader.next.Int(); //El nombre de reader y el valor pueden cambiarse
    
    //Pedir valor por linea de comandos
    int valor = Integer.parseInt(args[0]);
    
    //Generar número aleatorio
    (int)(Math.random()*6)+1
    

//CONSTANTES
    public static final float NUMERO P1 = 3.141516;

//TABLAS
    //Declarar tablas : tipo[] nombre;
    int[] unaTabla;
    unaTabla = new int[10];

    //Acceso a tablas
    unaTabla[0] = 10;

    //Meter nuestros valores
    int[] unaTabla = {1, 2, 3};

    //Tablas multidimensionales
    String[][] nombres = {{"G", "M", "S"},{"B", "M"}};
    
    //Imprimir valores en tablas multidimensionales (nombres[grupo][posicion en grupo])...posiciones desde 0
    System.out.println(nombres[0][0] + nombres[1][0]);
    
    //Tamaño de una tabla
    unaTabla.lenght
    
//IMPRIMIR
    System.out.println("Texto" + variables);
    
//SENTENCIAS
    //IF-THEN
        if (expresión booleana)
        {
            sentencia
        }
    //IF-THEN-ELSE
        if (expresión booleana)
        {
            sentencia
        }
        else
        {
            sentencia 
        }
    //SWITCH: Se compara la expresión con los valores y se ejecuta la que se cumple, si no, el default. No olvidar BREAK.
        switch (expresión)
        {
            case valor1 : Sentencia1; break;
            case valor2 : Sentencia2; break;
            default: SentenciaNMas1; break;
        }
    //WHILE
        while
        {
            
        }
    //DO-WHILE
        do
        {
        
        } while (expresión booleana);
    //FOR
        for (inicio, terminar, aumento)
        {
        
        }

}
//CLASES
	//Variables y metodos a un cierto tipo de objetos
	public class Persona 
	{
		//Propiedades 
		private int Propiedad1
		private String Propiedad2
		
		//Métodos
		public String getPropiedad2() //Dará Propiedad2 al void principal donde llamamos a esta función.
		{
				return Propiedad2; 
		}
		
		//Objeto
		public void setPropiedad1 (String _Propiedad1) 
		{
				this.Propiedad1 = _Propiedad1;
		}		
				
	}
	
	//En el VOID
	public static void main(String[] args)
	{
		//CLASE var = new CLASE(); DEFINIR;
		//var.objeto(VARIABLES...); LLAMAR;
	}
