package groupe6.ldveh.model.json;

public class JSONParser {

    private String toParse;
    private int compteur = 0;

    /**
     * Creates a new parser for a given JSON string.
     * Whitespace outside of quotes is removed.
     *
     * @param toParse The JSON string to parse
     */
    public JSONParser(String toParse) {
    	this.toParse = toParse.replaceAll("\\s+(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", ""); //notre propre regex    
    }

    /**
     * Parses the JSON string and returns the root object.
     *
     * @return A JSONObject if the string starts with '{', otherwise null
     */
    public JSONObject parse() {
        if (toParse.charAt(compteur) == '{') {
            return parseObject();
        }
        return null;
    }

    /**
     * Parses the JSON string and returns the root object.
     *
     * @return A JSONObject if the string starts with '{', otherwise null
     */
    public JSONObject parseObject() {
        JSONObject obj = new JSONObject();

        compteur++;
        
        while (compteur < toParse.length() && toParse.charAt(compteur) != '}') {
            if (toParse.charAt(compteur) == ',') {
                compteur++;
            }

            String key = parseString();
            //System.out.println(str);
            if (toParse.charAt(compteur) == ':') {
                compteur++;
            }

            Object value = parseValue();
            obj.put(key, value);
        }

        compteur++;
        return obj;
    }
    
    /**
     * Parses a JSON string (enclosed in "").
     *
     * @return The string value
     */
    public String parseString() {
    	 String str = "";
    	 
    	 compteur++;
    	 char ch = toParse.charAt(compteur);
    	 
    	 while (ch != '"') {
    		 str += ch;
    		 
    		 compteur++;
    		 
    		 ch = toParse.charAt(compteur);
    	 }
    	 compteur++;
    	 return str;

    	 }

    /**
     * Parses a JSON value (string, number, object, array, boolean, or null).
     *
     * @return Parsed value as Object
     */
    public Object parseValue() {
        char ch = toParse.charAt(compteur);

        if (ch == '"') {
            return parseString();
        } else if (ch == '{') {
            return parseObject();
        } else if (ch == '[') {
            return parseArray();
        } else if (ch == 't' || ch == 'f') {
            return parseBoolean();
        } else if (ch == 'n') {
            return parseNull();
        } else {
            return parseNumber();
        }
    }

    /**
     * Parses a boolean (true or false).
     *
     * @return true or false
     */
    public Boolean parseBoolean() {
    	 char ch = toParse.charAt(compteur);
    	 if (ch == 't' ) {
    		 compteur += 4;
    		 return true;
    	 } else {
    		 compteur += 5;
    		 return false;
    	 }

    }
    
    /**
     * Parses a number (int or double).
     *
     * @return Parsed number as Integer or Double
     */
    public Number parseNumber() {
        int start = compteur;
        while (compteur < toParse.length() &&
              (Character.isDigit(toParse.charAt(compteur)) || toParse.charAt(compteur) == '.' || toParse.charAt(compteur) == '-')) {
            compteur++;
        }
        String numberStr = toParse.substring(start, compteur);
        if (numberStr.contains("."))
            return Double.parseDouble(numberStr);
        else
            return Integer.parseInt(numberStr);
    }
    
    /**
     * Parses a null value.
     *
     * @return null
     */
    public Object parseNull() {
       compteur += 4;
       return null;
       
    }

    /**
     * Parses a JSON array (enclosed in []).
     *
     * @return Parsed JSONArray
     */
    public JSONArray parseArray() {
        JSONArray array = new JSONArray();
        compteur++;

        while (compteur < toParse.length() && toParse.charAt(compteur) != ']') {
            if (toParse.charAt(compteur) == ',') {
                compteur++;
            }

            Object value = parseValue();
            
            array.add(value);
        }

        compteur++;
        return array;
    }
}

