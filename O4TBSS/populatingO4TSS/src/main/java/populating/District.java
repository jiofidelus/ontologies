package populating;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import addIndividuals.IndividualHelper;
import dbConfig.ConnectionConfig;
import helper.Helper;

public class District {



	public static final String ONTOLOGYPATH = "/home/azanzi/workspace/"
			+ "ontologies/O4TBSS/O4TBSS_enriched_Code.owl";

	static String districtIndividual = "";
	static ResultSet queryResult;
	static ConnectionConfig connectionConfig;
	static Connection connection;
	// Populating region data
	public static void populatePatientData() {
		int i=0;

		//Connecting to the database 
		connectionConfig= new ConnectionConfig();
		connection = connectionConfig.getDBConnexion();
		
		//request to get the non rendez-vous patients

		String slqRequest = "SELECT districtsante.id, "
				+ "districtsante.nom_fr FROM public.districtsante";
		
		//Fetching the data from the database
		
		queryResult = connectionConfig.getDataFromDB(connection, slqRequest);
	
	// Browsing the list of ragions and add in the knowledge base

	try {
		while (queryResult.next()) {
			System.out.println("++++++++++++++++++++++++++++++++++++++++"+queryResult.getString("id"));

			//Idifying the region in the ontology by his/her id
			districtIndividual += IndividualHelper.genIndividual
					("#HealthDistrict", queryResult.getString("id"));
			// Add rendez-vous honore
			districtIndividual += IndividualHelper.genDataPropertyAssertion
					("hasName", queryResult.getString("id"), 
							"string", queryResult.getString(("nom_fr")));
					//("hasName", queryResult.getString("id"), queryResult.getString(("nom_fr")));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	//Instances to be added to the ontology
	System.out.println(districtIndividual);
	// Write rendez-vous data to the ontology
//	Helper.writeOntoToFile(ONTOLOGYPATH, regionIndividual);
}

	public static void main(String[] args) {
		populatePatientData();
	}
	
	
}
