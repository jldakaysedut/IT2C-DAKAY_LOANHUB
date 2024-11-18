package loanhub;

import java.util.Scanner;

/**
 * This class handles the reporting functionality for the LoanHub application.
 * It allows viewing of all applicants and detailed information for a specific applicant.
 */
public class Report {

    public void viewreport() {
        String aqry = "SELECT apt_id, apt_name, apt_age, apt_annualincome FROM tbl_applicant";
        String[] hrds = {"ID", "Applicant Name", "Age", "Annual Income"};
        String[] clmns = {"apt_id", "apt_name", "apt_age", "apt_annualincome"};
        config conf = new config();
        conf.viewRecords(aqry, hrds, clmns);

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Applicant ID to view individual report: ");
        int reportid = sc.nextInt();
        sc.nextLine();  // Consume the newline

        displayIndividualReport(reportid, conf);
    }

private void displayIndividualReport(int id, config conf) {
    String iqry = "SELECT apt_id, apt_name, apt_age, apt_gender, apt_contact, apt_address, apt_annualincome " +
                  "FROM tbl_applicant WHERE apt_id = ?";
    Object[] applicantData = conf.getSingleRecord(iqry, id);

    if (applicantData != null) {
        System.out.println("\nIndividual Report for Applicant ID: " + id);
        System.out.println("-------------------------------");
        System.out.println("Applicant ID    : " + applicantData[0]);
        System.out.println("Name            : " + applicantData[1]);
        System.out.println("Age             : " + applicantData[2]);
        System.out.println("Gender          : " + applicantData[3]);
        System.out.println("Contact         : " + applicantData[4]);
        System.out.println("Address         : " + applicantData[5]);
        System.out.println("Annual Income   : " + applicantData[6]);
    } else {
        System.out.println("No record found for Applicant ID: " + id);
    }
}
}

