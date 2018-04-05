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
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author DBBLPwildcatsECE18
 */
public class CreateAttendance extends javax.swing.JFrame implements ActionListener {
   
 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
  String DB_URL = "jdbc:mysql://localhost/dbict";

   //  Database credentials
   String USER = "root";
   String PASS = "root";
   Connection conn = null;
   Statement stmt = null;
   PreparedStatement preparedStmt = null;
   int employeeSelected = 1;
   double sickLeave = 0;
   double vacationLeave = 0;
   double birthLeave = 0;
    /**
     * Creates new form LeaveRequest
     */
    public CreateAttendance() {
             
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
    
     public CreateAttendance(int employeeId) {
        initComponents();
        employeeSelected = employeeId;
        try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

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
    JComboBox comboBox = (JComboBox) e.getSource();
    EmployeeItem item = (EmployeeItem) comboBox.getSelectedItem();
    
       try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

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
         double slBalance = rs.getDouble("sickleavebalance");
         double vlBalance = rs.getDouble("vacationleavebalance");
         double birthBalance = rs.getDouble("birthleavebalance");
         String department = rs.getString("department");
         String position = rs.getString("position");
         //Display values
         
         sickLeave = slBalance;
         vacationLeave = vlBalance;
         birthLeave = birthBalance;
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
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblPosition = new javax.swing.JLabel();
        lblDepartment = new javax.swing.JLabel();
        selectStartYear = new javax.swing.JComboBox<>();
        selectStartDay = new javax.swing.JComboBox<>();
        selectStartMonth = new javax.swing.JComboBox<>();
        dropdownEmployee = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        selectInHour = new javax.swing.JComboBox<>();
        selectInMinute = new javax.swing.JComboBox<>();
        selectInDay = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        selectOutHour = new javax.swing.JComboBox<>();
        selectOutMinute = new javax.swing.JComboBox<>();
        selectOutDay = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtHoursRequired = new javax.swing.JTextField();
        btnCompute = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        selectInHour1 = new javax.swing.JComboBox<>();
        selectInMinute1 = new javax.swing.JComboBox<>();
        selectInDay1 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        selectOutHour1 = new javax.swing.JComboBox<>();
        selectOutMinute1 = new javax.swing.JComboBox<>();
        selectOutDay1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtSlBalance = new javax.swing.JLabel();
        txtVlBalance = new javax.swing.JLabel();
        txtBirthBalance = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txtOtHours = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtHoursWork = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtUtHours = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtLateHours = new javax.swing.JLabel();
        btnSaveAttendance = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();

        setLocation(new java.awt.Point(350, 50));

        jLabel2.setText("Department");

        jLabel3.setText("Position");

        jLabel4.setText("Attendance Date");

        jLabel1.setText("Employee");

        lblPosition.setText("None");

        lblDepartment.setText("None");

        selectStartYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2018", "2019", "2020" }));

        selectStartDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        selectStartMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));

        jLabel8.setText("Schedule In");

