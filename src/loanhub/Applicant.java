package loanhub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class Applicant{
    
    
    public void mApplicant(){
    Scanner sc = new Scanner(System.in);
    
    
     
    String response = "";
    do{
        
        
        System.out.println("\nAPPLICANT PANEL");
        System.out.println("----------------------");
        System.out.println("|1. Add Applicant    |");
        System.out.println("|2. View Applicant   |");
        System.out.println("|3. Update Applicant |");
        System.out.println("|4. Delete Applicant |");
        System.out.println("|5. Exit             |");
        System.out.println("----------------------");

        
        int act = 0;
        boolean validInput = false;
        while (!validInput){
            System.out.print("Enter Action (1-5): ");
            String input = sc.next();
            try{
                act = Integer.parseInt(input);
                if (act >= 1 && act <= 5){
                    validInput = true;
                }else{
                    System.out.println("Invalid action. Please enter a number between 1 and 5.");
                }
            }catch(NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
            }
        }

        Applicant at = new Applicant();
        switch (act) {
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
       
    boolean validResponse = false;
    while (!validResponse) {
        System.out.print("\nDo you still want to continue in this Panel? (yes/no): ");
        response = sc.next().trim().toLowerCase();
        
        if(response.equals("yes") || response.equals("no")){
           validResponse = true;
        }else{
        System.out.println("Invalid response. Please enter 'yes' or 'no'.");
        }
    }
} while(response.equalsIgnoreCase("yes"));
 
}

  public void addApplicant(){
      
       Scanner sc = new Scanner(System.in);
        config conf = new config();
       
    System.out.println("->---------->->---------->->---------->->---------->->---------->");
        
    String name = "";
    while (!name.matches("^[a-zA-Z]+,\\s*[a-zA-Z]+(?:\\s+[a-zA-Z]+)*\\s+[a-zA-Z]\\.$")){
    System.out.print("Full Name (family name, given name, middle initial): ");
    name = sc.nextLine().trim();
    if(!name.matches("^[a-zA-Z]+,\\s*[a-zA-Z]+(?:\\s+[a-zA-Z]+)*\\s+[a-zA-Z]\\.$")) {
    System.out.println("Invalid Full Name. Please enter in the format 'Last Name, First Name Middle Initial.'.");
    }
}


    
    String birth = "";
    while (birth.isEmpty() || !birth.matches("\\d{2}/\\d{2}/\\d{4}")){
    System.out.print("Date of birth (MM/DD/YYYY): ");
    birth = sc.nextLine().trim();
    if(birth.isEmpty() || !birth.matches("\\d{2}/\\d{2}/\\d{4}")){
    System.out.println("Invalid date of birth. Please enter a valid date in MM/DD/YYYY format.");
    }
}

    String age = "";
    while (!age.matches("\\d+") || Integer.parseInt(age) < 18 || Integer.parseInt(age) > 60){
    System.out.print("Age (18-60): ");
    age = sc.nextLine().trim();
    if(!age.matches("\\d+") || Integer.parseInt(age) < 18 || Integer.parseInt(age) > 60){
    System.out.println("Invalid Age. Please enter an age between 18 and 60.");
    }
}


    String gender = "";
    while (gender.isEmpty() || !gender.matches("(?i)male|female|other")) {
    System.out.print("Gender (Male/Female/Other): ");
    gender = sc.nextLine().trim();
    if(gender.isEmpty() || !gender.matches("(?i)male|female|other")) {
    System.out.println("Invalid Gender. Please enter Male, Female, or Other.");
    }
}

    String contact = "";
    while (!contact.matches("^(09|\\+639)\\d{9}$")){
    System.out.print("Contact (Philippine number): ");
    contact = sc.nextLine().trim();
    if(!contact.matches("^(09|\\+639)\\d{9}$")) {
    System.out.println("Invalid Contact Number. Please enter a valid Philippine number starting with 09 or +639 followed by 9 digits.");
    }
}


    String address = "";
    while (!address.matches("^\\d+\\s*,\\s*[a-zA-Z]+\\s*,\\s*[a-zA-Z]+\\s*,\\s*[a-zA-Z]+$")){
    System.out.print("Address (purok number, barangay, city, province): ");
    address = sc.nextLine().trim();
    if(!address.matches("^\\d+\\s*,\\s*[a-zA-Z]+\\s*,\\s*[a-zA-Z]+\\s*,\\s*[a-zA-Z]+$")){
    System.out.println("Invalid Address. Please enter in the format 'purok number, barangay, city, province'.");
    }
}


    String email = "";
    while (email.isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
    System.out.print("Email Address: ");
    email = sc.nextLine().trim();
    if(email.isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
    System.out.println("Invalid Email Address. Please enter a valid email.");
    }
}

    String status = "";
    while (status.isEmpty() || !status.matches("(?i)employed|unemployed|part-time|contractual")){
    System.out.print("Employment Status (Employed, Unemployed, Part-time, Contractual): ");
    status = sc.nextLine().trim();
    if (status.isEmpty() || !status.matches("(?i)employed|unemployed|part-time|contractual")){
    System.out.println("Invalid Employment Status. Please enter Employed, Unemployed, Part-time, or Contractual.");
    }
}

    String annualincome = "";
    while (annualincome.isEmpty() || !annualincome.matches("\\d+") || Integer.parseInt(annualincome) < 120000){
    System.out.print("Annual income (minimum PHP 120,000): ");
    annualincome = sc.nextLine().trim();
    if(annualincome.isEmpty() || !annualincome.matches("\\d+") || Integer.parseInt(annualincome) < 120000) {
    System.out.println("Invalid Annual Income. Please enter a valid income as a number and at least 120,000.");
    }
}


    System.out.println("->---------->->---------->->---------->->---------->->---------->");

        
        String sql = "INSERT INTO tbl_applicant (apt_name, apt_birth, apt_age, apt_gender, apt_contact, apt_address, apt_email, apt_status, apt_annualincome) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, name, birth, age, gender, contact, address, email, status, annualincome);
   
  }


   
    

public void viewApplicant(){
        System.out.print("\nApplicant Details: \n");
        String aqry = "SELECT * FROM tbl_applicant";
        String[] hrds = {"ID", "FULL Name", "DATE OF BIRTH", "AGE", "GENDER", "CONTACT NUMBER", "ADDRESS", "EMAIL", "STATUS", "ANNUAL INCOME"};
        String[] clmns = {"apt_id", "apt_name", "apt_birth", "apt_age", "apt_gender", "apt_contact", "apt_address", "apt_email", "apt_status", "apt_annualincome"};
        config conf = new config();
        conf.viewRecords(aqry, hrds, clmns);
    }



   private void updateApplicant() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    System.out.print("\nEnter Applicant ID to Update: ");
    int id = sc.nextInt();
    sc.nextLine();

    while (conf.getSingleValue("SELECT apt_id FROM tbl_applicant WHERE apt_id = ?", id) == null) {
        System.out.println("Selected ID doesn't exist.");
        System.out.print("Enter Applicant ID again: ");
        id = sc.nextInt();
        sc.nextLine();
    }

    System.out.println("->---------->->---------->->---------->->---------->->---------->");

    String uname = "";
    while (!uname.matches("^[a-zA-Z]+,\\s*[a-zA-Z]+(?:\\s+[a-zA-Z]+)*\\s+[a-zA-Z]\\.$")) {
        System.out.print("Full Name (family name, given name, middle initial): ");
        uname = sc.nextLine().trim();
        if (!uname.matches("^[a-zA-Z]+,\\s*[a-zA-Z]+(?:\\s+[a-zA-Z]+)*\\s+[a-zA-Z]\\.$")) {
            System.out.println("Invalid Full Name. Please enter in the format 'Last Name, First Name Middle Initial.'.");
        }
    }

    String ubirth = "";
    while (ubirth.isEmpty() || !ubirth.matches("\\d{2}/\\d{2}/\\d{4}")) {
        System.out.print("Date of birth (MM/DD/YYYY): ");
        ubirth = sc.nextLine().trim();
        if (ubirth.isEmpty() || !ubirth.matches("\\d{2}/\\d{2}/\\d{4}")) {
            System.out.println("Invalid date of birth. Please enter a valid date in MM/DD/YYYY format.");
        }
    }

    String uage = "";
    while (!uage.matches("\\d+") || Integer.parseInt(uage) < 18 || Integer.parseInt(uage) > 60) {
        System.out.print("Age (18-60): ");
        uage = sc.nextLine().trim();
        if (!uage.matches("\\d+") || Integer.parseInt(uage) < 18 || Integer.parseInt(uage) > 60) {
            System.out.println("Invalid Age. Please enter an age between 18 and 60.");
        }
    }

    String ugender = "";
    while (ugender.isEmpty() || !ugender.matches("(?i)male|female|other")) {
        System.out.print("Gender (Male/Female/Other): ");
        ugender = sc.nextLine().trim();
        if (ugender.isEmpty() || !ugender.matches("(?i)male|female|other")) {
            System.out.println("Invalid Gender. Please enter Male, Female, or Other.");
        }
    }

    String ucontact = "";
    while (!ucontact.matches("^(09|\\+639)\\d{9}$")) {
        System.out.print("Contact (Philippine number): ");
        ucontact = sc.nextLine().trim();
        if (!ucontact.matches("^(09|\\+639)\\d{9}$")) {
            System.out.println("Invalid Contact Number. Please enter a valid Philippine number starting with 09 or +639 followed by 9 digits.");
        }
    }

    String uaddress = "";
    while (!uaddress.matches("^\\d+\\s*,\\s*[a-zA-Z]+\\s*,\\s*[a-zA-Z]+\\s*,\\s*[a-zA-Z]+$")) {
        System.out.print("Address (purok number, barangay, city, province): ");
        uaddress = sc.nextLine().trim();
        if (!uaddress.matches("^\\d+\\s*,\\s*[a-zA-Z]+\\s*,\\s*[a-zA-Z]+\\s*,\\s*[a-zA-Z]+$")) {
            System.out.println("Invalid Address. Please enter in the format 'purok number, barangay, city, province'.");
        }
    }

    String uemail = "";
    while (uemail.isEmpty() || !uemail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
        System.out.print("Email Address: ");
        uemail = sc.nextLine().trim();
        if (uemail.isEmpty() || !uemail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            System.out.println("Invalid Email Address. Please enter a valid email.");
        }
    }

    String ustatus = "";
    while (ustatus.isEmpty() || !ustatus.matches("(?i)employed|unemployed|part-time|contractual")) {
        System.out.print("Employment Status (Employed, Unemployed, Part-time, Contractual): ");
        ustatus = sc.nextLine().trim();
        if (ustatus.isEmpty() || !ustatus.matches("(?i)employed|unemployed|part-time|contractual")) {
            System.out.println("Invalid Employment Status. Please enter Employed, Unemployed, Part-time, or Contractual.");
        }
    }

    String uannualincome = "";
    while (uannualincome.isEmpty() || !uannualincome.matches("\\d+") || Integer.parseInt(uannualincome) < 120000) {
        System.out.print("Annual income (minimum PHP 120,000): ");
        uannualincome = sc.nextLine().trim();
        if (uannualincome.isEmpty() || !uannualincome.matches("\\d+") || Integer.parseInt(uannualincome) < 120000) {
            System.out.println("Invalid Annual Income. Please enter a valid income as a number and at least 120,000.");
        }
    }
    
    System.out.println("->---------->->---------->->---------->->---------->->---------->");

    String qry = "UPDATE tbl_applicant SET apt_name = ?, apt_birth = ?, apt_age = ?, apt_gender = ?, apt_contact = ?, apt_address = ?, apt_email = ?, apt_status = ?, apt_annualincome = ? WHERE apt_id = ?";
    conf.updateRecord(qry, uname, ubirth, uage, ugender, ucontact, uaddress, uemail, ustatus, uannualincome, id);
    System.out.println("Applicant updated successfully.");
}

   
   
   
   private void deleteApplicant() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    
    System.out.println("->---------->->---------->->---------->");
    boolean validId = false;
    int id = -1;

    while (!validId) {
        System.out.print("Enter Applicant ID to delete: ");
        if (sc.hasNextInt()) {
            id = sc.nextInt();
            sc.nextLine(); 
            String result = conf.getSingleValue("SELECT apt_id FROM tbl_applicant WHERE apt_id = ?", id);
            if (result != null) {
                validId = true;
            } else {
                System.out.println("Selected ID doesn't exist.");
            }
        } else {
            System.out.println("Invalid input. Please enter a numeric Applicant ID.");
            sc.next();
        }
    }
    
    System.out.println("->---------->->---------->->---------->");

    
    System.out.print(" This will permanently delete the applicant and all associated applications.\n Are you sure you want to proceed? (yes/no): ");
    String confirmation = sc.nextLine().trim().toLowerCase();
    if (!confirmation.equals("yes")) {
        System.out.println("Operation cancelled.");
        return;
    }

    try (Connection conn = conf.connectDB()) {
        conn.setAutoCommit(false);

        
        String deleteApplicationsSql = "DELETE FROM tbl_application WHERE apt_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteApplicationsSql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }

        
        String deleteApplicantSql = "DELETE FROM tbl_applicant WHERE apt_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteApplicantSql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }

        conn.commit();
        System.out.println("\nApplicant and all associated applications deleted successfully.");
    } catch (SQLException e) {
        System.out.println("Error during deletion: " + e.getMessage());
      
        try (Connection conn = conf.connectDB()) {
            conn.rollback();
        } catch (SQLException rollbackEx) {
            System.out.println("Error during rollback: " + rollbackEx.getMessage());
        }
    }
}

  
  
    
    
    
}
