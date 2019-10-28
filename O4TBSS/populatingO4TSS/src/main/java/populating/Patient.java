package populating;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import addIndividuals.IndividualHelper;
import dbConfig.ConnectionConfig;
import helper.Helper;

public class Patient {

	public static final String ONTOLOGYPATH = "/home/azanzi/workspace/"
			+ "ontologies/O4TBSS/O4TBSS_enriched_Code.owl";

	static String patientIndividual = "";
	static ResultSet queryResult;
	static ConnectionConfig connectionConfig;
	static Connection connection;
	// Populating patient data
	public static void populatePatientData() {
		int i=0;

		//Connecting to the database 
		connectionConfig= new ConnectionConfig();
		connection = connectionConfig.getDBConnexion();
		
//		SELECT 
//		  castuberculose.id, 
//		  castuberculose.devenirmalade, 
//		  castuberculose.castuberculosepatient_id, 
//		  centrespatientcentrediagtrait.centrediagtrait_id, 
//		  centrespatientcentrediagtrait.patient_id
//		FROM 
//		  public.castuberculose, 
//		  public.patient, 
//		  public.centrespatientcentrediagtrait
//		WHERE 
//		  patient.id = centrespatientcentrediagtrait.patient_id AND
//		   = ;

		
		//request to get the non TB patients
		String slqRequest = "SELECT DISTINCT patient.id, age, profession, quartier, sexe, ville, "
				+ "nationalite, centrespatientcentrediagtrait.centrediagtrait_id "
				+ " FROM public.castuberculose, public.patient, public.centrespatientcentrediagtrait"
				+ " WHERE patient.id !=  castuberculose.castuberculosepatient_id AND"
				+ " patient.id = centrespatientcentrediagtrait.patient_id LIMIT 12";
//		String slqRequest = "SELECT DISTINCT patient.id, age, profession, quartier, sexe, ville, nationalite "
//				+ "FROM public.examenbiologique, public.patient WHERE "
//				+ "patient.id = examenbiologique.examensbiologiquespatient_id LIMIT 50";
		//Fetching the data from the database
		
		queryResult = connectionConfig.getDataFromDB(connection, slqRequest);
	
	// Browsing the list of patients and add in the knowledge base
	try {
		while (queryResult.next()) {
			System.out.println("++++++++++++++++++++++++++++++++++++++++"+queryResult.getString("id"));

			//Idifying the patient in the ontology by his/her id
			patientIndividual += IndividualHelper.genIndividual
					("http://presence-ontology.org/ontology/Patient", queryResult.getString("id"));
			// Add patient age
			patientIndividual += IndividualHelper.genDataPropertyAssertion
					("hasAge", queryResult.getString("id"), "integer", ""+queryResult.getInt("age"));
			// Add patient sex
			patientIndividual += IndividualHelper.genObjectPropertyAssertion
					("hasGender", queryResult.getString("id"), queryResult.getString(("sexe")));
			// Add patient quater
			patientIndividual += IndividualHelper.genDataPropertyAssertion
					("hasQuater", queryResult.getString("id"), "string", queryResult.getString(("quartier")));
//			System.out.println(patientIndividual);
			

			patientIndividual += IndividualHelper.genObjectPropertyAssertion
					("isAssociatedWith", queryResult.getString("id"), 
							queryResult.getString(("centrediagtrait_id")));
			
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	//Instances to be added to the ontology
	System.out.println(patientIndividual);
	// Write patient data to the ontology
//	Helper.writeOntoToFile(ONTOLOGYPATH, patientIndividual);
}

	//Testing the population of patient data

	public static void testingPopulatePatientData() {
		int i=0;
		connectionConfig= new ConnectionConfig();
		connection = connectionConfig.getDBConnexion();
		
		//request to get the non TB patients
		String slqRequest = "SELECT DISTINCT patient.id, age, profession, quartier, sexe, ville, nationalite "
				+ "FROM public.castuberculose, public.patient WHERE patient.id != "
				+ "castuberculose.castuberculosepatient_id LIMIT 12";
		
		queryResult = connectionConfig.getDataFromDB(connection, slqRequest);
	
	// Browsing the list of patients and add in the knowledge base
	try {
		while (queryResult.next()) {
			//Idifying the patient in the ontology by his/her id
			patientIndividual += IndividualHelper.genIndividual
					("http://presence-ontology.org/ontology/Patient", 
							queryResult.getString("id"));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	System.out.println(patientIndividual);
}
	
	public static void main(String[] args) {
//		testingPopulatePatientData();
		populatePatientData();
	}

}