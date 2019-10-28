package populating;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import addIndividuals.IndividualHelper;
import dbConfig.ConnectionConfig;
import helper.Helper;

public class CasTuberculose {

//	public static final String 
//	ONTOLOGYPATH = "/home/azanzi/workspace/" + 
//	"ontologies/O4TBSS/O4TBSS_enriched_Code.owl";
	
	public static final String 
	ONTOLOGYPATHTEST = "/home/azanzi/"
			+ "workspace/ontologies/O4TBSS/instances/tbCase";

	static String tbIndividual = "";
	static ResultSet queryResult;
	static ConnectionConfig connectionConfig;
	static Connection connection;

	// Populating TB data
	public static void populateTBData() {
		int i = 0;

		// Connecting to the database
		connectionConfig = new ConnectionConfig();
		connection = connectionConfig.getDBConnexion();

		// request to get the non TB patients
		String slqRequest = "SELECT  DISTINCT castuberculose.id, "
				+ "castuberculose.devenirmalade, castuberculose.formemaladie, "
				+ "castuberculose.castuberculosepatient_id,"
				+ "castuberculose.typepatient FROM public.castuberculose limit 88";

		// Fetching the data from the database

		queryResult = connectionConfig.getDataFromDB(connection, slqRequest);

		// Browsing the list of patients and add in the knowledge base
		try {
			while (queryResult.next()) {
				System.out.println("++++++++++++++++++++++++++++++++++++++++" +
			queryResult.getString("id"));

				// Idifying the patient in the ontology by his/her id
				tbIndividual += IndividualHelper.genIndividual("#TBPatient",
						queryResult.getString("id"));
				// Add patient typepatient
				String typePatient = "";
				if(queryResult.getString("typepatient")!=null) {
				switch (queryResult.getString("typepatient")) {
				case "0":
					typePatient = "New case";
					break;
				case "1":
					typePatient = "Recovery after abandon";
					break;
				case "2":
					typePatient = "Treatment failed";
					break;
				case "3":
					typePatient = "Relapse";
					break;
				case "4":
					typePatient = "Other";
					break;
				default:
					break;
				}
				}
				tbIndividual += IndividualHelper.genDataPropertyAssertion
						("hasType", queryResult.getString("id"), "string", typePatient);
				// Add patient formemaladie
				String formemaladie = "";
				if(queryResult.getString("formemaladie")!=null) {
				switch (queryResult.getString("formemaladie")) {
				case "0":
					formemaladie = "tpm+";
					break;
				case "1":
					formemaladie = "tpm-";
					break;
				case "2":
					formemaladie = "tep";
					break;
				default:
					break;
				}
				}
				tbIndividual += IndividualHelper.genDataPropertyAssertion("hasForm", 
						queryResult.getString("id"), "string", formemaladie);
				// Add patient formemaladie
				String devenirmalade = "";
				if(queryResult.getString("devenirmalade")!=null) {
				switch (queryResult.getString("devenirmalade")) {
				case "0":
					devenirmalade = "Recovered";
					break;
				case "1":
					devenirmalade = "Treatment finished";
					break;
				case "2":
					devenirmalade = "Treatment failed";
					break;
				case "3":
					devenirmalade = "Deceased";
					break;
				case "4":
					devenirmalade = "LostSight";
					break;
				case "5":
					devenirmalade = "StoppedByPrescriber";
					break;
				case "6":
					devenirmalade = "StoppedCauseIndesirableEffect";
					break;
				case "7":
					devenirmalade = "StoppedOccurenceTB";
					break;
				default:
					break;
				}}
				tbIndividual += IndividualHelper.genDataPropertyAssertion
						("hasOutcome", queryResult.getString("id"), "string", devenirmalade);
				
				System.out.println("++++++++++++++++++"+tbIndividual);
				tbIndividual += IndividualHelper.genObjectPropertyAssertion
						("isAssociatedWith", queryResult.getString("castuberculosepatient_id"), 
								queryResult.getString("id"));
				
				// System.out.println(patientIndividual);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Instances to be added to the ontology
		System.out.println(tbIndividual);
		// Write patient data to the ontology
//		Helper.writeOntoToFile(ONTOLOGYPATH, tbIndividual);
	}

	//testing Populate TB data
	// Populating patient data
	public static void testPopulateTBData() {
		int i = 0;

		// Connecting to the database
		connectionConfig = new ConnectionConfig();
		connection = connectionConfig.getDBConnexion();

		// request to get the non TB patients
		String slqRequest = "SELECT  DISTINCT castuberculose.id, "
				+ "castuberculose.devenirmalade, castuberculose.formemaladie, "
				+ "castuberculose.typepatient FROM public.castuberculose limit 88";

		// Fetching the data from the database

		queryResult = connectionConfig.getDataFromDB(connection, slqRequest);

		// Browsing the list of patients and add in the knowledge base
		try {
			while (queryResult.next()) {

				// Idifying the patient in the ontology by his/her id
				tbIndividual += IndividualHelper.genIndividual("#TBPatient",
						queryResult.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(tbIndividual);
	}
	
	//Testing population TB data with rendez-vous

	// Populating TB data
	public static void testTBRDVpopulateTBData() {
		int i = 0;

		// Connecting to the database
		connectionConfig = new ConnectionConfig();
		connection = connectionConfig.getDBConnexion();

		// request to get the non TB patients
		String slqRequest = "SELECT  DISTINCT castuberculose.id,"
				+ "castuberculose.devenirmalade, castuberculose.formemaladie, "
				+ "castuberculose.typepatient, castuberculose.castuberculosepatient_id,"
				+ "centrespatientcentrediagtrait.centrediagtrait_id "
				+ " FROM public.castuberculose, public.centrespatientcentrediagtrait "
				+ " where castuberculose.castuberculosepatient_id = "
				+ "centrespatientcentrediagtrait.patient_id limit 50";

//		// request to get the non TB patients
//		String slqRequest = "SELECT  DISTINCT castuberculose.id as tbID, rendezvous.id "
//				+ "as rdvID, rendezvous.honore,"
//				+ "castuberculose.devenirmalade, castuberculose.formemaladie, "
//				+ "castuberculose.typepatient, castuberculose.castuberculosepatient_id"
//				+ "FROM public.castuberculose, public.rendezvous "
//				+ "where rendezvous.rendezvouscastuberculose_id=castuberculose.id limit 50";

		queryResult = connectionConfig.getDataFromDB(connection, slqRequest);

		try {
			while (queryResult.next()) {
				
				//***************************Add the appointments***************************
				// Idifying the patient in the ontology by his/her id
				tbIndividual += IndividualHelper.genIndividual("#TBCase",
						queryResult.getString("id"));
				// Add patient typepatient
				String typePatient = "";
				if(queryResult.getString("typepatient")!=null) {
				switch (queryResult.getString("typepatient")) {
				case "0":
					typePatient = "New case";
					break;
				case "1":
					typePatient = "Recovery after abandon";
					break;
				case "2":
					typePatient = "Treatment failed";
					break;
				case "3":
					typePatient = "Relapse";
					break;
				case "4":
					typePatient = "Other";
					break;
				default:
					break;
				}
				}
				tbIndividual += IndividualHelper.genDataPropertyAssertion
						("hasType", queryResult.getString("id"), "string", typePatient);
				// Add patient formemaladie
				String formemaladie = "";
				if(queryResult.getString("formemaladie")!=null) {
				switch (queryResult.getString("formemaladie")) {
				case "0":
					formemaladie = "tpm+";
					break;
				case "1":
					formemaladie = "tpm-";
					break;
				case "2":
					formemaladie = "tep";
					break;
				default:
					break;
				}
				}
				tbIndividual += IndividualHelper.genDataPropertyAssertion("hasForm", 
						queryResult.getString("id"), "string", formemaladie);
				// Add patient formemaladie
				String devenirmalade = "";
				if(queryResult.getString("devenirmalade")!=null) {
				switch (queryResult.getString("devenirmalade")) {
				case "0":
					devenirmalade = "Recovered";
					break;
				case "1":
					devenirmalade = "Treatment finished";
					break;
				case "2":
					devenirmalade = "Treatment failed";
					break;
				case "3":
					devenirmalade = "Deceased";
					break;
				case "4":
					devenirmalade = "LostSight";
					break;
				case "5":
					devenirmalade = "StoppedByPrescriber";
					break;
				case "6":
					devenirmalade = "StoppedCauseIndesirableEffect";
					break;
				case "7":
					devenirmalade = "StoppedOccurenceTB";
					break;
				default:
					break;
				}}

//				// Add rendez-vous honore
//				tbIndividual += IndividualHelper.genDataPropertyAssertion
//						("hasHonored", queryResult.getString("id"), "string", 
//								queryResult.getString(("honore")));
//				
//				tbIndividual += IndividualHelper.genDataPropertyAssertion
//						("hasOutcome", ""+queryResult.getString("tbID"), "string", devenirmalade);

//				//***************************Add the appointments***************************
//				//Idifying the appointment in the ontology by his/her id
//				tbIndividual += IndividualHelper.genIndividual
//						("#PatientAppointment", queryResult.getString("rdvID"));
//				// Add rendez-vous honore
//				tbIndividual += IndividualHelper.genDataPropertyAssertion
//						("hasHonored", queryResult.getString("rdvID"), "string", 
//								queryResult.getString(("honore")));
				// Link to TB case
				
				tbIndividual += IndividualHelper.genObjectPropertyAssertion
						("isAssociatedWith", queryResult.getString("centrediagtrait_id"), 
								queryResult.getString("id"));

				tbIndividual +=IndividualHelper.genIndividual
				("http://presence-ontology.org/ontology/Patient", 
						queryResult.getString("castuberculosepatient_id"));
				
				tbIndividual += IndividualHelper.genObjectPropertyAssertion
						("isAssociatedWith", queryResult.getString("castuberculosepatient_id"), 
								queryResult.getString("id"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(tbIndividual);
//		Helper.writeOntoToFile(ONTOLOGYPATHTEST, tbIndividual);
	}

	
	
	
	//Testing TB data
	public static void main(String[] args) {
//		testPopulateTBData();
		testTBRDVpopulateTBData();
	}
	
}