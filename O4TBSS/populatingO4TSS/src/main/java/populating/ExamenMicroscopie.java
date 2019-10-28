package populating;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import addIndividuals.IndividualHelper;
import dbConfig.ConnectionConfig;
import helper.Helper;

public class ExamenMicroscopie {

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
		
		//request to get the non TB patients
		String slqRequest = "SELECT DISTINCT patient.id, age, profession, quartier, sexe, ville, nationalite "
				+ "FROM public.castuberculose, public.patient WHERE patient.id != "
				+ "castuberculose.castuberculosepatient_id LIMIT 500";
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
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	//Instances to be added to the ontology
	System.out.println(patientIndividual);
	// Write patient data to the ontology
	Helper.writeOntoToFile(ONTOLOGYPATH, patientIndividual);
}

}