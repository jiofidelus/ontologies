package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import addIndividuals.IndividualHelper;
import dbConfig.ConnectionConfig;

public class Populating {
	
	static final String ontologyPath="/home/azanzi/"
			+ "workspace/ontologies/source2onto/knowledge2OntoDemo.owl";
	
	public static void main(String[] args) {
		String patientIndividual="";
		String regionindividuals="";
		IndividualHelper individualHelper = new IndividualHelper();
		ConnectionConfig connectionConfig = new ConnectionConfig();
		ResultSet queryResult=null;
		String slqQuery = "select * from patient";
		
		Connection connection = connectionConfig.getDBConnexion();
		queryResult = connectionConfig.getDataFromDB(connection, slqQuery);
		
		System.out.println("Starting the population of the ontology");
		
		int i=0;
		try {
			while (queryResult.next() && i < 5) {
				patientIndividual+=individualHelper.
						genIndividual("Patient", queryResult.getString("nom"));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String regionIndividuals="";
		i=1;
		queryResult = connectionConfig.getDataFromDB(connection, "select * from region");
		try {
			while (queryResult.next()) {
				regionIndividuals+=individualHelper.
						genIndividual("Region", "REG"+i);
				regionIndividuals+=individualHelper.genDataPropertyAssertion("name",
						"REG"+i, "string", queryResult.getString("nom_fr"));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String objectProp="";
		//Le premier patient est soigné dans la première région
		objectProp+=individualHelper.genObjectPropertyAssertion("isTreatedIn", 
				"PAT1", "REG1");
		
		System.out.println("The individuals generated\\");
//		System.out.println(patientIndividual);
//		System.out.println(regionIndividuals);
		System.out.println(objectProp);
	}

}
///home/azanzi/workspace/ontologies/source2onto/knowledge2OntoDemo.owl