        selectInHour.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        selectInMinute.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));

        selectInDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));

        jLabel9.setText("Schedule Out");

        selectOutHour.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        selectOutMinute.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));

        selectOutDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));

        jLabel5.setText("Required Hours");

        txtHoursRequired.setText("0");

        btnCompute.setText("Compute");
        btnCompute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComputeActionPerformed(evt);
            }
        });

        jLabel10.setText("Time In");

        selectInHour1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        selectInMinute1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));

        selectInDay1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));

        jLabel15.setText("Time Out");

        selectOutHour1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        selectOutMinute1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));

        selectOutDay1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPosition)
                            .addComponent(lblDepartment)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(45, 45, 45)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dropdownEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(selectInHour, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectInMinute, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectInDay, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel9))
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(selectInHour1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(selectInMinute1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(selectInDay1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(selectOutHour1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(selectOutMinute1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(selectOutDay1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(selectOutHour, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(selectOutMinute, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(selectOutDay, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(selectStartMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectStartDay, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnCompute)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(48, 48, 48)
                                    .addComponent(txtHoursRequired, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(54, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtHoursRequired, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(selectStartMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectStartDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(selectInHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectInMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectInDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(selectOutHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectOutMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectOutDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(selectInHour1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectInMinute1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectInDay1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(selectOutHour1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectOutMinute1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectOutDay1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(btnCompute)
                .addContainerGap())
        );

        jLabel11.setText("Sick Leave Balance");
        jLabel11.setToolTipText("");

        jLabel12.setText("Vacation Leave Balance");

        jLabel13.setText("Maternity Leave");

        txtSlBalance.setText("0");

        txtVlBalance.setText("0");

        txtBirthBalance.setText("0");

        jButton2.setText("Clear");

        txtOtHours.setText("0");

        jLabel16.setText("Overtime Hours");

        txtHoursWork.setText("0");

        jLabel14.setText("Hours Work");
        jLabel14.setToolTipText("");

        jLabel17.setText("Undertime Hours");

        txtUtHours.setText("0");

        jLabel18.setText("Late Hours");

        txtLateHours.setText("0");

        btnSaveAttendance.setText("Save");
        btnSaveAttendance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAttendanceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSaveAttendance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBirthBalance)
                            .addComponent(txtVlBalance)
                            .addComponent(txtSlBalance)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16)
                            .addComponent(jLabel14)
                            .addComponent(jLabel18))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLateHours)
                            .addComponent(txtUtHours)
                            .addComponent(txtOtHours)
                            .addComponent(txtHoursWork))))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
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
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtHoursWork)
                        .addGap(18, 18, 18)
                        .addComponent(txtOtHours)
                        .addGap(18, 18, 18)
                        .addComponent(txtUtHours))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtLateHours))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(btnSaveAttendance))
                .addGap(96, 96, 96))
        );

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel20.setText("Create Attendance");

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
                .addContainerGap(57, Short.MAX_VALUE))
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
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnComputeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComputeActionPerformed
        // TODO add your handling code here:
        double reqHours = 0;
        int inHour = 0;
        int inMinute = 0;
        int inDay = 0;
        int outHour = 0;
        int outMinute = 0;
        int outDay = 0;
        double lateHours = 0;
        double underHours = 0;
        
        String timeIn = "";
        String timeOut = "";
        
        int inHourSched = 0;
        int inMinuteSched = 0;
        int inDaySched = 0;
        int outHourSched = 0;
        int outMinuteSched = 0;
        int outDaySched = 0;
        
        String timeInSched = "";
        String timeOutSched = "";
        int day = 0;
        int month = 0;
        int year = 0;
        
        
        inHour = selectInHour1.getSelectedIndex()+1;
        inMinute = selectInMinute1.getSelectedIndex();
        inDay = selectInDay1.getSelectedIndex();
        
        outHour = selectOutHour1.getSelectedIndex()+1;
        outMinute = selectOutMinute1.getSelectedIndex();
        outDay = selectOutDay1.getSelectedIndex();
        
        if(inDay==1){
          inHour = inHour + 12;
        }
        
        if(outDay==1){
            outHour = outHour + 12;
        }
        
         inHourSched = selectInHour.getSelectedIndex()+1;
        inMinuteSched = selectInMinute.getSelectedIndex();
        inDaySched = selectInDay.getSelectedIndex();
        
        outHourSched = selectOutHour.getSelectedIndex()+1;
        outMinuteSched = selectOutMinute.getSelectedIndex();
        outDaySched = selectOutDay.getSelectedIndex();
        
        if(inDaySched==1){
          inHourSched = inHourSched + 12;
        }
        
        if(outDaySched==1){
            outHourSched = outHourSched + 12;
        }
        
        timeIn = inHour+":"+inMinute;
        timeOut = outHour+":"+outMinute;
        
         timeInSched = inHourSched+":"+inMinuteSched;
        timeOutSched = outHourSched+":"+outMinuteSched;
        System.out.println("Time "+timeIn);
        System.out.println("Time "+timeOut);
        DateFormat sdfIn = new SimpleDateFormat("hh:mm");
        DateFormat sdfOut = new SimpleDateFormat("hh:mm");
        DateFormat sdfInSched = new SimpleDateFormat("hh:mm");
        DateFormat sdfOutSched = new SimpleDateFormat("hh:mm");
         Date dateIn = new Date();
        Date dateOut = new Date();
        
         Date dateInSched = new Date();
        Date dateOutSched = new Date();
        try{
        
        dateIn = sdfIn.parse(timeIn);
        dateOut = sdfOut.parse(timeOut);
        dateInSched = sdfInSched.parse(timeInSched);
        dateOutSched = sdfOutSched.parse(timeOutSched);
        }catch(Exception ex){
            
        }
        
       System.out.println("Schedule In: " + sdfInSched.format(dateInSched));
