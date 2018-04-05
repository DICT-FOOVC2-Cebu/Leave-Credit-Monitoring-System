/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leavemanagementsystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author DBBLPwildcatsECE18
 */
public class LeaveDetails extends javax.swing.JFrame implements ActionListener {
   
 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   String DB_URL = "jdbc:mysql://localhost/dbict";

   //  Database credentials
   String USER = "root";
   String PASS = "root";
   Connection conn = null;
   Statement stmt = null;
   PreparedStatement preparedStmt = null;
   int employeeSelected = 1;
   int leaveTypeId = 0;
   int empLeaveId = 0;
   int empId = 0;
   float numberOfDays = 0;
    /**
     * Creates new form LeaveRequest
     */
    public LeaveDetails(int employeeId) {
            
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
        employeeSelected = employeeId;
           //Properties properties = new Properties();
   
       
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
        try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,properties);

      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * FROM Leaves l join Employee e on l.employeeId = e.employeeId where l.leaveId = "+ employeeSelected ;
      //employeeSelected = item.getId();
      ResultSet rs = stmt.executeQuery(sql);
      Vector empList = new Vector();
      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         int id  = rs.getInt("employeeId");
         
         String first = rs.getString("firstname");
         String last = rs.getString("lastname");
         String mid = rs.getString("middlename");
         int slBalance = rs.getInt("sickleavebalance");
         int vlBalance = rs.getInt("vacationleavebalance");
         int birthBalance = rs.getInt("birthleavebalance");
         String department = rs.getString("department");
         String position = rs.getString("position");
          int leaveType = rs.getInt("leaveType");
         
         java.sql.Date leaveStart = rs.getDate("startDate");
         java.sql.Date leaveEnd = rs.getDate("endDate");
         float noOfDays = rs.getFloat("noOfDays");
         int leaveStatus = rs.getInt("leaveStatus");
         int leaveId = rs.getInt("leaveId");
         String reason = rs.getString("reason");
         String status = "";
          long dateStart = leaveStart.getTime();  
          DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
          String startDate = dateFormat.format(dateStart);  
           long dateEnd = leaveEnd.getTime();    
          String endDate = dateFormat.format(dateEnd); 
          leaveTypeId = leaveType;
          empLeaveId = leaveId;
          empId = id;
          numberOfDays = noOfDays;
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
         //Display values
         lblEmployee.setText(first+" "+mid+" "+last);
         lblDepartment.setText(department);
         lblPosition.setText(position);
         txtSlBalance.setText(""+slBalance);
         txtVlBalance.setText(""+vlBalance);
         txtBirthBalance.setText(""+birthBalance);
         lblLeaveType.setText(status);
         lblStartDate.setText(startDate);
         lblEndDate.setText(endDate);
         textReason.setText(reason);
         
         switch(leaveStatus){
             case 1:
                 lblLeaveStatus.setText("Pending");
             break;
             case 2:
                 lblLeaveStatus.setText("Approved");
             break;
             
             case 3:
                 lblLeaveStatus.setText("Declined");
             break;
         }
         
