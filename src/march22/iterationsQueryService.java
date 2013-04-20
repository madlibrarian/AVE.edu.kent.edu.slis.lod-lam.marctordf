package march22;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class iterationsQueryService {
	public static String queryLocForSubjectLinkedUrl(RDFNode resourceliteral) {

		URL uri = null;
		String subjectTermToConvert = null;
		try {

			if (resourceliteral instanceof Resource) {
				// System.out.print(resourceliteral.toString());

				System.out.println("should be printed out as a resource");
			} else {
				// object is a literal
				// System.out.print(" \"" + resourceliteral.toString() + "\"");

				// URL uri = new
				// URL("http://id.loc.gov/authorities/label/".concat(subjectTermToConvert));
				subjectTermToConvert = resourceliteral.toString().trim();
				// subjectTermToConvert = subjectTermToConvert.substring(0,
				// subjectTermToConvert.lastIndexOf(".")) + "";

				subjectTermToConvert = subjectTermToConvert.replace(".", "");
				// subjectTermToConvert = subjectTermToConvert.replace("(", "");
				// subjectTermToConvert = subjectTermToConvert.replace(")", "");
				// subjectTermToConvert = subjectTermToConvert.replace(".",
				// "").trim();

				subjectTermToConvert = URLEncoder.encode(subjectTermToConvert,
						"ISO-8859-1").replace("+", "%20");// "UTF-8"

				uri = new URL("http", "id.loc.gov",
						"/authorities/label/".concat(subjectTermToConvert));
			}

			// URL uri = new URL("http://id.loc.gov");

			HttpURLConnection conn = (HttpURLConnection) uri.toURI().toURL()
					.openConnection();
			conn.connect();
			int responseCode = conn.getResponseCode();

			// InputStream is = conn.getInputStream();

			URL sonn = conn.getURL();

			if (responseCode != 200) {
				System.out.println("fail");
				System.out.println(responseCode);
				return "fail";

			} else {
				System.out.println("successfull web call to loc");
				return sonn.toString();
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return "fail";

	}


	public static String queryLocForSubjectLinkedUrl(RDFNode resourceliteral) {
		URL uri = null;
		String subjectTermToConvert = null;
		try {

			if (resourceliteral instanceof Resource) {
				// System.out.print(resourceliteral.toString());

				System.out.println("should be printed out as a resource");
			} else {
				// object is a literal
				// System.out.print(" \"" + resourceliteral.toString() + "\"");

				// URL uri = new
				// URL("http://id.loc.gov/authorities/label/".concat(subjectTermToConvert));
				subjectTermToConvert = resourceliteral.toString().trim();
				// subjectTermToConvert = subjectTermToConvert.substring(0,
				// subjectTermToConvert.lastIndexOf(".")) + "";

				subjectTermToConvert = subjectTermToConvert.replace(".", "");
				// subjectTermToConvert = subjectTermToConvert.replace("(", "");
				// subjectTermToConvert = subjectTermToConvert.replace(")", "");
				// subjectTermToConvert = subjectTermToConvert.replace(".",
				// "").trim();

				subjectTermToConvert = URLEncoder.encode(subjectTermToConvert,
						"ISO-8859-1").replace("+", "%20");// "UTF-8"

				uri = new URL("http", "id.loc.gov",
						"/authorities/label/".concat(subjectTermToConvert));
			}

			// URL uri = new URL("http://id.loc.gov");

			HttpURLConnection conn = (HttpURLConnection) uri.toURI().toURL()
					.openConnection();
			conn.connect();
			int responseCode = conn.getResponseCode();

			// InputStream is = conn.getInputStream();

			URL sonn = conn.getURL();

			if (responseCode != 200) {
				System.out.println("fail : " + responseCode + " & subjectterm = " + subjectTermToConvert );
				return null;

			} else {
				System.out.println("successfull web call to loc with " + subjectTermToConvert + " " + responseCode);
				return sonn.toString();
			}

		} catch (Exception e) {
		
			System.out.println(e.getMessage());
		}
		return null;
		

	}
	
	public static String queryLocForCreatorLink(RDFNode resourceliteral) {
		URL uri = null;
		String subjectTermToConvert = null;
		try {

			if (resourceliteral instanceof Resource) {
				System.out.println("should be printed out as a resource");
			} else {
		
				subjectTermToConvert = resourceliteral.toString().trim();
				subjectTermToConvert = subjectTermToConvert.replace(".", "");
				subjectTermToConvert = URLEncoder.encode(subjectTermToConvert,
						"ISO-8859-1").replace("+", "%20");// "UTF-8"

				uri = new URL("http", "id.loc.gov",
						"/authorities/names/label/".concat(subjectTermToConvert));
			}

			HttpURLConnection conn = (HttpURLConnection) uri.toURI().toURL()
					.openConnection();
			conn.connect();
			int responseCode = conn.getResponseCode();
			URL sonn = conn.getURL();

			if (responseCode != 200) {
				System.out.println("fail");
				System.out.println(responseCode);
				return "fail";

			} else {
				System.out.println("successfull web call to loc");
				return sonn.toString();
			}

		} catch (Exception e) {
		
			System.out.println(e.getMessage());
		}
		return "fail";

	}




}