System.out.println("Schedule Out: " + sdfOutSched.format(dateOutSched));  

System.out.println("Time In: " + sdfIn.format(dateIn));
System.out.println("Time Out: " + sdfOut.format(dateOut));   

long millis =    (dateOut.getTime() - dateIn.getTime());
long hours = millis/(1000 * 60 * 60);
long mins = (millis/(1000*60)) % 60;

String diff = hours + ":" + mins; 

System.out.println("Hours Diff: "+diff);

 if(mins>0||hours>0)
        txtHoursWork.setText(diff);

long millisSched =    (dateOutSched.getTime() - dateInSched.getTime());
long hoursSched = millisSched/(1000 * 60 * 60);
long minsSched = (millisSched/(1000*60)) % 60;

String diffSched = hoursSched + ":" + minsSched; 

System.out.println("Sched Diff: "+diffSched);

long millisLate =    (dateIn.getTime() - dateInSched.getTime());
long hoursLate = millisLate/(1000 * 60 * 60);
long minsLate = (millisLate/(1000*60)) % 60;

String diffLate = hoursLate + ":" + minsLate; 

    if(minsLate>0||hoursLate>0)
        txtLateHours.setText(diffLate);
    
    long millisOT =    (dateOut.getTime() - dateOutSched.getTime() );
long hoursOT = millisOT/(1000 * 60 * 60);
long minsOT = (millisOT/(1000*60)) % 60;

String diffOT = hoursOT + ":" + minsOT; 

    if(hoursOT>0||minsOT>0)
        txtOtHours.setText(diffOT);
    
       long millisUT =    (dateOutSched.getTime() - dateOut.getTime());
long hoursUT = millisUT/(1000 * 60 * 60);
long minsUT = (millisOT/(1000*60)) % 60;

