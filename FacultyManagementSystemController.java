

import model.FacultyDatabaseModel;
import view.FacultyManagementSystemView;

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


//javac model/FacultyDatabaseModel.java

//javac -cp . view/FacultyManagementSystemView.java

//javac -cp . FacultyManagementSystemController.java

//java -cp mysql-connector-j-8.2.0\mysql-connector-j-8.2.0.jar;. FacultyManagementSystemController

public class FacultyManagementSystemController {

    public static Connection con;
    public static Statement stmt;
    public static PreparedStatement pstmt;
    public static final String DB_URL = "jdbc:mysql://localhost:3306/facultyData";
    public static final String USER = "root";
    public static final String PWD = "Arya@2023";

public static void loadDriver()
{
try{Class.forName("com.mysql.cj.jdbc.Driver");
}
catch(ClassNotFoundException e)
{
System.out.println(e);
}
}

public static Statement createStatement()
{
Statement stmt=null;
try{stmt=con.createStatement();}
catch(SQLException e)
{System.out.println(e);}
return stmt;
}

public static Connection createConnection()
{
Connection con=null;
try{con=DriverManager.getConnection(DB_URL,USER,PWD);
}
catch(SQLException e)
{System.out.println(e);}
return con;
}



    public static void main(String[] args) {
        FacultyDatabaseModel modelMain = new FacultyDatabaseModel();
        FacultyManagementSystemView viewMain = new FacultyManagementSystemView();
        Scanner scanner = new Scanner(System.in);

        int userType;

	FacultyDatabaseModel.initializeDatabase();


	//loadDriver();
	//con =    createConnection();
	//stmt = createStatement();

        while (true) {
            userType = viewMain.showLoginPage(scanner);

            switch (userType) {
                case 1:
                    modelMain.studentLogin();
                    viewMain.searchSubMenu(scanner);
                    break;
                case 2:
                    modelMain.adminLogin();
                    viewMain.runAdminMenu(scanner);
                    break;
                case 3:
                    modelMain.teacherLogin();
                    viewMain.runTeacherMenu(scanner); 
                    break;
                case 4:
                    System.out.println("Exiting Faculty Management System.");
                    return;
                default:
                    System.out.println("Invalid user type. Please try again.");
            }
        }
    }
}