package addIndividuals;

public class IndividualHelper {
	
	/**
	 * Creates an individual given a class
	 * @param classIRI
	 * @param individualIRI
	 * @return
	 */
	public static String genIndividual(final String classIRI, final String individualIRI) {
		String individual2Add = "";
		// Add the individual declaration
		individual2Add = (individual2Add + "\n<Declaration>");
		individual2Add = (((((individual2Add + "\n\t <NamedIndividual IRI=") + "\"#") + individualIRI) + "\"") + "/>");
		individual2Add = (individual2Add + "\n</Declaration>");
		// Add class assertion
		individual2Add = (individual2Add + "<ClassAssertion>");
		individual2Add = (((((individual2Add + "\n\t <Class IRI=") + "\"") + classIRI) + "\"") + "/>");
		individual2Add = (((((individual2Add + "\n\t <NamedIndividual IRI=") + "\"#") + individualIRI) + "\"") + "/>");
		individual2Add = (individual2Add + "</ClassAssertion>");

		return individual2Add;
	}

	/**
	 * Add a datatype to an individual
	 * @param dataPropIRI
	 * @param namedIndIRI
	 * @param dataType
	 * @param data
	 * @return
	 */
	public static String genDataPropertyAssertion(final String dataPropIRI, final String namedIndIRI,
			final String dataType, String data) {
		String dataPropAssertion = "";

		dataPropAssertion = (dataPropAssertion + "<DataPropertyAssertion>");
		dataPropAssertion = (((((dataPropAssertion + "\n\t <DataProperty IRI=") + "\"#") + dataPropIRI) + "\"") + "/>");
		dataPropAssertion = (((((dataPropAssertion + "\n\t <NamedIndividual IRI=") + "\"#") + namedIndIRI) + "\"") + "/>");
		dataPropAssertion = (((((dataPropAssertion + 
				"\n\t <Literal datatypeIRI=") + 
				"\"http://www.w3.org/2001/XMLSchema#") +dataType+"\">") +data+ "</Literal>"));
		dataPropAssertion = (dataPropAssertion + "</DataPropertyAssertion>");
		
		return dataPropAssertion;
	}

	/**
	 * Add a data property to an individual
	 * @param objectPropIRI
	 * @param namedIndIRIStart
	 * @param namedIndIRIEnd
	 * @param dataType
	 * @param data
	 * @return
	 */
	public static String genObjectPropertyAssertion(final String objectPropIRI, 
			final String namedIndIRIStart, final String namedIndIRIEnd) {
		String objectPropAssertion = "";

		objectPropAssertion = (objectPropAssertion + "<ObjectPropertyAssertion>");
		objectPropAssertion = (((((objectPropAssertion + "\n\t <ObjectProperty IRI=") + "\"#") + objectPropIRI) + "\"") + "/>");
		objectPropAssertion = (((((objectPropAssertion + "\n\t <NamedIndividual IRI=") + "\"#") + namedIndIRIStart) + "\"") + "/>");
		objectPropAssertion = (((((objectPropAssertion + "\n\t <NamedIndividual IRI=") + "\"#") + namedIndIRIEnd) + "\"") + "/>");
		objectPropAssertion = (objectPropAssertion + "</ObjectPropertyAssertion>");
		
		return objectPropAssertion;
	}
}


///////////////////////////////////////////////////////
//public static String genIndividual(final String classIRI, final String individualIRI) {
//	String individual2Add = "";
//	// Add the individual declaration
//	individual2Add = (individual2Add + "\n<Declaration>");
//	individual2Add = (((((individual2Add + "\n\t <NamedIndividual IRI=") + "\"#") + individualIRI) + "\"") + "/>");
//	individual2Add = (individual2Add + "\n</Declaration>");
//	// Add class assertion
//	individual2Add = (individual2Add + "<ClassAssertion>");
//	individual2Add = (((((individual2Add + "\n\t <Class IRI=") + "\"#") + classIRI) + "\"") + "/>");
//	individual2Add = (((((individual2Add + "\n\t <NamedIndividual IRI=") + "\"#") + individualIRI) + "\"") + "/>");
//	individual2Add = (individual2Add + "</ClassAssertion>");
//
//	return individual2Add;
//}

//public static String genDataPropertyAssertion(final String dataPropIRI, final String namedIndIRI,
//		final String dataType, String data) {
//	String dataPropAssertion = "";
//
//	dataPropAssertion = (dataPropAssertion + "<DataPropertyAssertion>");
//	dataPropAssertion = (((((dataPropAssertion + "\n\t <DataProperty IRI=") + "\"#") + dataPropIRI) + "\"") + "/>");
//	dataPropAssertion = (((((dataPropAssertion + "\n\t <NamedIndividual IRI=") + "\"#") + namedIndIRI) + "\"") + "/>");
//	dataPropAssertion = (((((dataPropAssertion + 
//			"\n\t <Literal datatypeIRI=") + 
//			"\"http://www.w3.org/2001/XMLSchema#") +dataType+"\">") +data+ "</Literal>"));
//	dataPropAssertion = (dataPropAssertion + "</DataPropertyAssertion>");
//	
//	return dataPropAssertion;
//}

//public static String genObjectPropertyAssertion(final String objectPropIRI, 
//		final String namedIndIRIStart, final String namedIndIRIEnd) {
//	String objectPropAssertion = "";
//
//	objectPropAssertion = (objectPropAssertion + "<ObjectPropertyAssertion>");
//	objectPropAssertion = (((((objectPropAssertion + "\n\t <ObjectProperty IRI=") + "\"#") + objectPropIRI) + "\"") + "/>");
//	objectPropAssertion = (((((objectPropAssertion + "\n\t <NamedIndividual IRI=") + "\"#") + namedIndIRIStart) + "\"") + "/>");
//	objectPropAssertion = (((((objectPropAssertion + "\n\t <NamedIndividual IRI=") + "\"#") + namedIndIRIEnd) + "\"") + "/>");
//	objectPropAssertion = (objectPropAssertion + "</ObjectPropertyAssertion>");
//	
//	return objectPropAssertion;
//}