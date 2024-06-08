package objetos;

import java.io.Serializable;
import java.util.ArrayList;

public class ComplexCompany implements Serializable {
 
  private static final long serialVersionUID = 12345679L;     
    
  private final String name;
  private ComplexEmployee president;
  private final ArrayList<ComplexDepartment> departments;

  public ComplexCompany (final String name) {
    this.name = name;
    departments = new ArrayList();
  }

  public String getName () {
    return this.name;
  }

  public void addDepartment (final ComplexDepartment dept) {
    departments.add(dept);
  }

  public ComplexEmployee getPresident () {
    return this.president;
  }

  public void addPresident (final ComplexEmployee e) {
    this.president = e;
  }

  public void printCompanyObject () {
    System.out.println("The company name is " + getName());
    System.out.println("The company president is " + getPresident().getName());
    System.out.println(" ");

    for (final ComplexDepartment j: departments) {
      System.out.println("   The department name is " + j.getName());
      System.out.println("   The department manager is " + j.getManager().getName());
      System.out.println("");
    }
  }
  
  public static ComplexCompany getInstance () {

    final ComplexCompany comp = new ComplexCompany("Cash from Chaos");
    final ComplexEmployee emp0 = new ComplexEmployee("Bernard Madoff", 1000);
    comp.addPresident(emp0);

    final ComplexDepartment sales = new ComplexDepartment("C");
    final ComplexEmployee emp1 = new ComplexEmployee("Michele Sindona", 1200);
    sales.addManager(emp1);
    comp.addDepartment(sales);

    final ComplexDepartment accounting = new ComplexDepartment("E");
    final ComplexEmployee emp2 = new ComplexEmployee("Paul Marcinkus", 1230);
    accounting.addManager(emp2);
    comp.addDepartment(accounting);

    final ComplexDepartment maintenance = new ComplexDepartment("B");
    final ComplexEmployee emp3 = new ComplexEmployee("ILdH", 1020);
    maintenance.addManager(emp3);
    comp.addDepartment(maintenance);

    return comp;
  }  

}