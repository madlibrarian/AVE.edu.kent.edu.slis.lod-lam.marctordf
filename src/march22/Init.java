package march22;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class Init {

	// static final String inputFileName =
	// "file/SemanticEnhancedorgeonlargedownload.rdf";
	static final String inputFileName = "file/hhhhhhhhh.rdf";// hhhhhhhhh.rdf
	static final String outputFileName = "file/fibonacci2.rdf";

	static final String ns02 = "http://purl.org/dc/elements/1.1/";

	static int countofstatement = 0;
	static int countofresources = 0;

	static int numberoferrorsforsubjectconversion = 0;
	static int numberoferrorsforcreatorconversion = 0;
	static int numberoferrorsforpublidhingconversion = 0;
	static int numberoferrorsforcoverageconversion = 0;

	static int numberofsuccessfullpublishingconversion = 0;
	static int numberofsuccessfullcoverageconversion = 0;

	static int numberofresourcesatstart = 0;
	static int numberofstatementsatstart = 0;
	static int numberofreourcesatend = 0;
	static int numberofstatementatend = 0;

	private Shell shell;

	public Init(Display display) {

		shell = new Shell(display);
		shell.setText("MARC to Linked Data Wizard");

		dinitUI();

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();

		}

	}

	private void dinitUI() {
		
		
		final Text text1 = new Text(shell, SWT.WRAP | SWT.BORDER);
		text1.setBounds(100, 50, 100, 20);
		
		final Text text2 = new Text(shell, SWT.SINGLE | SWT.BORDER);
		text2.setBounds(100, 75, 100, 20);

		Label label1 = new Label(shell, SWT.BORDER);
		Label label2 = new Label(shell, SWT.BORDER);

		label1.setSize(100, 30);
		label1.setLocation(0, 50);
		label1.setText("rdf file input");

		label2.setSize(100, 30);
		label2.setLocation(0, 75);
		label2.setText("rdf file output");

		Button submitButton = new Button(shell, SWT.PUSH);
		submitButton.setBounds(0, 110, 80, 30);
		submitButton.setText("Submit");

		submitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				activateMarcToLinkedDataConversionScript(text1.getText(), text2.getText());
			}
		});

	}

	public static void main(String[] args) {

		Display display = new Display();
		new Init(display);
		display.dispose();

	}

	public static void activateMarcToLinkedDataConversionScript(String inputfilename, String outputfilename) {
		Model model = ModelFactory.createDefaultModel();
		UtilityClass.readRdfInputFile(model, inputfilename);

		Property p1 = model.getProperty(ns02, "creator");
		Property p2 = model.getProperty(ns02, "subject");
		Property p3 = model.getProperty(ns02, "publisher");
		Property p4 = model.getProperty(ns02, "coverage");

		List<Statement> listofsubjectstatesments = new ArrayList<Statement>();
		List<Statement> listofcreatorstatesments = new ArrayList<Statement>();

		// calculate numbers in the beginning
		List<Resource> relist1 = model.listSubjects().toList();
		List<Statement> statements1 = model.listStatements().toList();
		numberofresourcesatstart = relist1.size();
		numberofstatementsatstart = statements1.size();
		//

		System.out.println("resources = " + numberofresourcesatstart);
		System.out.println("statements = " + numberofstatementsatstart);

		ResIterator resiter = model.listSubjects();
		try {
			while (resiter.hasNext()) {
				countofresources++;
				Resource resoustuff = resiter.nextResource();
				StmtIterator st = resoustuff.listProperties();

				while (st.hasNext()) {
					countofstatement++;
					Statement stat2 = st.nextStatement();
					System.out.println("Statement ID: " + countofstatement);
					// Resource subject = stat2.getSubject(); // get the subject
					// Property predicate = stat2.getPredicate(); // get the
					RDFNode object = stat2.getObject(); // get the object

					// UtilityClass.consolePrintSubjectPredicateObject(subject,
					// predicate, object);

					// ////////////////////////////////////////////////////////////////////////
					if (stat2.getPredicate().equals(p2)) {

						String urlfromconvert = UtilityClass.queryLocLinked(
								object, LocQueryType.subject);
						if (urlfromconvert != null) {
							try {
								Statement jjl = model.createStatement(
										resoustuff, p2, urlfromconvert);

								listofsubjectstatesments.add(jjl);

							} catch (Exception e) {
								System.out
										.println("ERROR IN ADDING or Finding Subject Linked link: "
												+ e.getMessage());
							}
						} else {

							// this will add up when urlfromconvert subject was
							// null
							numberoferrorsforsubjectconversion++;
						}
					}
					// ///////////////////////////////////////////////////////////////////////////
					if (stat2.getPredicate().equals(p1)) {

						String urlfromconvert = UtilityClass.queryLocLinked(
								object, LocQueryType.creator);

						if (urlfromconvert != null) {
							try {
								Statement jjl = model.createStatement(
										resoustuff, p1, urlfromconvert);

								listofcreatorstatesments.add(jjl);

							} catch (Exception e) {
								System.out
										.println("ERROR IN ADDING: for finding Creator Linked link:"
												+ e.getMessage());
							}
						} else {

							// this will add up when urlfromconvert creator was
							// null
							numberoferrorsforcreatorconversion++;
						}

					}
					// ///////////////////////////////////////////////////////////////////////////////////
					if (stat2.getPredicate().equals(p3)) {

						String urlfromconvert = UtilityClass.queryLocLinked(
								object, LocQueryType.publisher);

						if (urlfromconvert != null) {
							try {
								// Statement jjl = model.createStatement(
								// resoustuff, p3, urlfromconvert);
								numberofsuccessfullpublishingconversion++;
								// listofcreatorstatesments.add(jjl);

							} catch (Exception e) {
								System.out
										.println("ERROR IN ADDING: for finding publisher Linked link:"
												+ e.getMessage());
							}
						} else {

							// this will add up when urlfromconvert creator was
							// null
							// numberoferrorsforcreatorconversion++;
							numberoferrorsforpublidhingconversion++;
						}
					}
					// ///////////////////////////////////////////////////////////
					if (stat2.getPredicate().equals(p4)) {

						String urlfromconvert = UtilityClass.queryLocLinked(
								object, LocQueryType.coverage);

						if (urlfromconvert != null) {
							try {
								// Statement jjl = model.createStatement(
								// resoustuff, p4, urlfromconvert);

								// numberofsuccessfullpublishingconversion++;
								// listofcreatorstatesments.add(jjl);
								numberofsuccessfullcoverageconversion++;
							} catch (Exception e) {
								System.out
										.println("ERROR IN ADDING: for finding publisher Linked link:"
												+ e.getMessage());
							}
						} else {
							// numberoferrorsforpublidhingconversion++;
							numberoferrorsforcoverageconversion++;
						}

					}
					// //////////////////////////////////////////////////////////////////////////////////////
				}
			}
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		}

		try {
			model.add(listofsubjectstatesments);
			model.add(listofcreatorstatesments);
			UtilityClass.printRdfToConsole(model);
			UtilityClass.publishRDFToDisk(model, outputfilename);
			// calculate numbers in the beginning
			List<Resource> relist2 = model.listSubjects().toList();
			List<Statement> statements2 = model.listStatements().toList();
			numberofreourcesatend = relist2.size();
			numberofstatementatend = statements2.size();
			//

		} catch (Exception e) {

			System.out
					.println("FINAL ERROR 101: ADDING LISTOFSTATEMENTS TO MODEL - "
							+ e.getMessage());
		}

		System.out.println("Total # of Statements: " + countofstatement);
		System.out.println("Total Number of Resources: " + countofresources);

		// bad conversions
		System.out.println("Total number of creator bad conversions:"
				+ numberoferrorsforcreatorconversion);
		System.out.println("Total number of subject bad conversions:"
				+ numberoferrorsforsubjectconversion);
		System.out.println("Total number of publisher bad conversions:"
				+ numberoferrorsforpublidhingconversion);
		System.out.println("Total number of coverage bad conversions:"
				+ numberoferrorsforcoverageconversion);

		// success for conversion
		System.out.println("Total number of subject successfull conversions: "
				+ listofsubjectstatesments.size() + " subjects");
		System.out.println("Total number of creator successfull conversions: "
				+ listofcreatorstatesments.size() + " creator");
		System.out
				.println("Total number of publishing successfull conversions: "
						+ numberofsuccessfullpublishingconversion + " publish");
		System.out.println("Total number of coverage successfull conversions: "
				+ numberofsuccessfullcoverageconversion + " coverage");

		System.out.println("resources -------- start : "
				+ numberofresourcesatstart + "end : " + numberofreourcesatend);
		System.out
				.println("creator   -------- start : "
						+ numberofstatementsatstart + "end : "
						+ numberofstatementatend);
	}
}

// text1.setTextLimit(5);
// text1.setText("12345");
// text2.setTextLimit(30);
// text2.setText("ABC");
