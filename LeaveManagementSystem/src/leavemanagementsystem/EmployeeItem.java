/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leavemanagementsystem;

/**
 *
 * @author DBBLPwildcatsECE18
 */
public class EmployeeItem {
    

  private int id;
  private String description;

  public EmployeeItem(int id, String description) {
    this.id = id;
    this.description = description;
  }

    EmployeeItem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public String toString() {
    return description;
  }
}
