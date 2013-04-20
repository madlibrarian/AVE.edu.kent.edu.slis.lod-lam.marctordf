package march22;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class iterationsmain {
	
	
	
	//5
	public static void printStatementsByResourceQueryLocForLinkedDataInfoAndPublishRdfDocumentFile() {
		Model model = ModelFactory.createDefaultModel();
		UtilityClass.readRdfInputFile(model, inputFileName);

		//Property p1 = model.getProperty(ns02, "creator");
		Property p2 = model.getProperty(ns02, "subject");
		List<Statement> listofsubjectstatesments = new ArrayList<Statement>();

		ResIterator resiter = model.listSubjects();
		try {
			while (resiter.hasNext()) {
				countofresources++;
				Resource resoustuff = resiter.nextResource();
				StmtIterator st = resoustuff.listProperties();
				while (st.hasNext()) {
					countofstatement++;
					Statement stat2 = st.nextStatement();
					System.out
							.println("Statement Temp ID: " + countofstatement);
					Resource subject = stat2.getSubject(); // get the subject
					Property predicate = stat2.getPredicate(); // get the
					RDFNode object = stat2.getObject(); // get the object
					
					
					UtilityClass.consolePrintSubjectPredicateObject(subject, predicate, object);

					// ////////////////////////////////////////////////////////////////////////
					if (stat2.getPredicate().equals(p2)) {
						
						String urlfromconvert = UtilityClass.queryLocForSubjectLinkedUrl(object);

						try {
							Statement jjl = model.createStatement(resoustuff,
									p2, urlfromconvert);

							listofsubjectstatesments.add(jjl);

						} catch (Exception e) {
							System.out.println("ERROR IN ADDING: "+ e.getMessage());
						}
					}
					/////////////////////////////////////////////////////////////////////////////
					
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		}

		try {
			model.add(listofsubjectstatesments);
			UtilityClass.printRdfToConsole(model);

		} catch (Exception e) {

			System.out.println("ERROR 101: ADDING LISTOFSTATEMENTS TO MODEL - "	+ e.getMessage());
		}

		UtilityClass.publishRDFToDisk(model, outputFileName);

		System.out.println("Total # of Statements: " + countofstatement);
		System.out.println("Total Number of Resources: " + countofresources);
	}

	//4
	public static void loadmodelAddPropertySubjectTessstPrintToConsole() {
		Model model = ModelFactory.createDefaultModel();
		UtilityClass.readRdfInputFile(model, inputFileName);
		Property p2 = model.getProperty("http://purl.org/dc/elements/1.1/",
				"subject");

		List<Statement> listofstatesments = new ArrayList<Statement>();

		// ResIterator resiter = model.listResourcesWithProperty(p2);
		ResIterator resiter = model.listSubjects();

		try {
			while (resiter.hasNext()) {
				Resource resoustuff = resiter.nextResource();
				// System.out.println(resoustuff.getLocalName().toString());

				StmtIterator st = resoustuff.listProperties();

				while (st.hasNext()) {

					Statement stat2 = st.nextStatement();

					countofstatement++;
					System.out
							.println("Statement Temp ID: " + countofstatement);

					Resource subject = stat2.getSubject(); // get the subject
					Property predicate = stat2.getPredicate(); // get the
																// predicate
					RDFNode object = stat2.getObject(); // get the object

					System.out.println("RESOURCE : " + subject.toString());
					// String locwebcall = LocWebCall.try03(object);
					// System.out.println(locwebcall);

					System.out.println(" PROPERTY : " + predicate.toString()
							+ " ");

					System.out.print("OBJECT");

					if (object instanceof Resource) {
						System.out.print(object.toString());

					} else {
						// object is a literal
						System.out.print(" \"" + object.toString() + "\"");
					}
					System.out.println(" .");

					if (stat2.getPredicate().equals(p2)) {
						// resoustuff.addProperty(p2, "testtttttttt");

						try {
							Statement jjl = model.createStatement(resoustuff,
									p2, "tessttt");
							// model.add(jjl);
							// model.commit();
							listofstatesments.add(jjl);

						} catch (Exception e) {
							// TODO: handle exception

							System.out.println("ERROR IN ADDING: "
									+ e.getMessage());
						}

					}

				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		}

		try {
			model.add(listofstatesments);
			UtilityClass.printRdfToConsole(model);

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("ERROR 101: ADDING LISTOFSTATEMENTS TO MODEL - "
					+ e.getMessage());
		}

		System.out.println("Total: " + countofstatement);
	}

	//3
	public static void listAllStatementsWithDCSubjectAsProperty() {
		Model model = ModelFactory.createDefaultModel();
		UtilityClass.readRdfInputFile(model, inputFileName);
		Property p2 = model.getProperty("http://purl.org/dc/elements/1.1/",
				"subject");
		StmtIterator iter = model.listStatements();
		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement();

			if (stmt.getPredicate().equals(p2)) {
				countofstatement++;
				System.out.println("Statement Temp ID: " + countofstatement);
				Resource subject = stmt.getSubject(); // get the subject
				Property predicate = stmt.getPredicate(); // get the predicate
				RDFNode object = stmt.getObject(); // get the object
				System.out.print("SUBJECT : " + subject.toString());
				String locwebcall = UtilityClass.try03(object);
				System.out.println(locwebcall);
				System.out.print(" Property : " + predicate.toString() + " ");
				if (object instanceof Resource) {
					System.out.print(object.toString());
				} else {
					// object is a literal
					System.out.print(" \"" + object.toString() + "\"");
				}
				System.out.println(" .");
			}
		}
		System.out.println("Total: " + countofstatement);
	}

	//2
	public static void printallstatementsandtrytoprintwebservicecallfromloc() {
		Model model = ModelFactory.createDefaultModel();
		UtilityClass.readRdfInputFile(model, inputFileName);

		Property pp = model.getProperty("dc", "title");
		Property p2 = model.getProperty("http://purl.org/dc/elements/1.1/",
				"subject");
		StmtIterator iter = model.listStatements();

		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement();
			Resource subject = stmt.getSubject(); // get the subject
			Property predicate = stmt.getPredicate(); // get the predicate
			RDFNode object = stmt.getObject(); // get the object

			System.out.print(subject.toString());

			if (predicate.equals(p2)) {
				String locwebcall = UtilityClass.try03(object);

				System.out.println("boom");
				countofstatement++;
				System.out.println(countofstatement);
				// subject.addProperty(p2, locwebcall);
			}

			System.out.print(" " + predicate.toString() + " ");
			if (object instanceof Resource) {
				System.out.print(object.toString());
			} else {
				// object is a literal
				System.out.print(" \"" + object.toString() + "\"");
			}
			System.out.println(" .");
		}
		System.out.println(countofstatement);
	}

	//1
	public static void printoffallstatements() {
		Model model = ModelFactory.createDefaultModel();
		UtilityClass.readRdfInputFile(model, inputFileName);

		StmtIterator iter = model.listStatements();

		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement();
			Resource subject = stmt.getSubject(); // get the subject
			Property predicate = stmt.getPredicate(); // get the predicate
			RDFNode object = stmt.getObject(); // get the object

			System.out.print(subject.toString());
			System.out.print(" " + predicate.toString() + " ");
			if (object instanceof Resource) {
				System.out.print(object.toString());
			} else {
				// object is a literal
				System.out.print(" \"" + object.toString() + "\"");
			}
			System.out.println(" .");
		}

	}
}
