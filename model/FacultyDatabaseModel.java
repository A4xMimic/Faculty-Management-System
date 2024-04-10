package model;



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

public class FacultyDatabaseModel {
    private static Connection con;
    private  static Statement stmt;
    private  static PreparedStatement pstmt;
    public static final String DB_URL = "jdbc:mysql://localhost:3306/facultyData";
    public static final String USER = "root";
    public static final String PWD = "Arya@2023";


    public static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection createConnection() {
        try {
            con = DriverManager.getConnection(DB_URL, USER, PWD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static Statement createStatement() {
        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    public static void initializeDatabase() {
        loadDriver();
        createConnection();
        createStatement();
    }
	

    public static void enterNewTeacherData(Scanner scanner) {
	    System.out.println();
            System.out.print("Enter teacher name: ");
            String name = scanner.nextLine();

            System.out.print("Enter teacher timings: ");
            String timings = scanner.nextLine();

            System.out.print("Enter subjects taught: ");
            String subjectsTaught = scanner.nextLine();

            System.out.print("Enter teacher's phone number: ");
            String phoneNumber = scanner.nextLine();

            System.out.print("Enter sections taught: ");
            String sectionsTaught = scanner.nextLine();	

            System.out.print("Enter teacher's cabin number: ");
            int cabinNumber = scanner.nextInt();

    	    insertNewTeacherData(cabinNumber,name,timings,subjectsTaught,phoneNumber,sectionsTaught);

    }



public static void insertNewTeacherData(int cabinNo , String name, String time, String sub, String pho, String Sec)
{

String sqlquery = " INSERT INTO FacultyTable VALUES (?,?,?,?,?,?,'0'); " ; 
try{
pstmt = con.prepareStatement(sqlquery);

pstmt.setInt(1,cabinNo);
pstmt.setString(2,name);
pstmt.setString(3,time);
pstmt.setString(4,sub);
pstmt.setString(5,pho);
pstmt.setString(6,Sec);
System.out.println();
System.out.println("New Teacher information inserted ");
System.out.println("Teacher Name : " + name );
System.out.println("Teacher Cabin : " + cabinNo );
System.out.println("Teacher Timmings : " + time );
System.out.println("Teacher Phone number : " + pho );
System.out.println("Teacher Section : " + Sec );
System.out.println("Teacher Subject : " + sub );
System.out.println();
pstmt.executeUpdate();
}
catch(SQLException e)
{System.out.println(e);}
}


public static void removeTeacherData(Scanner scanner) {
	System.out.println();
        System.out.print("Enter the name of the teacher to remove: ");
        String nameToRemove = scanner.nextLine();

        System.out.print("Enter teacher's cabin number: ");
        int cabinNumber = scanner.nextInt();
	
	removeData(cabinNumber);
  
    }


public static void removeData(int cabno)
{
String sqlquery = " DELETE FROM FacultyTable WHERE Cabin_No = ?; " ;

try{
pstmt = con.prepareStatement(sqlquery);
pstmt.setInt(1,cabno);
pstmt.executeUpdate();
System.out.println();
System.out.println("Information deleted sucessfully");
System.out.println();
}
catch(SQLException e)
{System.out.println(e);}
}


public static void modifyTeacherData(Scanner scanner) {
    System.out.print("Enter the cabin number of the teacher to modify: ");
    String cabinNumberToModify = scanner.nextLine();

    String sqlQuery = "SELECT * FROM FacultyTable WHERE cabin_No LIKE ?";
    try (PreparedStatement pstmt = con.prepareStatement(sqlQuery)) {
        pstmt.setString(1, "%" + cabinNumberToModify + "%");

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            // Teacher found, display modification menu
	    System.out.println();
            System.out.println("Modify Teacher Data Menu:");
            System.out.println("1. Change name");
            System.out.println("2. Change cabin number");
            System.out.println("3. Change phone number");
            System.out.println("4. Change timings");
            System.out.println("5. Change subjects taught");
            System.out.println("6. Change section taught");
            System.out.println("7. Back to main menu");
            System.out.print("Enter your choice: ");

            int modifyChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (modifyChoice) {
                case 1:
                    modifyTeacherDataByColumn(cabinNumberToModify, "Name", scanner);
                    break;
                case 2:
                    modifyTeacherDataByColumn(cabinNumberToModify, "cabin_No", scanner);
                    break;
                case 3:
                    modifyTeacherDataByColumn(cabinNumberToModify, "Phone_No", scanner);
                    break;
                case 4:
                    modifyTeacherDataByColumn(cabinNumberToModify, "Slot_Time", scanner);
                    break;
                case 5:
                    modifyTeacherDataByColumn(cabinNumberToModify, "Subject", scanner);
                    break;
		case 6:
                    modifyTeacherDataByColumn(cabinNumberToModify, "Section", scanner);
                    break;
                case 7:
                    return; // Back to the main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } else {
            System.out.println("Teacher with the cabin number '" + cabinNumberToModify + "' not found.");
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
}

public static void modifyTeacherDataByColumn(String cabinNumber, String columnName, Scanner scanner) {
    System.out.println();
    System.out.print("Enter new " + columnName + ": ");
    String newValue = scanner.nextLine();
    String sqlQuery = "UPDATE FacultyTable SET " + columnName + "=? WHERE cabin_No LIKE ?";
    try (PreparedStatement pstmt = con.prepareStatement(sqlQuery)) {
        pstmt.setString(1, newValue);
        pstmt.setString(2, "%" + cabinNumber + "%");

        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Teacher " + columnName + " changed successfully.");
        } else {
            System.out.println("Failed to update teacher " + columnName + " in the database.");
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
}



public static void searchTeacherByName(Scanner scanner) {
    System.out.println();
    System.out.print("Enter the name of the teacher to search: ");
    String name = scanner.nextLine();

    String sqlquery = "SELECT * FROM FacultyTable WHERE Name LIKE '%" + name + "%';";
    try {
        ResultSet rs = stmt.executeQuery(sqlquery);

        boolean found = false;  // Flag to check if any teacher is found

        while (rs.next()) {
            found = true;
            System.out.println();
            System.out.println("Teacher Name : " + rs.getString(2));
            System.out.println("Teacher Cabin : " + rs.getInt(1));
            System.out.println("Teacher Timings : " + rs.getString(3));
            System.out.println("Teacher Phone number : " + rs.getString(5));
            System.out.println("Teacher Section : " + rs.getString(6));
            System.out.println("Teacher Subject : " + rs.getString(4));
            System.out.println();
        }

        if (!found) {
            System.out.println("No teacher available with the name: " + name);
        }

    } catch (SQLException e) {
        System.out.println(e);
    }
}


public static void searchTeacherBySubject(Scanner scanner) {
    System.out.println();
    System.out.print("Enter the subject to search for teachers: ");
    String subjectToSearch = scanner.nextLine();

    String sqlquery = "SELECT * FROM FacultyTable WHERE Subject = '" + subjectToSearch + "';";
    try {
        ResultSet rs = stmt.executeQuery(sqlquery);

        boolean found = false;  // Flag to check if any teacher is found

        while (rs.next()) {
            found = true;
            System.out.println();
            System.out.println("Teacher Name : " + rs.getString(2));
            System.out.println("Teacher Cabin : " + rs.getInt(1));
            System.out.println("Teacher Timings : " + rs.getString(3));
            System.out.println("Teacher Phone number : " + rs.getString(5));
            System.out.println("Teacher Section : " + rs.getString(6));
            System.out.println("Teacher Subject : " + rs.getString(4));
            System.out.println();
        }

        if (!found) {
            System.out.println("No teacher available for the subject: " + subjectToSearch);
        }

    } catch (SQLException e) {
        System.out.println(e);
    }
}




public static void searchTeacherByCabinNumber(Scanner scanner) {
    System.out.println();
    System.out.print("Enter the cabin number to search for a teacher: ");
    int cabinNumberToSearch = scanner.nextInt();

    String sqlquery = "SELECT * FROM FacultyTable WHERE Cabin_No = " + cabinNumberToSearch + ";";
    try {
        ResultSet rs = stmt.executeQuery(sqlquery);

        boolean found = false;  // Flag to check if any teacher is found

        while (rs.next()) {
            found = true;
            System.out.println();
            System.out.println("Teacher Name : " + rs.getString(2));
            System.out.println("Teacher Cabin : " + rs.getInt(1));
            System.out.println("Teacher Timings : " + rs.getString(3));
            System.out.println("Teacher Phone number : " + rs.getString(5));
            System.out.println("Teacher Section : " + rs.getString(6));
            System.out.println("Teacher Subject : " + rs.getString(4));
            System.out.println();
        }

        if (!found) {
            System.out.println("No teacher available for cabin number: " + cabinNumberToSearch);
        }

    } catch (SQLException e) {
        System.out.println(e);
    }
}

public static void searchTeacherBySection(Scanner scanner) {
    System.out.println();
    System.out.print("Enter the section to search for teachers: ");
    String sectionToSearch = scanner.nextLine();

    String sqlquery = "SELECT * FROM FacultyTable WHERE Section = '" + sectionToSearch + "';";
    try {
        ResultSet rs = stmt.executeQuery(sqlquery);

        boolean found = false;  // Flag to check if any teacher is found

        while (rs.next()) {
            found = true;
            System.out.println();
            System.out.println("Teacher Name : " + rs.getString(2));
            System.out.println("Teacher Cabin : " + rs.getInt(1));
            System.out.println("Teacher Timings : " + rs.getString(3));
            System.out.println("Teacher Phone number : " + rs.getString(5));
            System.out.println("Teacher Section : " + rs.getString(6));
            System.out.println("Teacher Subject : " + rs.getString(4));
            System.out.println();
        }

        if (!found) {
            System.out.println("No teacher available for the section: " + sectionToSearch);
        }

    } catch (SQLException e) {
        System.out.println(e);
    }
}



public static void bookSlotWithTeacher(Scanner scanner)
{
System.out.print("Enter the name of the teacher to book a slot: ");
String teacherName = scanner.nextLine();

System.out.print("Enter your name to book a slot: ");
String studentName = scanner.nextLine();

System.out.print("Enter teacher cabin no. : ");
int CabinNo = scanner.nextInt();

bookSlotWithTeacher2(CabinNo,studentName);

String sqlquery = "SELECT * FROM FacultyTable where Section = '%" + teacherName + "%';";
try{
ResultSet rs = stmt.executeQuery(sqlquery);


while(rs.next())
{
            System.out.println();
            System.out.println("Teacher Name : " + rs.getString(2));
            System.out.println("Teacher Cabin : " + rs.getInt(1));
            System.out.println("Teacher Timings : " + rs.getString(3));
            System.out.println("Teacher Phone number : " + rs.getString(5));
            System.out.println("Teacher Section : " + rs.getString(6));
            System.out.println("Teacher Subject : " + rs.getString(4));
            System.out.println();
//System.out.println(rs.getInt(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) + "," + rs.getString(5) + "," + rs.getString(6) + "," + rs.getString(7));
}
}
catch(SQLException e)
{System.out.println(e);}
}


public static void bookSlotWithTeacher2(int cabn, String stuname)
{
String sqlquery = "UPDATE FacultyTable SET Booking_slot = ? WHERE Cabin_No = ?;";
try{
pstmt = con.prepareStatement(sqlquery);

pstmt.setString(1,stuname);
pstmt.setInt(2,cabn);
pstmt.executeUpdate();

}
catch(SQLException e)
{System.out.println(e);}

}



public static void checkSlotBookingStatus(Scanner scanner) {
    System.out.print("Enter your name: ");
    String teacherName = scanner.nextLine();

    String sqlQuery = "SELECT * FROM facultyTable WHERE Name LIKE ? AND Booking_slot = 0";
    try (PreparedStatement pstmt = con.prepareStatement(sqlQuery)) {
        pstmt.setString(1, "%" + teacherName + "%");

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            
            String bookedBy = rs.getString("Booking_slot");
	    System.out.println(); 
            System.out.println("Slot booking status for " + teacherName + ": Booked by " + bookedBy + ".");
	    System.out.println();
        } else {
            System.out.println();
            System.out.println("Slot booking status for " + teacherName + ": No available slots found.");
	    System.out.println();
        }

    } catch (SQLException e) {
        System.out.println(e);
    }
}


    
public static void adminLogin() {
        Scanner scanner = new Scanner(System.in);

        // Define arrays for usernames and passwords
        String[] usernames = {"admin1", "admin2", "admin3", "admin4", "admin5"};
        String[] passwords = {"12345", "abcde", "qwerty", "pass123", "adminPass"};

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Check if entered credentials match any in the arrays
        boolean loginSuccessful = false;
        for (int i = 0; i < usernames.length; i++) {
            if (username.equals(usernames[i]) && password.equals(passwords[i])) {
                loginSuccessful = true;
		String loginby = "Last logged in by - ";
		String loginUser = "Username : " + usernames[i];
		try {
      			FileWriter output = new FileWriter("data2.txt",true);
	 		output.write(loginby + "\n");
			output.write(loginUser + "\n");
			output.write("\n");
			output.close();
		} catch (IOException ex) {
			System.out.println("File append Error...");
		}
                break;
            }
        }

        if (loginSuccessful) {
            System.out.println("Login successful. Welcome, Admin!");
        } else {
            System.out.println("Login failed. Invalid credentials.");
            System.exit(0);
        }
    }


    public static void teacherLogin() {
        Scanner scanner = new Scanner(System.in);

        // Define arrays for teacher usernames and passwords
        String[] teacherUsernames = {"teacher1", "teacher2", "teacher3", "teacher4", "teacher5"};
        String[] teacherPasswords = {"123456", "abcdef", "qwerty", "teacherpass", "securepass"};

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        boolean loginSuccessful = false;
        for (int i = 0; i < teacherUsernames.length; i++) {
            if (username.equals(teacherUsernames[i]) && password.equals(teacherPasswords[i])) {
                loginSuccessful = true;
		String loginby = "Last logged in by - ";
		String loginUser = "Username : " + teacherUsernames[i];
		try {
      			FileWriter output = new FileWriter("data2.txt",true);
	 		output.write(loginby + "\n");
			output.write(loginUser + "\n");
			output.write("\n");
			output.close();
		} catch (IOException ex) {
			System.out.println("File append Error...");
		}		
                break;
            }
        }

        if (loginSuccessful) {
            System.out.println("Login successful. Welcome, Teacher!");
        } else {
            System.out.println("Login failed. Invalid credentials.");
            System.exit(0);
        }
    }


    public static void studentLogin() {
        Scanner scanner = new Scanner(System.in);

        // Define arrays for student usernames and passwords
        String[] studentUsernames = {"student1", "student2", "student3", "student4", "student5"};
        String[] studentPasswords = {"1234", "5678", "abcd", "studentpass", "secure123"};

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Check if entered credentials match any in the arrays
        boolean loginSuccessful = false;
        for (int i = 0; i < studentUsernames.length; i++) {
            if (username.equals(studentUsernames[i]) && password.equals(studentPasswords[i])) {
                loginSuccessful = true;
		String loginby = "Last logged in by - ";
		String loginUser = "Username : " + studentUsernames[i];
		try {
      			FileWriter output = new FileWriter("data2.txt",true);
	 		output.write(loginby + "\n");
			output.write(loginUser + "\n");
			output.write("\n");
			output.close();
		} catch (IOException ex) {
			System.out.println("File append Error...");
		}
                break;
            }
        }

        if (loginSuccessful) {
            System.out.println("Login successful. Welcome, Student!");
        } else {
            System.out.println("Login failed. Invalid credentials.");
            System.exit(0);
        }
    }


    	public static void lastLoginInfo()
	{
		char[] data = new char[100];
        	try {
      			FileReader input = new FileReader("data2.txt");
	 		input.read(data);
			System.out.println(data);
			input.close();
		} catch (IOException ex) {
			System.out.println("File read Error...");
		}


	}

    public static void main(String[] args) {
        initializeDatabase();
 
    }


}