         if(leaveStatus>1){
           btnAccept.setEnabled(false);
           btnDecline.setEnabled(false);
         }
      }
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
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
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
        
    }
    
    public LeaveDetails(int employeeId,boolean isUser) {
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
        employeeSelected = employeeId;
        try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,properties);

      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * FROM Leaves l join Employee e on l.employeeId = e.employeeId where l.leaveId = "+ employeeSelected ;
      //employeeSelected = item.getId();
      ResultSet rs = stmt.executeQuery(sql);
      Vector empList = new Vector();
      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         int id  = rs.getInt("employeeId");
         
         String first = rs.getString("firstname");
         String last = rs.getString("lastname");
         String mid = rs.getString("middlename");
         int slBalance = rs.getInt("sickleavebalance");
         int vlBalance = rs.getInt("vacationleavebalance");
         int birthBalance = rs.getInt("birthleavebalance");
         String department = rs.getString("department");
         String position = rs.getString("position");
          int leaveType = rs.getInt("leaveType");
         
         java.sql.Date leaveStart = rs.getDate("startDate");
         java.sql.Date leaveEnd = rs.getDate("endDate");
         float noOfDays = rs.getFloat("noOfDays");
         int leaveStatus = rs.getInt("leaveStatus");
         int leaveId = rs.getInt("leaveId");
         String reason = rs.getString("reason");
         String status = "";
          long dateStart = leaveStart.getTime();  
          DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
          String startDate = dateFormat.format(dateStart);  
           long dateEnd = leaveEnd.getTime();    
          String endDate = dateFormat.format(dateEnd); 
          leaveTypeId = leaveType;
          empLeaveId = leaveId;
          empId = id;
          numberOfDays = noOfDays;
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
         //Display values
         lblEmployee.setText(first+" "+mid+" "+last);
         lblDepartment.setText(department);
         lblPosition.setText(position);
         txtSlBalance.setText(""+slBalance);
         txtVlBalance.setText(""+vlBalance);
         txtBirthBalance.setText(""+birthBalance);
         lblLeaveType.setText(status);
         lblStartDate.setText(startDate);
         lblEndDate.setText(endDate);
         textReason.setText(reason);
         
         switch(leaveStatus){
             case 1:
                 lblLeaveStatus.setText("Pending");
             break;
             case 2:
                 lblLeaveStatus.setText("Approved");
             break;
             
             case 3:
                 lblLeaveStatus.setText("Declined");
             break;
         }
         
         if(leaveStatus>1||isUser){
           btnAccept.setEnabled(false);
           btnDecline.setEnabled(false);
         }
      }
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
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
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
        
    }
    
    @Override
     public void actionPerformed(ActionEvent e) {
    JComboBox comboBox = (JComboBox) e.getSource();
    EmployeeItem item = (EmployeeItem) comboBox.getSelectedItem();
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
       try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,properties);

      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT *  FROM Employee where employeeId = "+item.getId();
      employeeSelected = item.getId();
      ResultSet rs = stmt.executeQuery(sql);
      Vector empList = new Vector();
      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         int id  = rs.getInt("employeeId");
         
         String first = rs.getString("firstname");
         String last = rs.getString("lastname");
         String mid = rs.getString("middlename");
         int slBalance = rs.getInt("sickleavebalance");
         int vlBalance = rs.getInt("vacationleavebalance");
         int birthBalance = rs.getInt("birthleavebalance");
         String department = rs.getString("department");
         String position = rs.getString("position");
         
         
         //Display values
         lblDepartment.setText(department);
         lblPosition.setText(position);
         txtSlBalance.setText(""+slBalance);
         txtVlBalance.setText(""+vlBalance);
         txtBirthBalance.setText(""+birthBalance);
      }
      
    
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception f){
      //Handle errors for Class.forName
      f.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
       
    System.out.println(item.getId() + " : " + item.getDescription());
  }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textReason = new javax.swing.JTextArea();
        lblPosition = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblDepartment = new javax.swing.JLabel();
        lblEmployee = new javax.swing.JLabel();
        lblStartDate = new javax.swing.JLabel();
        lblEndDate = new javax.swing.JLabel();
        lblLeaveType = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSlBalance = new javax.swing.JLabel();
        txtVlBalance = new javax.swing.JLabel();
        txtBirthBalance = new javax.swing.JLabel();
        btnAccept = new javax.swing.JButton();
        btnDecline = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        lblLeaveStatus = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();

        setLocation(new java.awt.Point(350, 50));

        jLabel2.setText("Department");

        jLabel3.setText("Position");

        jLabel6.setText("Reason");

        jLabel5.setText("Leave End Date");

        jLabel4.setText("Leave Start Date");

        jLabel1.setText("Employee");

        textReason.setEditable(false);
        textReason.setColumns(10);
        textReason.setRows(5);
        textReason.setTabSize(5);
        jScrollPane1.setViewportView(textReason);

        lblPosition.setText("None");

        jLabel10.setText("Leave Type");

        lblDepartment.setText("None");

        lblEmployee.setText("jLabel8");

        lblStartDate.setText("jLabel8");

        lblEndDate.setText("jLabel8");

        lblLeaveType.setText("jLabel8");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel10)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                                .addComponent(lblEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblLeaveType, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPosition)
                            .addComponent(lblDepartment))))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblEmployee))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblDepartment))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblPosition))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblLeaveType))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblStartDate))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblEndDate))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jLabel7.setText("Approver");

        jLabel11.setText("Sick Leave Balance");

        jLabel12.setText("Vacation Leave Balance");

        jLabel13.setText("Maternity/Paternity Leave");

        jLabel15.setText("Administrator");

        txtSlBalance.setText("0");

        txtVlBalance.setText("0");

        txtBirthBalance.setText("0");

        btnAccept.setText("Approve");
        btnAccept.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });

        btnDecline.setText("Decline");
        btnDecline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeclineActionPerformed(evt);
            }
        });

        jLabel14.setText("Status");

        lblLeaveStatus.setText("None");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtBirthBalance)
                                    .addComponent(txtVlBalance)
                                    .addComponent(txtSlBalance)
                                    .addComponent(jLabel15)
                                    .addComponent(lblLeaveStatus))
                                .addGap(0, 69, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAccept)
                                .addGap(15, 15, 15)
                                .addComponent(btnDecline)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblLeaveStatus, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtSlBalance)
                        .addGap(18, 18, 18)
                        .addComponent(txtVlBalance)
                        .addGap(18, 18, 18)
                        .addComponent(txtBirthBalance))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAccept)
                    .addComponent(btnDecline))
                .addGap(66, 66, 66))
        );

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel20.setText("Leave Details");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel20))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        // TODO add your handling code here:
        String sql = "";
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to Approve Leave Request?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
  // Saving code here
  
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
   try{
              Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,properties);
      stmt = conn.createStatement();
      sql = "update leaves set leaveStatus = 2 where leaveId = "+employeeSelected; 
      
      stmt.executeUpdate(sql);
      
       stmt = conn.createStatement();
        switch(leaveTypeId){
             case 1:
               sql = "update employee set sickleavebalance = sickleavebalance - "+numberOfDays+" where employeeId = "+empId; 
             break;
             case 2:
                sql = "update employee set vacationleavebalance = vacationleavebalance - "+numberOfDays+" where employeeId = "+empId;
             break;
             case 3:
                sql = "update employee set birthleavebalance = birthleavebalance - "+numberOfDays+" where employeeId = "+empId;
             break;
             case 4:
                 sql = "update employee set birthleavebalance = birthleavebalance - "+numberOfDays+" where employeeId = "+empId;
             break;
         }
          stmt.executeUpdate(sql);
      //STEP 5: Extract data from result set
    
      
          //rs.close();
      stmt.close();
      conn.close();
       //JOptionPane.showMessageDialog(null,"Successfully Created Employee", "InfoBox: Employee", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null,"Successfully Approved Leave Request", "InfoBox: Leave", JOptionPane.INFORMATION_MESSAGE);
      this.setVisible(false);
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
        }
    }//GEN-LAST:event_btnAcceptActionPerformed

    private void btnDeclineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeclineActionPerformed
        // TODO add your handling code here:
        
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
        
         int dialogButton = JOptionPane.YES_NO_OPTION;
         String sql = "";
        int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to Decline Leave Request?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
  // Saving code here
  try{
              Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,properties);
      stmt = conn.createStatement();
      sql = "update leaves set leaveStatus = 3 where leaveId = "+employeeSelected; 
      
      stmt.executeUpdate(sql);
      
      
      //STEP 5: Extract data from result set
    
      
          //rs.close();
      stmt.close();
      conn.close();
       //JOptionPane.showMessageDialog(null,"Successfully Created Employee", "InfoBox: Employee", JOptionPane.INFORMATION_MESSAGE);
       JOptionPane.showMessageDialog(null,"Successfully Declined Leave Request", "InfoBox: Leave", JOptionPane.INFORMATION_MESSAGE);
       this.setVisible(false);
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
        }
    }//GEN-LAST:event_btnDeclineActionPerformed

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
            java.util.logging.Logger.getLogger(LeaveRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LeaveRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LeaveRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LeaveRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LeaveRequest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnDecline;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblEmployee;
    private javax.swing.JLabel lblEndDate;
    private javax.swing.JLabel lblLeaveStatus;
    private javax.swing.JLabel lblLeaveType;
    private javax.swing.JLabel lblPosition;
    private javax.swing.JLabel lblStartDate;
    private javax.swing.JTextArea textReason;
    private javax.swing.JLabel txtBirthBalance;
    private javax.swing.JLabel txtSlBalance;
    private javax.swing.JLabel txtVlBalance;
    // End of variables declaration//GEN-END:variables
}
