package dbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.Statement;

public class ConnectionConfig {
	
	/**
	 * Connect to the database
	 * @return
	 */
	
	public Connection getDBConnexion() {
		Connection connection = null;

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Connection Failed! Bad configuration of the DB driver");
			e.printStackTrace();
		}

		System.out.println("PostgresQL JDBC Driver Registered!");

		try {
			connection = DriverManager.getConnection
					("jdbc:postgresql://localhost:5432/epicam", "ummisco", "yaounde");
		} catch (SQLException e) {
			System.out.println("Connection Failed! connexion path, DB user or password incorrect");
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public ResultSet getDataFromDB (Connection connection, String slqRequest) {
		ResultSet queryResult=null;

		try {
			Statement statement = connection.createStatement ();
			System.out.println(".........Executing the SQL request...................");
			System.out.println(slqRequest);
			queryResult = statement.executeQuery(slqRequest);
			System.out.println(".........The SQL request executed...................");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return queryResult;
	}

}
