package groupe6.ldveh.model.json;

import java.util.HashMap;
import java.util.Map.Entry;


/**
 * The JSONObject class is a simple implementation of a JSON object in Java. It
 * allows storing key-value pairs where values can be of different types
 * (String, Number, Boolean, JSONObject, JSONArray).
 */
public class JSONObject {

	public HashMap<String, Object> data;

	/**
	 * Initializes a new JSONObject with an empty HashMap to store data.
	 */
	public JSONObject() {
		data = new HashMap<String, Object>();
	}

	/**
	 * Adds a key-value pair to the JSON object.
	 *
	 * @param key    The key under which the value will be stored.
	 * @param object The value to be stored.
	 */
	public void put(String key, Object object) {
		data.put(key, object);
	}

	/**
	 * Retrieves the value associated with the specified key as a String.
	 *
	 * @param key The key whose value is to be retrieved.
	 * @return The value associated with the key as a String.
	 * @throws ClassCastException If the value associated with the key is not an
	 *                            instance of String.
	 */
	public String getString(String key) throws ClassCastException {
		if (data.get(key) instanceof String) {
			return (String) data.get(key);
		} else {
			throw new ClassCastException();
		}
	}

	/**
	 * Retrieves the value associated with the specified key as a Number.
	 *
	 * @param key The key whose value is to be retrieved.
	 * @return The value associated with the key as a Number.
	 * @throws ClassCastException If the value associated with the key is not an
	 *                            instance of Number.
	 */
	public Number getNumber(String key) throws ClassCastException {
		if (data.get(key) instanceof Number) {
			return (Number) data.get(key);
		} else {
			throw new ClassCastException();
		}
	}

	/**
	 * Retrieves the value associated with the specified key as a Boolean.
	 *
	 * @param key The key whose value is to be retrieved.
	 * @return The value associated with the key as a Boolean.
	 * @throws ClassCastException If the value associated with the key is not an
	 *                            instance of Boolean.
	 */
	public boolean getBoolean(String key) throws ClassCastException {
		if (data.get(key) instanceof Boolean) {
			return (Boolean) data.get(key);
		} else {
			throw new ClassCastException();
		}
	}

	/**
	 * Retrieves the value associated with the specified key as a JSONObject.
	 *
	 * @param key The key whose value is to be retrieved.
	 * @return The value associated with the key as a JSONObject.
	 * @throws ClassCastException If the value associated with the key is not an
	 *                            instance of JSONObject.
	 */
	public JSONObject getJSONObject(String key) throws ClassCastException {
		if (data.get(key) instanceof JSONObject) {
			return (JSONObject) data.get(key);
		} else {
			throw new ClassCastException();
		}
	}


	public JSONArray getJSONArray(String key) throws ClassCastException {
		if (data.get(key) instanceof JSONArray) {
			return (JSONArray) data.get(key);
		} else {
			throw new ClassCastException();
		}
	}
	
	@Override
	public String toString() {
		for (Entry<String, Object> e: data.entrySet()) {
			System.out.println(e.getKey() + " : " + e.getValue());
		}
		return "";
	}
}