String diffUT = hoursUT + ":" + minsUT; 
System.out.println("Late: "+diffLate);
System.out.println("Undertime: "+diffUT);
System.out.println("Overtime: "+diffOT);
    if(hoursUT>0||minsUT>0)
        txtUtHours.setText(diffUT);
                
    }//GEN-LAST:event_btnComputeActionPerformed

    private void btnSaveAttendanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAttendanceActionPerformed
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
         double reqHours = 0;
        int inHour = 0;
        int inMinute = 0;
        int inDay = 0;
        int outHour = 0;
        int outMinute = 0;
        int outDay = 0;
        double lateHours = 0;
        double underHours = 0;
        
        String timeIn = "";
        String timeOut = "";
        
        int inHourSched = 0;
        int inMinuteSched = 0;
        int inDaySched = 0;
        int outHourSched = 0;
        int outMinuteSched = 0;
        int outDaySched = 0;
        
        String timeInSched = "";
        String timeOutSched = "";
        int day = 0;
        int month = 0;
        int year = 0;
        
        
        inHour = selectInHour1.getSelectedIndex()+1;
        inMinute = selectInMinute1.getSelectedIndex();
        inDay = selectInDay1.getSelectedIndex();
        
        outHour = selectOutHour1.getSelectedIndex()+1;
        outMinute = selectOutMinute1.getSelectedIndex();
        outDay = selectOutDay1.getSelectedIndex();
        
        if(inDay==1){
          inHour = inHour + 12;
        }
        
        if(outDay==1){
            outHour = outHour + 12;
        }
        
         inHourSched = selectInHour.getSelectedIndex()+1;
        inMinuteSched = selectInMinute.getSelectedIndex();
        inDaySched = selectInDay.getSelectedIndex();
        
        outHourSched = selectOutHour.getSelectedIndex()+1;
        outMinuteSched = selectOutMinute.getSelectedIndex();
        outDaySched = selectOutDay.getSelectedIndex();
        
        if(inDaySched==1){
          inHourSched = inHourSched + 12;
        }
        
        if(outDaySched==1){
            outHourSched = outHourSched + 12;
        }
        
        timeIn = inHour+":"+inMinute;
        timeOut = outHour+":"+outMinute;
        
         timeInSched = inHourSched+":"+inMinuteSched;
        timeOutSched = outHourSched+":"+outMinuteSched;
        System.out.println("Time "+timeIn);
        System.out.println("Time "+timeOut);
        DateFormat sdfIn = new SimpleDateFormat("hh:mm");
        DateFormat sdfOut = new SimpleDateFormat("hh:mm");
        DateFormat sdfInSched = new SimpleDateFormat("hh:mm");
        DateFormat sdfOutSched = new SimpleDateFormat("hh:mm");
         Date dateIn = new Date();
        Date dateOut = new Date();
        
         Date dateInSched = new Date();
        Date dateOutSched = new Date();
        try{
        
        dateIn = sdfIn.parse(timeIn);
        dateOut = sdfOut.parse(timeOut);
        dateInSched = sdfInSched.parse(timeInSched);
        dateOutSched = sdfOutSched.parse(timeOutSched);
        }catch(Exception ex){
            
        }
        
       System.out.println("Schedule In: " + sdfInSched.format(dateInSched));
System.out.println("Schedule Out: " + sdfOutSched.format(dateOutSched));  

System.out.println("Time In: " + sdfIn.format(dateIn));
System.out.println("Time Out: " + sdfOut.format(dateOut));   

long millis =    (dateOut.getTime() - dateIn.getTime());
long hours = millis/(1000 * 60 * 60);
long mins = (millis/(1000*60)) % 60;

String diff = hours + ":" + mins; 

double hourWorked = Double.parseDouble(""+hours);
int minWorked = Integer.parseInt(""+mins);

