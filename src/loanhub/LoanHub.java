package loanhub;

import java.util.Scanner;


public class LoanHub{

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        boolean exit = true;

      

do{
   

    System.out.println("\n---WELCOME TO LOANHUB---");
    System.out.println("------------------------");
    System.out.println("|1. Manage Applicant   |");
    System.out.println("|2. Manage Application |");
    System.out.println("|3. Show Reports       |");
    System.out.println("|4. Exit               |");
    System.out.println("------------------------");

  
    int action = 0;
    boolean validInput = false;
    while (!validInput){
        System.out.print("Enter Action (1-4): ");
        String input = sc.next();
        try{
            action = Integer.parseInt(input);
            if(action >= 1 && action <= 4){
                validInput = true;
            }else{
                System.out.println("Invalid action. Please enter a number between 1 and 4.");
            }
        }catch(NumberFormatException e){
            System.out.println("Invalid input. Please enter a number between 1 and 4.");
        }
    }
           
          switch(action){
           case 1:
           Applicant at = new Applicant();
           at.mApplicant();
           break;
            
           case 2:
           Application an = new Application();
           an.mApplication();
           break;
           
           case 3:
               Report rt = new Report();
               rt.viewreport();
               break;
        case 4:
            boolean validResponse = false;
            while (!validResponse) {
                System.out.print("Exit selected...type yes to continue: ");
                String resp = sc.next().trim().toLowerCase();
                if (resp.equals("yes")) {
                    exit = false;
                    validResponse = true;
                } else if (resp.equals("no")) {
                    validResponse = true;
                } else {
                    System.out.println("Invalid response. Please enter 'yes' or 'no'.");
                }
            }
            break;
    }

}while(exit);

}
    
}