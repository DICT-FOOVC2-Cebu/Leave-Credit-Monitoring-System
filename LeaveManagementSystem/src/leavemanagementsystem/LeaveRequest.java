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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author DBBLPwildcatsECE18
 */
public class LeaveRequest extends javax.swing.JFrame implements ActionListener {
   
 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
  String DB_URL = "jdbc:mysql://localhost/dbict";

   //  Database credentials
   String USER = "root";
   String PASS = "root";
   Connection conn = null;
   Statement stmt = null;
   PreparedStatement preparedStmt = null;
   int employeeSelected = 1;
    /**
     * Creates new form LeaveRequest
     */
    public LeaveRequest() {
             
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
      sql = "SELECT *  FROM Employee";
      ResultSet rs = stmt.executeQuery(sql);
      Vector empList = new Vector();
      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         int id  = rs.getInt("employeeId");
         
         String first = rs.getString("firstname");
         String last = rs.getString("lastname");
         String mid = rs.getString("middlename");
         //Display values
         EmployeeItem empItem = new EmployeeItem(id,first+" "+mid.charAt(0)+". "+last);
         empList.add(empItem);
      }
      
      dropdownEmployee.setModel(new javax.swing.DefaultComboBoxModel<>(empList));
      dropdownEmployee.setRenderer(new EmployeeItemRenderer());
      dropdownEmployee.addActionListener(this);
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
    
     public LeaveRequest(int employeeId) {
         
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
      sql = "SELECT * FROM  Employee where employeeId  = "+ employeeSelected ;
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
       
         String status = "";
          
          employeeSelected = id;
      
         //Display values
         //lblEmployee.setText(first+" "+mid+" "+last);
          lblDepartment.setText(department);
         lblPosition.setText(position);
         txtSlBalance.setText(""+slBalance);
         txtVlBalance.setText(""+vlBalance);
         txtBirthBalance.setText(""+birthBalance);
         //blLeaveType.setText(status);
         //lblStartDate.setText(startDate);
         //lblEndDate.setText(endDate);
        /*textReason.setText(reason);
         
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
         }*/
        EmployeeItem empItem = new EmployeeItem(id,first+" "+mid.charAt(0)+". "+last);
         empList.add(empItem);
      }
      
      dropdownEmployee.setModel(new javax.swing.DefaultComboBoxModel<>(empList));
      dropdownEmployee.setRenderer(new EmployeeItemRenderer());
      dropdownEmployee.addActionListener(this);
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
    JComboBox comboBox = (JComboBox) e.getSource();
    EmployeeItem item = (EmployeeItem) comboBox.getSelectedItem();
    
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
        selectStartYear = new javax.swing.JComboBox<>();
        selectStartDay = new javax.swing.JComboBox<>();
        selectStartMonth = new javax.swing.JComboBox<>();
        selectEndYear = new javax.swing.JComboBox<>();
        selectEndDay = new javax.swing.JComboBox<>();
        selectEndMonth = new javax.swing.JComboBox<>();
        dropdownEmployee = new javax.swing.JComboBox<>();
        selectLeaveType = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSlBalance = new javax.swing.JLabel();
        txtVlBalance = new javax.swing.JLabel();
        txtBirthBalance = new javax.swing.JLabel();
        btnSaveLeave = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();

        setLocation(new java.awt.Point(350, 50));

        jLabel2.setText("Department");

        jLabel3.setText("Position");

        jLabel6.setText("Reason");

        jLabel5.setText("Leave End Date");

        jLabel4.setText("Leave Start Date");

        jLabel1.setText("Employee");

        textReason.setColumns(20);
        textReason.setRows(5);
        textReason.setTabSize(5);
        jScrollPane1.setViewportView(textReason);

        lblPosition.setText("None");

        jLabel10.setText("Leave Type");

        lblDepartment.setText("None");

        selectStartYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2018", "2019", "2020" }));

        selectStartDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        selectStartMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));

        selectEndYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2018", "2019", "2020" }));

        selectEndDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        selectEndMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));

        selectLeaveType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sick Leave", "Vacation Leave", "Leave Without Pay", "Maternity Leave", "Paternity Leave" }));

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(selectStartMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectStartDay, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(selectEndMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectEndDay, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectEndYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(selectStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(selectLeaveType, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dropdownEmployee, javax.swing.GroupLayout.Alignment.LEADING, 0, 169, Short.MAX_VALUE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)))
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
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(dropdownEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(selectLeaveType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(selectStartMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectStartDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(selectEndMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectEndDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectEndYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jLabel7.setText("Approver");

        jLabel11.setText("Sick Leave Balance");

        jLabel12.setText("Vacation Leave Balance");

        jLabel13.setText("Maternity Leave");

        jLabel15.setText("None");

        txtSlBalance.setText("0");

        txtVlBalance.setText("0");

        txtBirthBalance.setText("0");

        btnSaveLeave.setText("Save");
        btnSaveLeave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveLeaveActionPerformed(evt);
            }
        });

        jButton2.setText("Clear");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
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
                            .addComponent(jLabel15))
                        .addContainerGap(79, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSaveLeave)
                        .addGap(15, 15, 15)
                        .addComponent(jButton2)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(txtSlBalance)
                        .addGap(18, 18, 18)
                        .addComponent(txtVlBalance)
                        .addGap(18, 18, 18)
                        .addComponent(txtBirthBalance))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveLeave)
                    .addComponent(jButton2))
                .addGap(96, 96, 96))
        );

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel20.setText("Create Leave");

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
                .addContainerGap(82, Short.MAX_VALUE))
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
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveLeaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveLeaveActionPerformed
        // TODO add your handling code here:
           
           String startMonth = "";
           String startDay = "";
           String startYear = "";
           String endMonth = "";
           String endDay = "";
           String endYear = "";
           String reason = "";
           String leaveType = "";
           
           startMonth = ""+(selectStartMonth.getSelectedIndex() + 1);
           startDay = selectStartDay.getSelectedItem().toString();
           startYear = selectStartYear.getSelectedItem().toString();
           
            endMonth = ""+(selectEndMonth.getSelectedIndex() + 1);
           endDay = selectEndDay.getSelectedItem().toString();
           endYear = selectEndYear.getSelectedItem().toString();
           
           reason = textReason.getText();
           
           leaveType = ""+(selectLeaveType.getSelectedIndex() + 1);
           String startDateString = "06/27/2007";
           String endDateString = "06/27/2007";
           
           if(Integer.parseInt(startMonth)<10){
               startMonth = "0"+startMonth;
           }
           
           if(Integer.parseInt(startDay)<10){
               startDay = "0"+startDay;
           }
           
           if(Integer.parseInt(endMonth)<10){
              endMonth = "0"+endMonth;
           }
           
           if(Integer.parseInt(endDay)<10){
              endDay = "0"+endDay;
           }
           startDateString = startMonth+"/"+startDay+"/"+startYear;
           endDateString = endMonth+"/"+endDay+"/"+endYear;
           DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
           Date startDate = new Date();
           Date endDate = new Date();
           long diff = 0;
           float days = 0;
           try{
            startDate = df.parse(startDateString);
            endDate = df.parse(endDateString);
             diff = endDate.getTime() - startDate.getTime();
            days = (diff / (1000*60*60*24));
           }
           catch(Exception ex){
               System.out.println(ex);
           }
           
            String sql="";
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
      
      sql = "INSERT INTO Leaves(employeeId,startDate,endDate,reason,createdDate,noOfDays,leaveStatus,leaveType,recordStatus)"
              + "values (?,?,?,?,CURDATE(),?,1,?,1)";
      java.sql.Date d = new java.sql.Date(1,1,2018);
      
      preparedStmt = conn.prepareStatement(sql);
       //stmt.executeUpdate(sql);
       preparedStmt.setInt(1,employeeSelected);
       preparedStmt.setDate(2,new java.sql.Date(startDate.getTime()));
       preparedStmt.setDate(3,new java.sql.Date(endDate.getTime()));
         preparedStmt.setString(4,reason);
         preparedStmt.setFloat(5, days);
         preparedStmt.setInt(6,Integer.parseInt(leaveType));
         preparedStmt.executeUpdate();
         //rs.close();
      //stmt.close();
      preparedStmt.close();
      conn.close();
         JOptionPane.showMessageDialog(null,"Successfully Requested Leave", "Leave", JOptionPane.INFORMATION_MESSAGE);
       
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
           
           
         
           
    }//GEN-LAST:event_btnSaveLeaveActionPerformed

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
    private javax.swing.JButton btnSaveLeave;
    private javax.swing.JComboBox<String> dropdownEmployee;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JLabel lblPosition;
    private javax.swing.JComboBox<String> selectEndDay;
    private javax.swing.JComboBox<String> selectEndMonth;
    private javax.swing.JComboBox<String> selectEndYear;
    private javax.swing.JComboBox<String> selectLeaveType;
    private javax.swing.JComboBox<String> selectStartDay;
    private javax.swing.JComboBox<String> selectStartMonth;
    private javax.swing.JComboBox<String> selectStartYear;
    private javax.swing.JTextArea textReason;
    private javax.swing.JLabel txtBirthBalance;
    private javax.swing.JLabel txtSlBalance;
    private javax.swing.JLabel txtVlBalance;
    // End of variables declaration//GEN-END:variables
}
