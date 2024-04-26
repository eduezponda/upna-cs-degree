//package ofertaventajosa;

import java.util.Scanner;

public class Ejecutor {

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) 
    {
      System.out.println("Introduce los datos del cliente: ");
      System.out.println("");
      //System.out.print("Introduce la fecha de nacimiento: ");
      //java.util.Date fecha = new Date();
      System.out.print("Introduce la edad del cliente: ");
      int edad = scan.nextInt();
      
      while (edad >= 122 || edad < 0)
      {
        System.out.println("ERROR (edad > 0 y edad < 122)");
        System.out.print("Introduce la edad del cliente: ");
        edad = scan.nextInt();
      }
      
      //while ()
      
      System.out.print("Introduce el salario del cliente: ");
      int salario = scan.nextInt();
      System.out.print("Introduce el tipo de bien/activo (Vehiculo/Vivienda): ");
      String tipoBien = scan.next();
      System.out.print("Introduce el valor del bien/activo: ");
      int valor = scan.nextInt();
      
      while (tipoBien.equals("Vehiculo") && valor > 50000)
      {
        System.out.println("ERROR (valor < 50000 si es un vehículo)");
        System.out.print("Introduce el valor del vehículo: ");
        valor = scan.nextInt();
      }
      while (tipoBien.equals("Vivienda") && valor < 50000)
      {
        System.out.println("ERROR (valor > 50000 si es una vivienda)");
        System.out.print("Introduce el valor de la vivienda: ");
        valor = scan.nextInt();
      }
      
      Bien bien = new Bien (tipoBien, valor);
      Cliente cliente = new Cliente (edad, bien, salario);
      LineaIndirecta lineaIndirecta = new LineaIndirecta ();
      Adasles adasles = new Adasles();
      Mafro mafro = new Mafro();
      Oferta oferta = new Oferta();
      
      lineaIndirecta.calcularImporte(cliente);
      lineaIndirecta.calcularComision();
      
      //System.out.println(lineaIndirecta.devolverImporte());
      //System.out.println(lineaIndirecta.devolverComision());
      
      adasles.calcularImporte(cliente);
      adasles.calcularComision();
      
      mafro.calcularImporte(cliente);
      mafro.calcularComision();
      
      oferta.calcularOferta (cliente, lineaIndirecta, adasles, mafro);
      
      //int precioOferta = oferta.devolverOferta();
      String nombre = oferta.devolverNombre();
      
      if (nombre.equals("MAFRO"))
      {
        System.out.println("MAFRO | " + mafro.devolverImporte() + " | " + (int)mafro.devolverComision());
      }
      else if (nombre.equals("ADASLES"))
      {
        System.out.println("ADASLES | " + adasles.devolverImporte() + " | " + (int)adasles.devolverComision());
      }
      else
      {
        System.out.println("LINEAINDIRECTA | " + lineaIndirecta.devolverImporte() + " | " + (int)lineaIndirecta.devolverComision());
      }     
    }
    
}
