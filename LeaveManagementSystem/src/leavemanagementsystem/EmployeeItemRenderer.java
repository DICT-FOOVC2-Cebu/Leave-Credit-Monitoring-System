/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leavemanagementsystem;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author DBBLPwildcatsECE18
 */
public class EmployeeItemRenderer extends BasicComboBoxRenderer{
    
  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    super.getListCellRendererComponent(list, value, index, isSelected,
        cellHasFocus);

    EmployeeItem item = (EmployeeItem) value;
    setText(item.getDescription().toUpperCase());

    return this;
  }
}