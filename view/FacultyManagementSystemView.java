package view;

import model.FacultyDatabaseModel;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;


public class FacultyManagementSystemView {

    FacultyDatabaseModel modelNew = new FacultyDatabaseModel();

    public int showLoginPage(Scanner scanner) {
	System.out.println();
        System.out.println("Login Page:");
        System.out.println("1. Login as Student");
        System.out.println("2. Login as Admin");
        System.out.println("3. Login as Teacher");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");

	return scanner.nextInt();
    }


    public void runAdminMenu(Scanner scanner) {
        while (true) {
	    System.out.println();
            System.out.println("Faculty Management System Menu:");
            System.out.println("1. Enter data of a new teacher");
            System.out.println("2. Remove data of a teacher");
            System.out.println("3. Modify data for a teacher");
            System.out.println("4. Search for a teacher and additional search options");
	    System.out.println("5. Last Logged in Info");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    modelNew.enterNewTeacherData(scanner);
                    break;
                case 2:
                    modelNew.removeTeacherData(scanner);
                    break;
                case 3:
                    modelNew.modifyTeacherData(scanner);
                    break;
                case 4:
                    searchSubMenu(scanner);
                    break;
                case 5:
                    modelNew.lastLoginInfo();
                    break;
                case 6:
                    System.out.println("Exiting Faculty Management System.");
                    return; // Exit admin menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void searchSubMenu(Scanner scanner) {
        while (true) {
	    System.out.println();
            System.out.println("Search Menu:");
            System.out.println("1. Search for a teacher by name");
            System.out.println("2. Search for a teacher by subject");
            System.out.println("3. Search for a teacher by cabin number");
            System.out.println("4. Search for teachers by section");
            System.out.println("5. Book Your Slot");
            System.out.println("6. Back to main menu");
            System.out.print("Enter your choice: ");

            int searchChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (searchChoice) {
                case 1:
                    modelNew.searchTeacherByName(scanner);
                    break;
                case 2:
                    modelNew.searchTeacherBySubject(scanner);
                    break;
                case 3:
                    modelNew.searchTeacherByCabinNumber(scanner);
                    break;
                case 4:
                    modelNew.searchTeacherBySection(scanner);
                    break;
                case 5:
                    modelNew.bookSlotWithTeacher(scanner);
                    break;
                case 6:
                    return; // Back to the main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    public void runTeacherMenu(Scanner scanner) {
        while (true) {
	    System.out.println();
            System.out.println("Faculty Management System Menu:");
            System.out.println("1. Search for a teacher by name");
            System.out.println("2. Search for a teacher by subject");
            System.out.println("3. Search for a teacher by cabin number");
            System.out.println("4. Search for teachers by section");
            System.out.println("5. Check if a student has booked my slot");           
            System.out.println("6. Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

           switch (choice) {
                case 1:
                    modelNew.searchTeacherByName(scanner);
                    break;
                case 2:
                    modelNew.searchTeacherBySubject(scanner);
                    break;
                case 3:
                    modelNew.searchTeacherByCabinNumber(scanner);
                    break;
                case 4:
                    modelNew.searchTeacherBySection(scanner);
                    break;
                case 5:
                    modelNew.checkSlotBookingStatus(scanner);
                    break;
                case 6:
                    return; // Back to the main menu
                 default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}