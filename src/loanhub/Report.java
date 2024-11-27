package loanhub;

import java.util.Scanner;

public class Report {

    public void viewreport() {
        String response;
        do {
            String aqry = "SELECT apt_id, apt_name, apt_age, apt_annualincome FROM tbl_applicant";
            String[] hrds = {"ID", "Applicant Name", "Age", "Annual Income"};
            String[] clmns = {"apt_id", "apt_name", "apt_age", "apt_annualincome"};
            config conf = new config();

            System.out.println("\n--- Report Panel ---");
            conf.viewRecords(aqry, hrds, clmns);

            Scanner sc = new Scanner(System.in);

            boolean validReportId = false;
            int reportid = -1;

            while (!validReportId) {
                System.out.print("\nEnter Applicant ID to view individual report (or 0 to return): ");
                if (sc.hasNextInt()) {
                    reportid = sc.nextInt();
                    sc.nextLine();
                    String result = conf.getSingleValue("SELECT apt_id FROM tbl_applicant WHERE apt_id = ?", reportid);
                    if (reportid == 0 || result != null) {
                        validReportId = true;
                    } else {
                        System.out.println("Selected ID doesn't exist. Try again.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a numeric Applicant ID.");
                    sc.next();
                }
            }

            if (reportid != 0) {
                displayIndividualReport(reportid, conf);
                displayApplicationsForApplicant(reportid, conf);
            } else {
                System.out.println("Returning to the main menu...");
                break;
            }

            do {
                System.out.print("\nDo you still want to continue in this Panel? (yes/no): ");
                response = sc.nextLine().trim().toLowerCase();

                if (!response.equals("yes") && !response.equals("no")) {
                    System.out.println("Invalid response. Please enter 'yes' or 'no'.");
                }
            } while (!response.equals("yes") && !response.equals("no"));

        } while (response.equalsIgnoreCase("yes"));
    }

    private void displayIndividualReport(int id, config conf) {
        String iqry = "SELECT apt_id, apt_name, apt_age, apt_gender, apt_contact, apt_address, apt_annualincome " +
                      "FROM tbl_applicant WHERE apt_id = ?";
        Object[] applicantData = conf.getSingleRecord(iqry, id);

        if (applicantData != null) {
            System.out.println("\n\n=========================================");
            System.out.println("     Individual Report for Applicant ID: " + id);
            System.out.println("=========================================");
            System.out.println("Applicant ID    : " + applicantData[0]);
            System.out.println("Name            : " + applicantData[1]);
            System.out.println("Age             : " + applicantData[2]);
            System.out.println("Gender          : " + applicantData[3]);
            System.out.println("Contact         : " + applicantData[4]);
            System.out.println("Address         : " + applicantData[5]);
            System.out.println("Annual Income   : " + applicantData[6]);
            System.out.println("-----------------------------------------");
        } else {
            System.out.println("No record found for Applicant ID: " + id);
        }
    }

    private void displayApplicationsForApplicant(int id, config conf) {
        String appQry = "SELECT apn_id, apn_type, apn_date, apn_duedate, apn_amount, apn_status " +
                        "FROM tbl_application WHERE apt_id = ?";
        String[] appHeaders = {"Application ID", "Loan Type", "Date", "Due Date", "Amount", "Status"};
        String[] appColumns = {"apn_id", "apn_type", "apn_date", "apn_duedate", "apn_amount", "apn_status"};

        System.out.println("\n\n=========================================");
        System.out.println("     Applications for Applicant ID: " + id);
        System.out.println("=========================================");
        conf.viewRecords(appQry, appHeaders, appColumns, id);

        // Count applications by status
        int approved = conf.getCount("SELECT COUNT(*) FROM tbl_application WHERE apt_id = ? AND UPPER(apn_status) = 'APPROVED'", id);
        int denied = conf.getCount("SELECT COUNT(*) FROM tbl_application WHERE apt_id = ? AND UPPER(apn_status) = 'DENIED'", id);
        int pending = conf.getCount("SELECT COUNT(*) FROM tbl_application WHERE apt_id = ? AND UPPER(apn_status) = 'PENDING'", id);

        // Calculate total amounts for each status
        double approvedAmount = conf.getTotal("SELECT SUM(apn_amount) FROM tbl_application WHERE apt_id = ? AND UPPER(apn_status) = 'APPROVED'", id);
        double deniedAmount = conf.getTotal("SELECT SUM(apn_amount) FROM tbl_application WHERE apt_id = ? AND UPPER(apn_status) = 'DENIED'", id);
        double pendingAmount = conf.getTotal("SELECT SUM(apn_amount) FROM tbl_application WHERE apt_id = ? AND UPPER(apn_status) = 'PENDING'", id);

        // Cool and organized summary
        System.out.println("\n==============================================");
        System.out.println("            Application Summary        ");
        System.out.println("==============================================");
        System.out.printf("Total Applications Submitted  : %d%n", (approved + denied + pending));
        System.out.println("Applications Breakdown:");
        System.out.printf("   - Approved                 : %d%n", approved);
        System.out.printf("   - Denied                   : %d%n", denied);
        System.out.printf("   - Pending                  : %d%n", pending);
        System.out.println("---------------------------------------------");
        System.out.printf("Total Approved Amount         : ₱%,.2f%n", approvedAmount);
        System.out.printf("Total Denied Amount           : ₱%,.2f%n", deniedAmount);
        System.out.printf("Total Pending Amount          : ₱%,.2f%n", pendingAmount);
        System.out.println("=============================================\n");

        if (approved + denied + pending == 0) {
            System.out.println("No applications found for this applicant.");
        }
    }
}
