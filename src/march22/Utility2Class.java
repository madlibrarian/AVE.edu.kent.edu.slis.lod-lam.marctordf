package march22;

public class Utility2Class {
	
	
	
	
	public static String buildStringToQueryLoc(LocQueryType queryType) {
		if(queryType == LocQueryType.creator){
			return "/authorities/label/";
		}else if(queryType == LocQueryType.subject){
			return "/authorities/label/";
		}else if(queryType == LocQueryType.publisher){
			return "/authorities/label/";
		}
		return null;
		
	}

}

