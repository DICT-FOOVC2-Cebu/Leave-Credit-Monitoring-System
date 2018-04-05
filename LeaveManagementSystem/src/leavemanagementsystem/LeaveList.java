/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leavemanagementsystem;

import com.sun.glass.ui.Cursor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author DBBLPwildcatsECE18
 */
public class LeaveList extends javax.swing.JFrame {

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
   boolean isUser = false;
   
    public LeaveList() {
          
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
 System.out.println("Server: "+DB_URL);
        
        initComponents();
        
        Vector columnNames = new Vector();
        columnNames.add("Leave Id");
        columnNames.add("Employee");
        columnNames.add("Department");
        columnNames.add("Position");
        columnNames.add("Date Created");
        columnNames.add("Leave Type");
        columnNames.add("Leave Start");
        columnNames.add("Leave End");
        columnNames.add("No. of Days");
        columnNames.add("Status");
        //columnNames.add("Action");
        
                   // TODO add your handling code here:

        
        
        //stmt = conn.createStatement();
       String sql = "";
       
        System.out.println("Get records into the table...");
      
  
         Vector empList = new Vector();
        
        try{
              Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,properties);
      stmt = conn.createStatement();
      sql = "SELECT * FROM Leaves l join Employee e on l.employeeId = e.employeeId order by l.leaveId desc"; 
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
         int leaveType = rs.getInt("leaveType");
         Date leaveStart = rs.getDate("startDate");
         Date leaveEnd = rs.getDate("endDate");
         Date dateCreated = rs.getDate("createdDate");
         float noOfDays = rs.getFloat("noOfDays");
         int leaveStatus = rs.getInt("leaveStatus");
         int leaveId = rs.getInt("leaveId");
         String status = "";
         String sex = "";
         Vector array = new Vector();
         
         array.add(leaveId);
         array.add(first+" "+mid+" "+last);
         array.add(dept);
         array.add(position);
         long dateStart = leaveStart.getTime();  
          DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
          String startDate = dateFormat.format(dateStart);  
           long dateEnd = leaveEnd.getTime();    
          String endDate = dateFormat.format(dateEnd); 
            long createNum = dateCreated.getTime();    
          String createDateString = dateFormat.format(createNum); 



            array.add(createDateString);
         switch(leaveType){
             case 1:
                 status = "Sick Leave";
             break;
             case 2:
                 status = "Vacation Leave";
             break;
             case 3:
                 status = "Leave Without Pay";
             break;
             case 4:
                 status = "Maternity Leave";
             break;
             case 5:
                 status = "Paternity Leave";
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
          //array.add(sex);
          
          array.add(startDate);
          array.add(endDate);
          array.add(noOfDays);
          String leaveString = "";
             switch(leaveStatus){
             case 1:
                  leaveString = "Pending";
             break;
             case 2:
                 leaveString = "Approved";
             break;
              case 3:
                 leaveString = "Declined";
             break;
         }
          array.add(leaveString);
          JButton btnClearEmployment = new JButton();
          btnClearEmployment.setText("Create Leave");
          //array.add(btnClearEmployment);
          
          empList.add(array);
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
        
        Object[][] data = {
    {"Kathy", "Smith",
     "Snowboarding", new Integer(5), new Boolean(false)},
    {"John", "Doe",
     "Rowing", new Integer(3), new Boolean(true)},
    {"Sue", "Black",
     "Knitting", new Integer(2), new Boolean(false)},
    {"Jane", "White",
     "Speed reading", new Integer(20), new Boolean(true)},
    {"Joe", "Brown",
     "Pool", new Integer(10), new Boolean(false)}
};
        
        JTable table = new JTable(empList, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
       
        
         //java.awt.Cursor defCursor = table.getCursor();
         table.setCursor(new java.awt.Cursor(Cursor.CURSOR_POINTING_HAND));
        //employeePanel.add(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
        //table.setSize(WIDTH,HEIGHT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(70);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        table.getColumnModel().getColumn(6).setPreferredWidth(70);
        table.getColumnModel().getColumn(7).setPreferredWidth(70);
        table.getColumnModel().getColumn(8).setPreferredWidth(70);
         table.getColumnModel().getColumn(9).setPreferredWidth(70);
        table.addMouseListener(new MouseAdapter() {
  public void mouseClicked(MouseEvent e) {

       int row = table.rowAtPoint(e.getPoint());
        int col = table.columnAtPoint(e.getPoint());

        //build your address / link
         Object valueInCell = (Object)table.getValueAt(row, 0);
            LeaveDetails leaveDetails = new LeaveDetails(Integer.parseInt(""+valueInCell));
            leaveDetails.setVisible(true);
            
        System.out.println("Row: "+row);
        System.out.println("Column: "+col);
        System.out.println("Value: "+valueInCell);
      }
    });
        //TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        //table.getColumn("Action").setCellRenderer(buttonRenderer);
        employeePanel.setLayout(new BorderLayout());
        employeePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        employeePanel.add(table, BorderLayout.CENTER);
        
    }
    
     public LeaveList(int employeeId) {
         
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
        initComponents();
        isUser = true;
        Vector columnNames = new Vector();
        columnNames.add("Leave Id");
        columnNames.add("Employee");
        columnNames.add("Department");
        columnNames.add("Position");
        columnNames.add("Date Created");
        columnNames.add("Leave Type");
        columnNames.add("Leave Start");
        columnNames.add("Leave End");
        columnNames.add("No. of Days");
        columnNames.add("Status");
        //columnNames.add("Action");
        
                   // TODO add your handling code here:

        
        
        //stmt = conn.createStatement();
       String sql = "";
       
        System.out.println("Get records into the table...");
      
  
         Vector empList = new Vector();
        
        try{
              Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,properties);
      stmt = conn.createStatement();
      sql = "SELECT * FROM Leaves l join Employee e on l.employeeId = e.employeeId "
              + "where e.employeeId = "+employeeId+" order by l.leaveId desc"; 
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
         int leaveType = rs.getInt("leaveType");
         Date leaveStart = rs.getDate("startDate");
         Date leaveEnd = rs.getDate("endDate");
         Date dateCreated = rs.getDate("createdDate");
         float noOfDays = rs.getFloat("noOfDays");
         int leaveStatus = rs.getInt("leaveStatus");
         int leaveId = rs.getInt("leaveId");
         String status = "";
         String sex = "";
         Vector array = new Vector();
         
         array.add(leaveId);
         array.add(first+" "+mid+" "+last);
         array.add(dept);
         array.add(position);
         long dateStart = leaveStart.getTime();  
          DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
          String startDate = dateFormat.format(dateStart);  
           long dateEnd = leaveEnd.getTime();    
          String endDate = dateFormat.format(dateEnd); 
            long createNum = dateCreated.getTime();    
          String createDateString = dateFormat.format(createNum); 



            array.add(createDateString);
         switch(leaveType){
             case 1:
                 status = "Sick Leave";
             break;
             case 2:
                 status = "Vacation Leave";
             break;
             case 3:
                 status = "Leave Without Pay";
             break;
             case 4:
                 status = "Maternity Leave";
             break;
             case 5:
                 status = "Paternity Leave";
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
          //array.add(sex);
          
          array.add(startDate);
          array.add(endDate);
          array.add(noOfDays);
          String leaveString = "";
             switch(leaveStatus){
             case 1:
                  leaveString = "Pending";
             break;
             case 2:
                 leaveString = "Approved";
             break;
              case 3:
                 leaveString = "Declined";
             break;
         }
          array.add(leaveString);
          JButton btnClearEmployment = new JButton();
          btnClearEmployment.setText("Create Leave");
          //array.add(btnClearEmployment);
          
          empList.add(array);
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
        
        Object[][] data = {
    {"Kathy", "Smith",
     "Snowboarding", new Integer(5), new Boolean(false)},
    {"John", "Doe",
     "Rowing", new Integer(3), new Boolean(true)},
    {"Sue", "Black",
     "Knitting", new Integer(2), new Boolean(false)},
    {"Jane", "White",
     "Speed reading", new Integer(20), new Boolean(true)},
    {"Joe", "Brown",
     "Pool", new Integer(10), new Boolean(false)}
};
        
        JTable table = new JTable(empList, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
       
        
         //java.awt.Cursor defCursor = table.getCursor();
         table.setCursor(new java.awt.Cursor(Cursor.CURSOR_POINTING_HAND));
        //employeePanel.add(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
        //table.setSize(WIDTH,HEIGHT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(70);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        table.getColumnModel().getColumn(6).setPreferredWidth(70);
        table.getColumnModel().getColumn(7).setPreferredWidth(70);
        table.getColumnModel().getColumn(8).setPreferredWidth(70);
         table.getColumnModel().getColumn(9).setPreferredWidth(70);
        table.addMouseListener(new MouseAdapter() {
  public void mouseClicked(MouseEvent e) {

       int row = table.rowAtPoint(e.getPoint());
        int col = table.columnAtPoint(e.getPoint());

        //build your address / link
         Object valueInCell = (Object)table.getValueAt(row, 0);
            
         if(isUser){
             LeaveDetails leaveDetails = new LeaveDetails(Integer.parseInt(""+valueInCell),isUser);
             leaveDetails.setVisible(true);
         }
         else{
             LeaveDetails leaveDetails = new LeaveDetails(Integer.parseInt(""+valueInCell));
             leaveDetails.setVisible(true);
         }
            
            
        System.out.println("Row: "+row);
        System.out.println("Column: "+col);
        System.out.println("Value: "+valueInCell);
      }
    });
        //TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        //table.getColumn("Action").setCellRenderer(buttonRenderer);
        employeePanel.setLayout(new BorderLayout());
        employeePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        employeePanel.add(table, BorderLayout.CENTER);
        
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
        jLabel1.setText("Leave List");

        javax.swing.GroupLayout employeePanelLayout = new javax.swing.GroupLayout(employeePanel);
        employeePanel.setLayout(employeePanelLayout);
        employeePanelLayout.setHorizontalGroup(
            employeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 752, Short.MAX_VALUE))
                    .addComponent(employeePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(employeePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
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
