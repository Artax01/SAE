package groupe6.ldveh.model.json;

import java.util.List;

import java.util.ArrayList;


/**
 * The JSONArray class represents a JSON array in Java.
 * It allows storing a list of objects of different types
 * and provides methods to access these objects based on their type.
 */
public class JSONArray {

    // List to store the elements of the JSON array
    private List<Object> data;

    /**
     * Constructor for the JSONArray class.
     * Initializes a new empty list to store the elements of the JSON array.
     */
    JSONArray() {
        data = new ArrayList<Object>();
    }

    /**
     * Adds an object to the end of the JSON array.
     *
     * @param object The object to add to the JSON array.
     */
    public void add(Object object) {
        data.add(object);
    }

    /**
     * Retrieves the element at the specified index as a String.
     *
     * @param index The index of the element to retrieve.
     * @return The element at the specified index as a String.
     * @throws ClassCastException If the element is not a String.
     */
    public String getString(int index) throws ClassCastException {
        if (data.get(index) instanceof String) {
            return (String) data.get(index);
        } else {
            throw new ClassCastException();
        }
    }

    /**
     * Retrieves the element at the specified index as a Number.
     *
     * @param index The index of the element to retrieve.
     * @return The element at the specified index as a Number.
     * @throws ClassCastException If the element is not a Number.
     */
    public Number getNumber(int index) throws ClassCastException {
        if (data.get(index) instanceof Number) {
            return (Number) data.get(index);
        } else {
            throw new ClassCastException();
        }
    }

    /**
     * Retrieves the element at the specified index as a Boolean.
     *
     * @param index The index of the element to retrieve.
     * @return The element at the specified index as a Boolean.
     * @throws ClassCastException If the element is not a Boolean.
     */
    public boolean getBoolean(int index) throws ClassCastException {
        if (data.get(index) instanceof Boolean) {
            return (Boolean) data.get(index);
        } else {
            throw new ClassCastException();
        }
    }

    /**
     * Retrieves the element at the specified index as a JSONObject.
     *
     * @param index The index of the element to retrieve.
     * @return The element at the specified index as a JSONObject.
     * @throws ClassCastException If the element is not a JSONObject.
     */
    public JSONObject getJSONObject(int index) throws ClassCastException {
        if (data.get(index) instanceof JSONObject) {
            return (JSONObject) data.get(index);
        } else {
            throw new ClassCastException();
        }
    }

    /**
     * Retrieves the element at the specified index as a JSONArray.
     *
     * @param index The index of the element to retrieve.
     * @return The element at the specified index as a JSONArray.
     * @throws ClassCastException If the element is not a JSONArray.
     */
    public JSONArray getJSONArray(int index) throws ClassCastException {
        if (data.get(index) instanceof JSONArray) {
            return (JSONArray) data.get(index);
        } else {
            throw new ClassCastException();
        }
    }
	
	@Override
	public String toString() {
		for (int i = 0; i < this.data.size(); i++) {
			System.out.println(i + " : " + data.get(i));
		}
		return "";
	}
}
