package populating;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import addIndividuals.IndividualHelper;
import dbConfig.ConnectionConfig;
import helper.Helper;

public class RendezVous {

	public static final String ONTOLOGYPATH = "/home/azanzi/workspace/"
			+ "ontologies/O4TBSS/O4TBSS_enriched_Code.owl";

	static String appointmentIndividual = "";
	static ResultSet queryResult;
	static ConnectionConfig connectionConfig;
	static Connection connection;
	// Populating rendez-vous data
	public static void populatePatientData() {
		int i=0;

		//Connecting to the database 
		connectionConfig= new ConnectionConfig();
		connection = connectionConfig.getDBConnexion();
		
		//request to get the non rendez-vous patients
		
		String slqRequest = "SELECT rendezvous.id, rendezvous.honore, "
				+ "rendezvous.rendezvouscastuberculose_id FROM public.rendezvous LIMIT 200";
//				+ "WHERE rendezvous.rendezvouscastuberculose_id = castuberculose.id";
//				+ "LIMIT 500";

		//Fetching the data from the database
		
		queryResult = connectionConfig.getDataFromDB(connection, slqRequest);
	
	// Browsing the list of patients and add in the knowledge base
		System.out.println("..........................Query result: ");
		System.out.println(queryResult);
	try {
		while (queryResult.next()) {
			System.out.println("++++++++++++++++++++++++++++++++++++++++"+queryResult.getString("id"));
			System.out.println("++++++++++++++++++++++++++++++++++++++++"+queryResult.getString("rendezvouscastuberculose_id"));

			//Idifying the appointment in the ontology by his/her id
			appointmentIndividual += IndividualHelper.genIndividual
					("#PatientAppointment", queryResult.getString("id"));
			// Add rendez-vous honore
			appointmentIndividual += IndividualHelper.genDataPropertyAssertion
					("hasHonored", queryResult.getString("id"), "string", queryResult.getString(("honore")));
			// Link to TB case
			
			appointmentIndividual += IndividualHelper.genObjectPropertyAssertion
					("isAssociatedWith", queryResult.getString("id"), 
							queryResult.getString(("rendezvouscastuberculose_id")));
//			System.out.println(patientIndividual);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	//Instances to be added to the ontology
	System.out.println(appointmentIndividual);
	// Write rendez-vous data to the ontology
//	Helper.writeOntoToFile(ONTOLOGYPATH, appointmentIndividual);
}

}