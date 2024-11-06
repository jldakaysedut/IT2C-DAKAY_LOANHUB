
package loanhub;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Application {
    
    public void  mApplication(){
        
         Scanner sc = new Scanner(System.in);
    String response;
    do{   
    System.out.println("\n-------------------------");   
    System.out.println("APPLICATION PANEL");
    System.out.println("1. Add Application");
    System.out.println("2. View Application ");
    System.out.println("3. Update Application ");
    System.out.println("4. Delete Application ");
    System.out.println("5. Exit ");
    
    System.out.print("Enter Action: ");
    int act = sc.nextInt();
    Application an = new Application();
    switch(act){
        
        case 1:  
              an.addApplication(); 
           break;
        case 2:
              an.viewApplication();
           break;
        case 3:          
              an.viewApplication();
              an.updateApplication();
            
           break;
        case 4:
             an.viewApplication();
              an.deleteApplication(); 
              an.viewApplication();
             
            break;
        case 5:
            break;
           
            
    }
    System.out.print("Do you want to continue?(yes/no): ");
    response = sc.next();
    
    }while(response.equalsIgnoreCase("yes")); 
             
    }
    
  private void addApplication(){  
   Scanner sc = new Scanner(System.in);
   config conf = new config();
   Applicant at = new Applicant();
    at.viewApplicant();
    
    
    System.out.println("Enter the ID of the Applicant: ");
    int aptid = sc.nextInt();
    
             
    String sql = "SELECT apt_id FROM tbl_applicant WHERE apt_id = ?";   
    while(conf.getSingleValue(sql, aptid) == 0){
            System.out.println("Selected ID doesn't exist, Try again: ");      
            aptid = sc.nextInt();
      
}
    
    System.out.print("Enter loan Type: ");
     String type = sc.next();
     
     System.out.print("Enter loan Amount: ");
     double amount = sc.nextDouble();  
     
     LocalDate currdate = LocalDate.now();
     DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
     String date = currdate.format(format);
    
     System.out.print("Enter due Date: ");
     String duedate = sc.next(); 
     
     String status = "Pending";
     
          String applicationsql = "INSERT INTO tbl_application (apt_id, apn_type, apn_amount, apn_date, apn_duedate, apn_status) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(applicationsql, aptid, type, amount, date, duedate, status);
  
     
  }
  
  public void viewApplication() {
       
        
     
        String aqry = "SELECT apn_id, apt_name, apn_type, apn_amount, apn_date, apn_duedate, apn_status FROM tbl_application "
                + "LEFT JOIN tbl_applicant ON tbl_applicant.apt_id = tbl_application.apt_id";

         
        String[] hrds = {"ID", "Applicant", "Loan type", "Loan amount", "Date", "Due Date", "Status"};
        String[] clmns = {"apn_id", "apt_name", "apn_type", "apn_amount", "apn_date", "apn_duedate", "apn_status"};
        config conf = new config();
        conf.viewRecords(aqry, hrds, clmns);
    }

  
  
private void updateApplication() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    
    System.out.print("Enter Application ID: ");
    int id = sc.nextInt();
    sc.nextLine();
    

    while (conf.getSingleValue("SELECT apn_id FROM tbl_application WHERE apn_id = ?", id) == 0) {
        System.out.println("Selected ID doesn't exist.");
        System.out.print("Enter Application ID again: ");
        id = sc.nextInt();
        sc.nextLine();
    }
    
    System.out.print("Enter new status (e.g., Approved, Pending, Denied): ");
    String newStatus = sc.nextLine();
    
   
    String qry = "UPDATE tbl_application SET apn_status = ? WHERE apn_id = ?";
    conf.updateRecord(qry, newStatus, id);
    
    System.out.println("Application status updated successfully.");
}


  
  
 private void deleteApplication(){

       Scanner sc = new Scanner(System.in);
       config conf = new config();
   System.out.print("Enter Application ID to delete: ");
   int id = sc.nextInt(); 
   
   while(conf.getSingleValue("Select apn_id FROM tbl_application WHERE apn_id = ?", id)==0){
            System.out.println("Selected ID doesn't exist");
            System.out.println("Selected Applicantion ID again: ");
            id = sc.nextInt();
                 sc.nextLine();
            }
   
   String sqlDelete = "DELETE FROM tbl_application WHERE apn_id = ?";
   
   conf.deleteRecord(sqlDelete, id);
    }
  

  
  }


  

