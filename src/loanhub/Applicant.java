package loanhub;

import java.util.Scanner;


public class Applicant {
    
    
    public void mApplicant(){
        
    Scanner sc = new Scanner(System.in);
    String response;
    do{   
    System.out.println("\n-------------------------");   
    System.out.println("APPLICANT PANEL");
    System.out.println("1. Add Applicant");
    System.out.println("2. View Applicant ");
    System.out.println("3. Update Applicant ");
    System.out.println("4. Delete Applicant ");
    System.out.println("5. Exit ");
    
    System.out.print("Enter Action: ");
    int act = sc.nextInt();
    Applicant at = new Applicant();
    switch(act){
        
        case 1:  
              at.addApplicant(); 
           break;
        case 2:
              at.viewApplicant();
           break;
        case 3:
              at.viewApplicant();
              at.updateApplicant();
           break;
        case 4:
              at.viewApplicant();
              at.deleteApplicant(); 
              at.viewApplicant();
            break;
        case 5:
            break;
           
            
    }
    System.out.print("Do you want to continue?(yes/no): ");
    response = sc.next();
    
    }while(response.equalsIgnoreCase("yes"));      
}
  public void addApplicant(){
      
       Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Full Name (family name/given name/middle initial): ");
        String name = sc.nextLine();
         System.out.print("Date of birth (month/day/year): ");
        String birth = sc.nextLine();
        System.out.print("Age: ");
        String age = sc.nextLine();
         System.out.print("Gender: ");
        String gender = sc.nextLine();
        System.out.print("Contact: ");
        String contact = sc.nextLine();
         System.out.print("Address (purok/barangay/city/province): ");
        String address = sc.nextLine();
         System.out.print("Email Address: ");
        String email = sc.nextLine();
        System.out.print("Employment Status (Employed - Regular, Part-time, Contractual, Unemployed): ");
        String status = sc.nextLine();
        System.out.print("Annual income: ");
        String annualincome = sc.nextLine();
    
        
          String sql = "INSERT INTO tbl_applicant (apt_name, apt_birth, apt_age, apt_gender, apt_contact, apt_address, apt_email, apt_status, apt_annualincome) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, name, birth, age, gender, contact, address, email, status, annualincome);
   
  }


   
    

public void viewApplicant() {
        String aqry = "SELECT * FROM tbl_applicant";
        String[] hrds = {"ID", "FULL Name", "DATE OF BIRTH", "AGE", "GENDER", "CONTACT NUMBER", "ADDRESS", "EMAIL", "STATUS", "MONTHLY INCOME"};
        String[] clmns = {"apt_id", "apt_name", "apt_birth", "apt_age", "apt_gender", "apt_contact", "apt_address", "apt_email", "apt_status", "apt_annualincome"};
        config conf = new config();
        conf.viewRecords(aqry, hrds, clmns);
    }



   private void updateApplicant(){
   Scanner sc = new Scanner(System.in);
   config conf = new config();
   System.out.print("Enter Applicant ID: ");
   int id = sc.nextInt();
            sc.nextLine();
            
            while(conf.getSingleValue("Select apt_id FROM tbl_applicant WHERE apt_id = ?", id)==0){
            System.out.println("Selected ID doesn't exist");
            System.out.println("Selected Applicant ID again: ");
            id = sc.nextInt();
                 sc.nextLine();
            }
            
        System.out.print("Updated Full Name (family name/given name/middle initial): ");
        String uname = sc.nextLine();
         System.out.print("Updated Date of birth (month/day/year): ");
        String ubirth = sc.nextLine();
        System.out.print("Updated Age: ");
        String uage = sc.nextLine();
        System.out.print("Updated Gender: ");
        String ugender = sc.nextLine();
        System.out.print("Updated Contact: ");
        String ucontact = sc.nextLine();
         System.out.print("Updated Address (purok/barangay/city/province): ");
        String uaddress = sc.nextLine();
         System.out.print("Updated Email Address: ");
        String uemail = sc.nextLine();
        System.out.print("Updated Status (Employed, Unemployed): ");
        String ustatus = sc.nextLine();
        System.out.print("Updated Monthly income: ");
        String uannualincome = sc.nextLine();  
      
      String qry = "UPDATE tbl_applicant SET apt_name = ?, apt_birth = ?, apt_age = ?, apt_gender = ?, apt_contact = ?, apt_address = ?, apt_email = ?, apt_status = ?, apt_annualincome = ? WHERE apt_id = ? ";
      
      conf.updateRecord(qry, uname, ubirth, uage, ugender, ucontact, uaddress, uemail, ustatus, uannualincome, id);
   
   
   
   }
   
   
   
   
  private void deleteApplicant(){

       Scanner sc = new Scanner(System.in);
       config conf = new config();
   System.out.print("Enter Applicant ID to delete: ");
   int id = sc.nextInt(); 
   
   while(conf.getSingleValue("Select apt_id FROM tbl_applicant WHERE apt_id = ?", id)==0){
            System.out.println("Selected ID doesn't exist");
            System.out.println("Selected Applicant ID again: ");
            id = sc.nextInt();
                 sc.nextLine();
            }
   
   String sqlDelete = "DELETE FROM tbl_applicant WHERE apt_id = ?";
   
   conf.deleteRecord(sqlDelete, id);
    }
  
  
    
    
    
}
