package objetos;

import java.io.Serializable;

public class ComplexDepartment implements Serializable {
    
  private static final long serialVersionUID = 12345679L;  
    
  private final String name;
  private ComplexEmployee manager;

  public ComplexDepartment (final String name) {
    this.name = name;
  }

  public String getName () {
    return this.name;
  }

  public ComplexEmployee getManager () {
    return this.manager;
  }

  public void addManager (final ComplexEmployee e) {
    manager = e;
  }
}