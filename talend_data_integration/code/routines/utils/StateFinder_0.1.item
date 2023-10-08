package routines;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StateFinder {

    private static final Map<String, String> stateCodeToNameMap = new HashMap<>();
    
    static {
        stateCodeToNameMap.put("AL", "Alabama");
        stateCodeToNameMap.put("AK", "Alaska");
        stateCodeToNameMap.put("AZ", "Arizona");
        stateCodeToNameMap.put("AR", "Arkansas");
        stateCodeToNameMap.put("AS", "American Samoa");
        stateCodeToNameMap.put("CA", "California");
        stateCodeToNameMap.put("CO", "Colorado");
        stateCodeToNameMap.put("CT", "Connecticut");
        stateCodeToNameMap.put("DE", "Delaware");
        stateCodeToNameMap.put("DC", "District of Columbia");
        stateCodeToNameMap.put("FL", "Florida");
        stateCodeToNameMap.put("GA", "Georgia");
        stateCodeToNameMap.put("GU", "Guam");
        stateCodeToNameMap.put("HI", "Hawaii");
        stateCodeToNameMap.put("ID", "Idaho");
        stateCodeToNameMap.put("IL", "Illinois");
        stateCodeToNameMap.put("IN", "Indiana");
        stateCodeToNameMap.put("IA", "Iowa");
        stateCodeToNameMap.put("KS", "Kansas");
        stateCodeToNameMap.put("KY", "Kentucky");
        stateCodeToNameMap.put("LA", "Louisiana");
        stateCodeToNameMap.put("ME", "Maine");
        stateCodeToNameMap.put("MD", "Maryland");
        stateCodeToNameMap.put("MA", "Massachusetts");
        stateCodeToNameMap.put("MI", "Michigan");
        stateCodeToNameMap.put("MN", "Minnesota");
        stateCodeToNameMap.put("MS", "Mississippi");
        stateCodeToNameMap.put("MO", "Missouri");
        stateCodeToNameMap.put("MT", "Montana");
        stateCodeToNameMap.put("NE", "Nebraska");
        stateCodeToNameMap.put("NV", "Nevada");
        stateCodeToNameMap.put("NH", "New Hampshire");
        stateCodeToNameMap.put("NJ", "New Jersey");
        stateCodeToNameMap.put("NM", "New Mexico");
        stateCodeToNameMap.put("NY", "New York");
        stateCodeToNameMap.put("NC", "North Carolina");
        stateCodeToNameMap.put("ND", "North Dakota");
        stateCodeToNameMap.put("MP", "Northern Mariana Islands");
        stateCodeToNameMap.put("OH", "Ohio");
        stateCodeToNameMap.put("OK", "Oklahoma");
        stateCodeToNameMap.put("OR", "Oregon");
        stateCodeToNameMap.put("PA", "Pennsylvania");
        stateCodeToNameMap.put("PR", "Puerto Rico");
        stateCodeToNameMap.put("RI", "Rhode Island");
        stateCodeToNameMap.put("SC", "South Carolina");
        stateCodeToNameMap.put("SD", "South Dakota");
        stateCodeToNameMap.put("TN", "Tennessee");
        stateCodeToNameMap.put("TX", "Texas");
        stateCodeToNameMap.put("TT", "Trust Territories");
        stateCodeToNameMap.put("UT", "Utah");
        stateCodeToNameMap.put("VT", "Vermont");
        stateCodeToNameMap.put("VI", "Virgin Islands");
        stateCodeToNameMap.put("VA", "Virginia");
        stateCodeToNameMap.put("WA", "Washington");
        stateCodeToNameMap.put("WV", "West Virginia");
        stateCodeToNameMap.put("WI", "Wisconsin");
        stateCodeToNameMap.put("WY", "Wyoming");
        stateCodeToNameMap.put("PW", "Palau");
        stateCodeToNameMap.put("MH", "Marshall Islands");
        stateCodeToNameMap.put("FM", "Federated States of Micronesia");
    }
	
    public static String getStateFromAddress(String address) {
        if (address != null) {
            Pattern apoDpoFpoPattern = Pattern.compile("(?i)(APO|DPO|FPO)");
            Matcher apoDpoFpoMatcher = apoDpoFpoPattern.matcher(address);

            if (apoDpoFpoMatcher.find()) {
                return "Armed Forces";
            }
        	
        	Pattern pattern = Pattern.compile("([A-Z]{2})");
            Matcher matcher = pattern.matcher(address);
            
            if (matcher.find()) {
                String stateCode = matcher.group();
                String stateName = stateCodeToNameMap.get(stateCode);
                if (stateName != null) {
                    return stateName;
                }
            }
        }
        return "Unknown state";
    }
}
