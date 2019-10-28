package populating;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import addIndividuals.IndividualHelper;
import dbConfig.ConnectionConfig;
import helper.Helper;

public class TBDiagnosisAndTreatment {



	public static final String ONTOLOGYPATH = "/home/azanzi/workspace/"
			+ "ontologies/O4TBSS/O4TBSS_enriched_Code.owl";

	static String tbDiagnosisAndTreatmentIndividual = "";
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

		String slqRequest = "select centrediagtrait.id, centrediagtrait.nom, centrediagtrait.quartier,"
				+ " centrediagtrait.ville, centrediagtrait.region_id, "
				+ "centrediagtrait.districtsante_id FROM public.centrediagtrait";
				
//				"centrediagtrait.id, "
//				+ "centrediagtrait.region_id, centrediagtrait.districtsante_id,"
//				+ "centrediagtrait.nom, centrediagtrait.quartier,"
//				+ "centrediagtrait.ville FROM public.centrediagtrait";
//		SELECT 
//		  centrediagtrait.id, 
//		  centrediagtrait.nom, 
//		  centrediagtrait.quartier, 
//		  centrediagtrait.ville, 
//		  centrediagtrait.region_id, 
//		  centrediagtrait.districtsante_id
//		FROM 
//		  public.centrediagtrait;

		//Fetching the data from the database
		
		queryResult = connectionConfig.getDataFromDB(connection, slqRequest);
	
	// Browsing the list of ragions and add in the knowledge base
		
		System.out.println(" ---"+queryResult);

	try {
		while (queryResult.next()) {
			System.out.println("++++++++++++++++++++++++++++++++++++++++"+queryResult.getString("id"));

			//Idifying the region in the ontology by his/her id
			tbDiagnosisAndTreatmentIndividual += IndividualHelper.genIndividual
					("#TBDiagnosisAndTreatment", queryResult.getString("id"));
			
			tbDiagnosisAndTreatmentIndividual += IndividualHelper.genDataPropertyAssertion
					("hasName", queryResult.getString("id"), 
							"string", queryResult.getString(("nom")));

			tbDiagnosisAndTreatmentIndividual += IndividualHelper.genDataPropertyAssertion
					("hasQuater", queryResult.getString("id"), 
							"string", queryResult.getString(("quartier")));

			tbDiagnosisAndTreatmentIndividual += IndividualHelper.genDataPropertyAssertion
					("hasTown", queryResult.getString("id"), 
							"string", queryResult.getString(("ville")));


			tbDiagnosisAndTreatmentIndividual += IndividualHelper.genObjectPropertyAssertion
					("isAssociatedWith", queryResult.getString("id"), 
							queryResult.getString(("region_id")));


			tbDiagnosisAndTreatmentIndividual += IndividualHelper.genObjectPropertyAssertion
					("isAssociatedWith", queryResult.getString("id"), 
							queryResult.getString(("districtsante_id")));
			
			
			//("hasName", queryResult.getString("id"), queryResult.getString(("nom_fr")));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	//Instances to be added to the ontology
	System.out.println(tbDiagnosisAndTreatmentIndividual);
	// Write rendez-vous data to the ontology
//	Helper.writeOntoToFile(ONTOLOGYPATH, regionIndividual);
}

	public static void main(String[] args) {
		populatePatientData();
	}
	
	
}