switch(minWorked){
    case 1: hourWorked = hourWorked + 0.02; break;
case 2:	hourWorked = hourWorked + 0.03; break;
case 3:	hourWorked = hourWorked + 0.05; break;
case 4:	hourWorked = hourWorked + 0.07; break;
case 5:	hourWorked = hourWorked + 0.08; break;
case 6:	hourWorked = hourWorked + 0.10; break;
case 7:	hourWorked = hourWorked + 0.12; break;
case 8:	hourWorked = hourWorked + 0.13; break;
case 9:	hourWorked = hourWorked + 0.15; break;
case 10: hourWorked = hourWorked +  0.17; break;
case 11: hourWorked = hourWorked + 0.18; break;
case 12: hourWorked = hourWorked + 0.20; break;
case 13: hourWorked = hourWorked + 0.22; break;
case 14: hourWorked = hourWorked + 0.23; break;
case 15: hourWorked = hourWorked + 0.25; break;
case 16: hourWorked = hourWorked + 0.27; break;
case 17: hourWorked = hourWorked + 0.28; break;
case 18: hourWorked = hourWorked + 0.30; break;
case 19: hourWorked = hourWorked + 0.32; break;
case 20: hourWorked = hourWorked + 0.33; break;
case 21: hourWorked = hourWorked + 0.35; break;
case 22: hourWorked = hourWorked + 	0.37; break;
case 23: hourWorked = hourWorked + 	0.38; break;
case 24: hourWorked = hourWorked + 	0.40; break;
case 25: hourWorked = hourWorked + 	0.42; break;
case 26: hourWorked = hourWorked + 	0.43; break;
case 27: hourWorked = hourWorked + 	0.45; break;
case 28: hourWorked = hourWorked + 	0.47; break;
case 29: hourWorked = hourWorked + 	0.48; break;
case 30: hourWorked = hourWorked + 	0.50; break;
case 31: hourWorked = hourWorked + 	0.52; break;
case 32: hourWorked = hourWorked + 	0.53; break;
case 33: hourWorked = hourWorked + 	0.55; break;
case 34: hourWorked = hourWorked + 	0.57; break;
case 35: hourWorked = hourWorked + 	0.58; break;
case 36: hourWorked = hourWorked + 	0.60; break;
case 37: hourWorked = hourWorked + 	0.62; break;
case 38: hourWorked = hourWorked + 	0.63; break;
case 39: hourWorked = hourWorked + 	0.65; break;
case 40: hourWorked = hourWorked + 	0.67; break;
case 41: hourWorked = hourWorked + 	0.68; break;
case 42: hourWorked = hourWorked + 	0.70; break;
case 43: hourWorked = hourWorked + 	0.72; break;
case 44: hourWorked = hourWorked + 	0.73; break;
case 45: hourWorked = hourWorked + 	0.75; break;
case 46: hourWorked = hourWorked + 	0.77; break;
case 47: hourWorked = hourWorked + 	0.78; break;
case 48: hourWorked = hourWorked + 	0.80; break;
case 49: hourWorked = hourWorked + 	0.82; break;
case 50: hourWorked = hourWorked + 	0.83; break;
case 51: hourWorked = hourWorked + 	0.85; break;
case 52: hourWorked = hourWorked + 	0.87; break;
case 53: hourWorked = hourWorked + 	0.88; break;
case 54: hourWorked = hourWorked + 	0.90; break;
case 55: hourWorked = hourWorked + 	0.92; break;
case 56: hourWorked = hourWorked + 	0.93; break;
case 57: hourWorked = hourWorked + 	0.95; break;
case 58: hourWorked = hourWorked + 	0.97; break;
case 59: hourWorked = hourWorked + 	0.98; break;
}

System.out.println("Hours Diff: "+diff);

long millisSched =    (dateOutSched.getTime() - dateInSched.getTime());
long hoursSched = millisSched/(1000 * 60 * 60);
long minsSched = (millisSched/(1000*60)) % 60;

String diffSched = hoursSched + ":" + minsSched; 

System.out.println("Sched Diff: "+diffSched);

long millisLate =    (dateIn.getTime() - dateInSched.getTime());
long hoursLate = millisLate/(1000 * 60 * 60);
long minsLate = (millisLate/(1000*60)) % 60;

String diffLate = hoursLate + ":" + minsLate; 

    //if(minsLate>0||hoursLate>0)
        //txtLateHours.setText(diffLate);
    
    long millisOT =    (dateOut.getTime() - dateOutSched.getTime() );
long hoursOT = millisOT/(1000 * 60 * 60);
long minsOT = (millisOT/(1000*60)) % 60;

String diffOT = hoursOT + ":" + minsOT; 

    //if(hoursOT>0||minsOT>0)
        //txtOtHours.setText(diffOT);
    
       long millisUT =    (dateOutSched.getTime() - dateOut.getTime());
long hoursUT = millisUT/(1000 * 60 * 60);
long minsUT = (millisOT/(1000*60)) % 60;

