package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbConfig.ConnectionConfig;

public class TestDB {
	
	public static void main(String[] args) {
		
		ResultSet queryResult;
		
		ConnectionConfig connectionConfig = new ConnectionConfig();
		System.out.println("Configuring the data base "
		+connectionConfig.getDBConnexion());
		
		 Connection connection = connectionConfig.getDBConnexion();
		
		System.out.println("Testing a request");
		String slqRequest = "SELECT patient.age, patient.quartier, patient.profession, "
				+ "patient.sexe, patient.ville, patient.nationalite, patient.precisionnationalite, "
				+ "castuberculose.antiretroviraux, castuberculose.cotrimoxazole, "
				+ "castuberculose.datedebuttraitement, castuberculose.datefintraitement, "
				+ "castuberculose.devenirmalade, castuberculose.extrapulmonaireprecisions, "
				+ "castuberculose.formemaladie, castuberculose.fumeur, castuberculose.typepatient, "
				+ "castuberculose.typepatientprecisions FROM public.patient, "
				+ "public.castuberculose WHERE patient.id = castuberculose.castuberculosepatient_id";
		connectionConfig.getDataFromDB(connection, slqRequest);
		queryResult = connectionConfig.getDataFromDB(connection, slqRequest);
		
		int i=0;
		try {
			while (queryResult.next() && i < 5) {
				System.out.println("+++++++++The list of patients quaters ++++++++++++++");
				System.out.println(queryResult.getString("fumeur"));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
//List of patients who are not TB cases
//SELECT 
//patient.age, 
//patient.profession, 
//patient.quartier, 
//patient.ville, 
//patient.nationalite, 
//patient.sexe
//FROM 
//public.patient, 
//public.castuberculose
//WHERE 
//castuberculose.castuberculosepatient_id != patient.id;
