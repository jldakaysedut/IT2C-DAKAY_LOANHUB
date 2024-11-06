
package loanhub;

import java.util.Scanner;


public class LoanHub {

    
    public static void main(String[] args) {
     Scanner sc = new Scanner(System.in);
     boolean exit = true;  
     
        do{
        System.out.println("\nWELCOME TO LOANHUB");
        System.out.println("-----------------");
        System.out.println("1. Manage Applicant");
        System.out.println("2. Manage Application");
        System.out.println("3. Show Reports");
        System.out.println("4. Exit");
                  
        System.out.print("Enter action: ");
        int action = sc.nextInt();
                    
           switch(action){
               
           case 1:
           Applicant at = new Applicant();
           at.mApplicant();
           break;
            
           case 2:
           Application an = new Application();
           an.mApplication();
           break;
           
            case 4:
                 System.out.println("Exit selected...type yes to continue: ");
                String resp = sc.next();
                if(resp.equalsIgnoreCase("yes")){
                exit = false;
                }
                break;
                
           
               
                
              
        
       
    }
      
  }while(exit);
          
           
}

}