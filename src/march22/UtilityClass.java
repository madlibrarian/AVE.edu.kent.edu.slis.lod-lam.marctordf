package march22;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URLEncoder;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class UtilityClass {

	
	
	public static String queryLocLinked(RDFNode resourceliteral, LocQueryType queryType) {
		
		URL uri = null;
		String entitieToQuery = null;

			try {

				if (resourceliteral instanceof Resource) {
					System.out.println("should be printed out as a resource");
				} else {
					entitieToQuery = resourceliteral.toString().trim();
					entitieToQuery = entitieToQuery.replace(".", "");
					entitieToQuery = URLEncoder.encode(entitieToQuery,
							"ISO-8859-1").replace("+", "%20");// "UTF-8"
					uri = new URL("http", "id.loc.gov",
							"/authorities/label/".concat(entitieToQuery));
				}

				HttpURLConnection conn = (HttpURLConnection) uri.toURI().toURL()
						.openConnection();
				conn.connect();
				int responseCode = conn.getResponseCode();

				URL sonn = conn.getURL();

				if (responseCode != 200) {
					System.out.println(responseCode + " "+ queryType + " " + entitieToQuery );
				
					return null;

				} else {
					System.out.println(responseCode + " "+ queryType + " " +  entitieToQuery );
					System.out.println(sonn);
					return sonn.toString();
				}

			} catch (Exception e) {
			
				System.out.println(e.getMessage());
			
			}
			return null;

	}



	

	
	// http://stackoverflow.com/questions/1660034/replace-last-part-of-string
	// http://www.ibiblio.org/xml/books/xmljava/chapters/ch03s03.html
	
	
	
	
	
	
	
	public static void readRdfInputFile(Model model, String inputFileName) {
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName
					+ " not found");
		}

		// read the RDF/XML file
		model.read(in, "RDF/XML");
	}

	public static void printRdfToConsole(Model model) {
		// write it to standard out
		model.write(System.out, "RDF/XML-ABBREV" );
	}

	public static void publishRDFToDisk(Model model, String fileName) {
		OutputStream fout;
		try {
			fout = new FileOutputStream(fileName);
			OutputStream bout = new BufferedOutputStream(fout);
			model.write(bout, "N-TRIPLE");
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}
	}

	public static void consolePrintSubjectPredicateObject(Resource subject,
			Property predicate, RDFNode object) {
		System.out.println("RESOURCE   : " + subject.toString());
		System.out.println("PROPERTY   : " + predicate.toString());
		  System.out.print("OBJECT     : ");
		if (object instanceof Resource) {
			System.out.print(object.toString());
		} else {
			// object is a literal
			System.out.print(" \"" + object.toString() + "\"");
		}
		System.out.println(" .");
	}
}
