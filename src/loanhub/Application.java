
package loanhub;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Application{
    
    public void  mApplication(){
        
    Scanner sc = new Scanner(System.in);
    
    String response = "";
    do{   
    System.out.println("\nAPPLICATION PANEL");
    System.out.println("------------------------");  
    System.out.println("|1. Add Application    |");
    System.out.println("|2. View Application   |");
    System.out.println("|3. Update Application |");
    System.out.println("|4. Delete Application |");
    System.out.println("|5. Exit               |");
    System.out.println("------------------------");
    
 
    int act = -1; 
    while (act < 1 || act > 5){
        
    System.out.print("Enter Action (1-5): ");
    
    if(sc.hasNextInt()){
     act = sc.nextInt();
     
    if(act < 1 || act > 5){
    System.out.println("Invalid action. Please enter a number between 1 and 5.");
    }
    }else{
    System.out.println("Invalid input. Please enter a numeric action.");
    sc.next(); 
      }
  }

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
    
    
    boolean validResponse = false;
    while (!validResponse){
    System.out.print("\nDo you still want to continue in this Panel? (yes/no): ");
    response = sc.next().trim().toLowerCase();
    if (response.equals("yes") || response.equals("no")) {
    validResponse = true;
    }else{
    System.out.println("Invalid response. Please enter 'yes' or 'no'.");
    }
    }
    }while(response.equalsIgnoreCase("yes"));
    
             
    }
        
  
   private void addApplication(){
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    Applicant at = new Applicant();
    at.viewApplicant();

    boolean validId = false;
    int aptid = -1;

    while (!validId){
        System.out.print("Enter the ID of the Applicant: ");
        if (sc.hasNextInt()) {
            aptid = sc.nextInt();
            sc.nextLine();
            String sql = "SELECT apt_id FROM tbl_applicant WHERE apt_id = ?";
            if (conf.getSingleValue(sql, aptid) != null){
                validId = true;
            }else{
                System.out.println("Selected ID doesn't exist. Try again.");
            }
        }else{
            System.out.println("Invalid input. Please enter a numeric Applicant ID.");
            sc.next();
        }
    }

    String annualIncomeQuery = "SELECT apt_annualincome FROM tbl_applicant WHERE apt_id = ?";
    String annualIncomeStr = conf.getSingleValue(annualIncomeQuery, aptid);
    int annualIncome = (annualIncomeStr != null) ? Integer.parseInt(annualIncomeStr) : 0;

    System.out.println("->---------->->---------->->---------->->---------->");
    String type = "";
    
    while(!type.matches("^[a-zA-Z ]+$")){
        System.out.print("Enter loan Type: ");
        type = sc.nextLine().trim();
        
        if(!type.matches("^[a-zA-Z ]+$")){
            System.out.println("Invalid input. Loan type must contain only letters and spaces. Please enter a valid loan type.");
        }
    }

    double amount = -1;
    while (amount <= 0 || amount > annualIncome){
        System.out.print("Enter loan Amount: ");
        
        if(sc.hasNextDouble()){
            amount = sc.nextDouble();
            
            if(amount <= 0){
                System.out.println("Invalid input. Loan amount must be a positive number.");
                
            }else if(amount > annualIncome){
                System.out.println("Invalid input. Loan amount cannot exceed the applicant's annual income of " + annualIncome + ".");
            }
        }else{
            System.out.println("Invalid input. Please enter a valid loan amount.");
            sc.next();
        }
    }
    sc.nextLine();

    LocalDate currdate = LocalDate.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String date = currdate.format(format);

    String duedate = "";
    while (!duedate.matches("\\d{2}/\\d{2}/\\d{4}")) {
        System.out.print("Enter due Date (MM/dd/yyyy): ");
        duedate = sc.nextLine().trim();
        if (!duedate.matches("\\d{2}/\\d{2}/\\d{4}")) {
            System.out.println("Invalid date format. Please enter in MM/dd/yyyy format.");
        }
    }

    String status = "Pending";
    System.out.println("->---------->->---------->->---------->->---------->");

    String applicationsql = "INSERT INTO tbl_application (apt_id, apn_type, apn_amount, apn_date, apn_duedate, apn_status) VALUES (?, ?, ?, ?, ?, ?)";
    conf.addRecord(applicationsql, aptid, type, amount, date, duedate, status);
}

    
    
    
  
  public void viewApplication(){
       
        
     System.out.print("\nApplication Details: \n");
        String aqry = "SELECT apn_id, apt_name, apn_type, apn_amount, apn_date, apn_duedate, apn_status FROM tbl_application "
                + "LEFT JOIN tbl_applicant ON tbl_applicant.apt_id = tbl_application.apt_id";

         
        String[] hrds = {"ID", "Applicant", "Loan type", "Loan amount", "Date", "Due Date", "Status"};
        String[] clmns = {"apn_id", "apt_name", "apn_type", "apn_amount", "apn_date", "apn_duedate", "apn_status"};
        config conf = new config();
        conf.viewRecords(aqry, hrds, clmns);
    }

  
  
  
  
  
  
  private void updateApplication(){
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    boolean validId = false;
    int id = -1;

    while (!validId){
        System.out.print("\nEnter Application ID: ");
        if (sc.hasNextInt()) {
            id = sc.nextInt();
            sc.nextLine(); 
            if (conf.getSingleValue("SELECT apn_id FROM tbl_application WHERE apn_id = ?", id) != null){
                validId = true; 
            }else{
                System.out.println("Selected ID doesn't exist. Try again.");
            }
        }else{
            System.out.println("Invalid input. Please enter a numeric Application ID.");
            sc.next();
        }
    }


    String currentStatus = conf.getSingleValue("SELECT apn_status FROM tbl_application WHERE apn_id = ?", id);
    if ("Approved".equalsIgnoreCase(currentStatus)){
        System.out.println("Application is already approved and cannot be changed.");
        return; 
    }else if(currentStatus == null){
        System.out.println("Error: Status not found.");
        return;
    }

    String newStatus = "";
    while(!newStatus.matches("(?i)Approved|Pending|Denied")){
        System.out.println("->---------->->---------->->---------->->---------->->---------->");
        System.out.print("Enter new status (e.g., Approved, Pending, Denied): ");
        newStatus = sc.nextLine().trim();
        if (!newStatus.matches("(?i)Approved|Pending|Denied")) {
            System.out.println("Invalid status. Please enter 'Approved', 'Pending', or 'Denied'.");
        }
        System.out.println("->---------->->---------->->---------->->---------->->---------->");
    }

    String qry = "UPDATE tbl_application SET apn_status = ? WHERE apn_id = ?";
    conf.updateRecord(qry, newStatus, id);

    System.out.println("Application status updated successfully.");
}

  
  
  



  
  
private void deleteApplication(){
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    System.out.println("->---------->->---------->->---------->");

    boolean validId = false;
    int id = -1;

    while(!validId){
        System.out.print("Enter Application ID to delete: ");
        if (sc.hasNextInt()) {
            id = sc.nextInt();
            sc.nextLine();
            String result = conf.getSingleValue("SELECT apn_id FROM tbl_application WHERE apn_id = ?", id);
            if(result != null){
                validId = true;
            }else{
                System.out.println("Selected ID doesn't exist. Try again.");
            }
        }else{
            System.out.println("Invalid input. Please enter a numeric Application ID.");
            sc.next();
        }
    }

    String qry = "DELETE FROM tbl_application WHERE apn_id = ?";
    conf.deleteRecord(qry, id);

    System.out.println("Application deleted successfully.");
}

 
 
 
 
 
 
 
}

  

