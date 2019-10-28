package test;

import addIndividuals.IndividualHelper;
import helper.Helper;

public class TestHelper {

//	public static final String ONTOLOGYPATH = "/home/azanzi/workspace/"
//			+ "ontologies/O4TBSS/O4TBSS_enriched.owl";

	public static final String ONTOLOGYPATH = "/home/azanzi/workspace/"
			+ "ontologies/O4TBSS/O4TBSS_enriched_Code.owl";

	//For the demo
//	public static final String ONTOLOGYPATH = "/home/azanzi/workspace/"
//			+ "ontologies/O4TBSS/populationDemo.owl";
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Hello boy");
		String patientIndividual="";

//		patientIndividual+=
//				IndividualHelper.genIndividual("http://presence-ontology.org/ontology/OutPatient", "Patient"+5);
//		patientIndividual+=
//				IndividualHelper.genIndividual("http://presence-ontology.org/ontology/OutPatient", "Patient"+6);
//		
//		for (int i = 5; i < 50; i++) {
//			patientIndividual+=
//					IndividualHelper.genIndividual("http://presence-ontology.org/ontology/OutPatient", "PAT"+i);
//		}
		patientIndividual+=
				IndividualHelper.genDataPropertyAssertion("hasAge", "PAT10", "integer", "18");
		
		patientIndividual+=
				IndividualHelper.genDataPropertyAssertion("hasQuater", "PAT10", "string", "Nkolmesseng");
		
		patientIndividual+=
				IndividualHelper.genObjectPropertyAssertion("hasGender", "PAT10", "0");
		
		System.out.println(patientIndividual);
		
		Helper.writeOntoToFile(ONTOLOGYPATH, patientIndividual);
		
		
		
		
	}

}
