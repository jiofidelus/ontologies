package testingPopulatingO4TSS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	
	public static void connect () {
	    try {
	        Class.forName("org.postgresql.Driver");
	    } catch (ClassNotFoundException e) {
	        System.out.println("Where is your MySQL JDBC Driver?");
	        e.printStackTrace();
	        return;
	    }

	    System.out.println("PostgresQL JDBC Driver Registered!");
	    Connection connection = null;

	    try {
	        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/epicam",
	        		"ummisco", "yaounde");

	    } catch (SQLException e) {
	        System.out.println("Connection Failed! Check output console");
	        e.printStackTrace();
	        return;
	    }

	    if (connection != null) {
	        System.out.println("You made it, take control your database now!");
	    } else {
	        System.out.println("Failed to make connection!");
	    }
	}
	
}
