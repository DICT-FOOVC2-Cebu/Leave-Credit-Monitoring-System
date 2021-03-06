/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leavemanagementsystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author DBBLPwildcatsECE18
 */
public class EmployeeList extends javax.swing.JFrame {

    /**
     * Creates new form EmployeeList
     */
    
     static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    String DB_URL = "jdbc:mysql://localhost/dbict";

   //  Database credentials
   String USER = "root";
   String PASS = "root";
   Connection conn = null;
   Statement stmt = null;
   PreparedStatement preparedStmt = null;
   
    public EmployeeList() {
           
    Properties properties = new Properties();
   
       
try {
     BufferedReader br =  new BufferedReader(new FileReader("systemLocator.txt"));
    StringBuilder sb = new StringBuilder();
    String line = br.readLine();

    while (line != null) {
        sb.append(line);
        sb.append(System.lineSeparator());
        line = br.readLine();
    }
    String everything = sb.toString();
    br.close();
    System.out.println("Ip Config: "+everything);
    StringBuilder url = new StringBuilder(100);
    DB_URL = everything.replaceAll("\\s","");
     properties.setProperty("user",USER);
    properties.setProperty("password",PASS);
    properties.setProperty("useSSL", "false");
    properties.setProperty("verifyServerCertificate","false");
    
    
} catch(IOException ex){
    System.out.println(ex);
    ex.printStackTrace();
    DB_URL = "jdbc:mysql://localhost/dbict";
   USER = "root";
   PASS = "";
    properties.setProperty("user",USER);
    properties.setProperty("password",PASS);
}
 System.out.print("Server: "+DB_URL);
        initComponents();
        
        Vector columnNames = new Vector();
        columnNames.add("Employee");
        columnNames.add("Department");
        columnNames.add("Position");
        columnNames.add("Maritalstatus");
        columnNames.add("Gender");
        columnNames.add("Vacation Leave");
        columnNames.add("Sick Leave");
        columnNames.add("Maternity/Patenity Leave");
        //columnNames.add("Action");
        
                   // TODO add your handling code here:

        
        
        //stmt = conn.createStatement();
       String sql = "";
       
        System.out.println("Get records into the table...");
      
  
         Vector empList = new Vector();
         List emps = new ArrayList();
        
        try{
              Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,properties);
      stmt = conn.createStatement();
      sql = "SELECT * FROM EMPLOYEE"; 
      ResultSet rs = stmt.executeQuery(sql);
      
    
      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         int id  = rs.getInt("employeeId");
         //int age = rs.getInt("age");
         String first = rs.getString("firstname");
         String last = rs.getString("lastname");
         String mid = rs.getString("middlename");
         String address = rs.getString("address");
         String dept = rs.getString("department");
         String position = rs.getString("position");
         int maritalStatus = rs.getInt("maritalstatus");
         int gender = rs.getInt("gender");
         float vacationBalance = rs.getFloat("vacationleavebalance");
         float sickBalance = rs.getFloat("sickleavebalance");
         float maternityBalance = rs.getFloat("birthleavebalance");
         String status = "";
         String sex = "";
         Vector array = new Vector();
         
         array.add(first+" "+mid+" "+last);
         array.add(dept);
         array.add(position);
         
         switch(maritalStatus){
             case 1:
                 status = "Single";
             break;
             case 2:
                 status = "Married";
             break;
             case 3:
                 status = "Widow(er)";
             break;
             case 4:
                 status = "Separated";
             break;
         }
         
         array.add(status);
          switch(gender){
             case 1:
                 sex = "Male";
             break;
             case 2:
                 sex = "Female";
             break;
         }
          array.add(sex);
          array.add(""+vacationBalance);
          array.add(""+sickBalance);
          array.add(""+maternityBalance);
          JButton btnClearEmployment = new JButton();
          btnClearEmployment.setText("Create Leave");
          //array.add(btnClearEmployment);
          
          empList.add(array);
          emps.add(id);
      }
      
          rs.close();
      stmt.close();
      conn.close();
       //JOptionPane.showMessageDialog(null,"Successfully Created Employee", "InfoBox: Employee", JOptionPane.INFORMATION_MESSAGE);
       
     
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
         if(preparedStmt!=null){
             preparedStmt.close();
         }
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }
        
         Date d = new Date(System.currentTimeMillis());
         Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
         
        if(month==12&&day==31){
            for(int x=0;x<empList.size();x++){
                 
                Object empId = emps.get(x);
                
                resetLeaves(Integer.parseInt(empId.toString()));
                
            }
        }
        
        JTable table = new JTable(empList, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        //employeePanel.add(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
        //table.setSize(WIDTH,HEIGHT);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(180);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(70);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        table.getColumnModel().getColumn(6).setPreferredWidth(70);
        table.getColumnModel().getColumn(7).setPreferredWidth(70);
        table.getColumnModel().getColumn(7).setPreferredWidth(80);
        //TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        //table.getColumn("Action").setCellRenderer(buttonRenderer);
        employeePanel.setLayout(new BorderLayout());
        employeePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        employeePanel.add(table, BorderLayout.CENTER);
        
    }
    
    public boolean resetLeaves(int employeeId){
        
        boolean result = false;
         Properties properties = new Properties();
   
       
try {
     BufferedReader br =  new BufferedReader(new FileReader("systemLocator.txt"));
    StringBuilder sb = new StringBuilder();
    String line = br.readLine();

    while (line != null) {
        sb.append(line);
        sb.append(System.lineSeparator());
        line = br.readLine();
    }
    String everything = sb.toString();
    br.close();
    System.out.println("Ip Config: "+everything);
    StringBuilder url = new StringBuilder(100);
    DB_URL = everything.replaceAll("\\s","");
     properties.setProperty("user",USER);
    properties.setProperty("password",PASS);
    properties.setProperty("useSSL", "false");
    properties.setProperty("verifyServerCertificate","false");
    
    
} catch(IOException ex){
    System.out.println(ex);
    ex.printStackTrace();
    DB_URL = "jdbc:mysql://localhost/dbict";
   USER = "root";
   PASS = "";
    properties.setProperty("user",USER);
    properties.setProperty("password",PASS);
}
String sql;
        try{
              Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,properties);
      stmt = conn.createStatement();
      sql = "update employee set sickleavebalance = 10 , vacationleavebalance = 10 "
              + ", birthleavebalance = 10 where employeeId = "+employeeId; 
      stmt.executeUpdate(sql);
      
    
      //STEP 5: Extract data from result set
      result = true;
      stmt.close();
      conn.close();
       //JOptionPane.showMessageDialog(null,"Successfully Created Employee", "InfoBox: Employee", JOptionPane.INFORMATION_MESSAGE);
       
     
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
         if(preparedStmt!=null){
             preparedStmt.close();
         }
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }
        
        return result;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        employeePanel = new javax.swing.JPanel();

        setLocation(new java.awt.Point(250, 50));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Employee List");

        javax.swing.GroupLayout employeePanelLayout = new javax.swing.GroupLayout(employeePanel);
        employeePanel.setLayout(employeePanelLayout);
        employeePanelLayout.setHorizontalGroup(
            employeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 755, Short.MAX_VALUE)
        );
        employeePanelLayout.setVerticalGroup(
            employeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(employeePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(employeePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmployeeList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel employeePanel;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