String diffUT = hoursUT + ":" + minsUT; 
System.out.println("Late: "+diffLate);
System.out.println("Undertime: "+diffUT);
System.out.println("Overtime: "+diffOT);
    //if(hoursUT>0||minsUT>0)
        //txtUtHours.setText(diffUT);
                
                 
   
   
       
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
        
        String sql = "";
        String birthMonth = ""+(selectStartMonth.getSelectedIndex() + 1);
        String birthDay = selectStartDay.getSelectedItem().toString();
        String birthYear = selectStartYear.getSelectedItem().toString();
        
        String attendanceDate = "";
       if(Integer.parseInt(birthMonth)<10){
               birthMonth = "0"+birthMonth;
           }
           
           if(Integer.parseInt(birthDay)<10){
               birthDay = "0"+birthDay;
           }
           
           if(Integer.parseInt(birthMonth)<10){
              birthMonth = "0"+birthMonth;
           }
           
           if(Integer.parseInt(birthDay)<10){
              birthDay = "0"+birthDay;
           }
           attendanceDate = birthMonth+"/"+birthDay+"/"+birthYear;
           
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
           java.util.Date efAttendanceDate = new java.util.Date();
           
           try{
           efAttendanceDate = df.parse(attendanceDate);
           }
           catch(Exception ex){
               System.out.println(ex);
               
               return;
           }
           
           String reqString = "0";
           
           if(!txtHoursRequired.getText().equals("")){
               reqString = txtHoursRequired.getText();
           }
           
          reqHours = Double.parseDouble(reqString);
           
        try{
                  SimpleDateFormat formatWork = new SimpleDateFormat("HH:mm"); // 12 hour format

    java.util.Date d1Work =(java.util.Date)formatWork.parse(diff);

    java.sql.Time efWork = new java.sql.Time(d1Work.getTime());
             SimpleDateFormat formatIn = new SimpleDateFormat("HH:mm"); // 12 hour format

    java.util.Date d1TimeIn =(java.util.Date)formatIn.parse(timeIn);

    java.sql.Time efTimeIn = new java.sql.Time(d1TimeIn.getTime());
    
    
     SimpleDateFormat formatOut = new SimpleDateFormat("HH:mm"); // 12 hour format

    java.util.Date d1TimeOut =(java.util.Date)formatOut.parse(timeOut);

    java.sql.Time efTimeOut = new java.sql.Time(d1TimeOut.getTime());
    
      SimpleDateFormat formatInSched = new SimpleDateFormat("HH:mm"); // 12 hour format

    java.util.Date d1TimeInSched =(java.util.Date)formatInSched.parse(timeInSched);

    java.sql.Time efTimeInSched = new java.sql.Time(d1TimeInSched.getTime());
    
    
     SimpleDateFormat formatOutSched = new SimpleDateFormat("HH:mm"); // 12 hour format

    java.util.Date d1TimeOutSched =(java.util.Date)formatOutSched.parse(timeOutSched);

    java.sql.Time efTimeOutSched = new java.sql.Time(d1TimeOutSched.getTime());
    
       SimpleDateFormat formatLate = new SimpleDateFormat("HH:mm"); // 12 hour format

    java.util.Date d1TimeLate =(java.util.Date)formatLate.parse(diffLate);

    java.sql.Time efTimeLate = new java.sql.Time(d1TimeLate.getTime());
            
    
       SimpleDateFormat formatOvertime = new SimpleDateFormat("HH:mm"); // 12 hour format

    java.util.Date d1TimeOvertime =(java.util.Date)formatOvertime.parse(diffOT);

    java.sql.Time efTimeOvertime = new java.sql.Time(d1TimeOvertime.getTime());
            
    
       SimpleDateFormat formatUndertime = new SimpleDateFormat("HH:mm"); // 12 hour format

    java.util.Date d1TimeUndertime =(java.util.Date)formatUndertime.parse(diffUT);

    java.sql.Time efTimeUndertime = new java.sql.Time(d1TimeUndertime.getTime());
            
            
          Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,properties);
      
      sql = "INSERT INTO Attendance(employeeId,attendanceDate,scheduleIn,scheduleOut,timeIn,timeOut,otHours,utHours,lateHours,requiredHours,hoursWorked,status)"
      +"VALUES (?,?,?,?,?,?,?,?,?,?,?,1)";
      java.sql.Date d = new java.sql.Date(1,1,2018);
      
      preparedStmt = conn.prepareStatement(sql);
       //stmt.executeUpdate(sql);
       
       preparedStmt.setInt(1,employeeSelected);
        preparedStmt.setDate(2,new java.sql.Date(efAttendanceDate.getTime()));
         preparedStmt.setTime(3,efTimeIn);
         preparedStmt.setTime(4,efTimeOut);
         preparedStmt.setTime(5,efTimeInSched);
         preparedStmt.setTime(6,efTimeOutSched);
         preparedStmt.setTime(7,efTimeOvertime);
         preparedStmt.setTime(8,efTimeUndertime);
         preparedStmt.setTime(9,efTimeLate);
         preparedStmt.setDouble(10,reqHours);
         preparedStmt.setDouble(11,hourWorked);
         preparedStmt.executeUpdate();
         
         double totLate = 0;
         int hoursLateConvert = Integer.parseInt(""+hoursLate);
         int minLateConvert = Integer.parseInt(""+minsLate);
       
             totLate = hoursLateConvert * .125;
             totLate = totLate + (minLateConvert * .002);
         Random rand = new Random();
         int randomNum = rand.nextInt((100 - 1) + 1) + 1;
         
       
          
        if(totLate>0){
        stmt = conn.createStatement();
          if(randomNum%2==0){
          
         if(sickLeave>0){
         sql = "update employee set sickleavebalance = sickleavebalance - "+totLate+" where employeeId = "+employeeSelected;
         }
         else if(vacationLeave >0){
         sql = "update employee set vacationleavebalance = vacationleavebalance - "+totLate+" where employeeId = "+employeeSelected;
         }
         else if(birthLeave>0){
         sql = "update employee set birthleavebalance =birthleavebalance - "+totLate+" where employeeId = "+employeeSelected;
         }
         }
         else{
           if(vacationLeave >0){
         sql = "update employee set vacationleavebalance = vacationleavebalance - "+totLate+" where employeeId = "+employeeSelected;
         }
         else if(sickLeave>0){
         sql = "update employee set sickleavebalance = sickleavebalance - "+totLate+" where employeeId = "+employeeSelected;
         }
         else if(birthLeave>0){
         sql = "update employee set birthleavebalance =birthleavebalance - "+totLate+" where employeeId = "+employeeSelected;
         }
         }
         stmt.executeUpdate(sql);
        }
      
      
         //rs.close();
      stmt.close();
      preparedStmt.close();
      conn.close();
      
      JOptionPane.showMessageDialog(null,"Successfully Created Attendance", "InfoBox: Attendance", JOptionPane.INFORMATION_MESSAGE);
      
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
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
        
        
    }//GEN-LAST:event_btnSaveAttendanceActionPerformed

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
            java.util.logging.Logger.getLogger(CreateAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateAttendance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompute;
    private javax.swing.JButton btnSaveAttendance;
    private javax.swing.JComboBox<String> dropdownEmployee;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblPosition;
    private javax.swing.JComboBox<String> selectInDay;
    private javax.swing.JComboBox<String> selectInDay1;
    private javax.swing.JComboBox<String> selectInHour;
    private javax.swing.JComboBox<String> selectInHour1;
    private javax.swing.JComboBox<String> selectInMinute;
    private javax.swing.JComboBox<String> selectInMinute1;
    private javax.swing.JComboBox<String> selectOutDay;
    private javax.swing.JComboBox<String> selectOutDay1;
    private javax.swing.JComboBox<String> selectOutHour;
    private javax.swing.JComboBox<String> selectOutHour1;
    private javax.swing.JComboBox<String> selectOutMinute;
    private javax.swing.JComboBox<String> selectOutMinute1;
    private javax.swing.JComboBox<String> selectStartDay;
    private javax.swing.JComboBox<String> selectStartMonth;
    private javax.swing.JComboBox<String> selectStartYear;
    private javax.swing.JLabel txtBirthBalance;
    private javax.swing.JTextField txtHoursRequired;
    private javax.swing.JLabel txtHoursWork;
    private javax.swing.JLabel txtLateHours;
    private javax.swing.JLabel txtOtHours;
    private javax.swing.JLabel txtSlBalance;
    private javax.swing.JLabel txtUtHours;
    private javax.swing.JLabel txtVlBalance;
    // End of variables declaration//GEN-END:variables
}
