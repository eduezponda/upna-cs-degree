package objetos;

import java.io.Serializable;

public class ComplexEmployee implements Serializable {
    
  private static final long serialVersionUID = 12345679L;  
  
  private final String name;
  private final int salary;

  /** Creates a new instance of ComplexEmployee */
  public ComplexEmployee (final String name, final int salary) {
    this.name = name;
    this.salary = salary;
  }

  public String getName () {
    return name;
  }

  public int getSalary () {
    return this.salary;
  }
}
