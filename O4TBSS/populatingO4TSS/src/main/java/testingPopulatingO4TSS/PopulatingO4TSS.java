package testingPopulatingO4TSS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PopulatingO4TSS {

	public static void main(String[] args) {

		   
		
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
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/epicam", "ummisco", "yaounde");
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

		
		//// Request the database
		try {
			Statement statement = connection.createStatement ();
			ResultSet queryResult;
//			PreparedStatement stmPatients1 = connection.prepareStatement("select * from Patient");
//			ResultSet resultSet1 = stmPatients1.executeQuery();
////			PreparedStatement stmPatients2 = connection.prepareStatement
////					("select age, quartier, profession, sexe, ville, nationalite, from Patient");
////			ResultSet resultSet2 = stmPatients2.executeQuery();
			int i = 0;
//			while (resultSet1.next() && i < 5) {
//				System.out.println("+++++++++The list of patients++++++++++++++");
//				System.out.println(resultSet1.getString("nom"));
//				i++;
//			}
			i=0;
			PreparedStatement stmPatients2 = 
					connection.prepareStatement("select age, quartier, profession, sexe, ville, nationalite from Patient");
			ResultSet resultSet2 = stmPatients2.executeQuery();
			while (resultSet2.next() && i < 5) {
				System.out.println("+++++++++The list of patients quaters ++++++++++++++");
				System.out.println(resultSet2.getString("quartier"));
				i++;
			}
			
			///SQL joined request
			String sql = "SELECT patient.age, patient.quartier, patient.profession, "
					+ "patient.sexe, patient.ville, patient.nationalite, patient.precisionnationalite, "
					+ "castuberculose.antiretroviraux, castuberculose.cotrimoxazole, "
					+ "castuberculose.datedebuttraitement, castuberculose.datefintraitement, "
					+ "castuberculose.devenirmalade, castuberculose.extrapulmonaireprecisions, "
					+ "castuberculose.formemaladie, castuberculose.fumeur, castuberculose.typepatient, "
					+ "castuberculose.typepatientprecisions FROM public.patient, "
					+ "public.castuberculose WHERE patient.id = castuberculose.castuberculosepatient_id";
			queryResult = statement.executeQuery(sql);
			i=0;
			while (queryResult.next() && i < 5) {
				System.out.println("+++++++++The list of patients quaters ++++++++++++++");
				System.out.println(queryResult.getString("fumeur"));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
