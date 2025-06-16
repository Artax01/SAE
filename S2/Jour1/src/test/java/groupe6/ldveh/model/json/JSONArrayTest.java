package groupe6.ldveh.model.json;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class JSONArrayTest {

	private JSONArray jsonArray;

	JSONArrayTest() {
		jsonArray = new JSONArray();
	}

	@Test
	public void testAddAndGetString() {
		jsonArray.add("Hello");
		assertEquals("Hello", jsonArray.getString(0));
	}

	@Test
	public void testAddAndGetNumber() {
		jsonArray.add(42);
		assertEquals(42, jsonArray.getNumber(0));
	}

	@Test
	public void testAddAndGetBoolean() {
		jsonArray.add(true);
		assertTrue(jsonArray.getBoolean(0));
	}

	@Test
	public void testAddAndGetJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonArray.add(jsonObject);
		assertEquals(jsonObject, jsonArray.getJSONObject(0));
	}

	@Test
	public void testAddAndGetJSONArray() {
		JSONArray innerArray = new JSONArray();
		jsonArray.add(innerArray);
		assertEquals(innerArray, jsonArray.getJSONArray(0));
	}

	@Test
	public void testGetStringThrowsClassCastException() {
		jsonArray.add(123);
		assertThrows(ClassCastException.class, () -> jsonArray.getString(0));
	}

	@Test
	public void testGetNumberThrowsClassCastException() {
		jsonArray.add("Not a number");
		assertThrows(ClassCastException.class, () -> jsonArray.getNumber(0));
	}

	@Test
	public void testGetBooleanThrowsClassCastException() {
		jsonArray.add("true");
		assertThrows(ClassCastException.class, () -> jsonArray.getBoolean(0));
	}

	@Test
	public void testGetJSONObjectThrowsClassCastException() {
		jsonArray.add("Not a JSONObject");
		assertThrows(ClassCastException.class, () -> jsonArray.getJSONObject(0));
	}

	@Test
	public void testGetJSONArrayThrowsClassCastException() {
		jsonArray.add("Not a JSONArray");
		assertThrows(ClassCastException.class, () -> jsonArray.getJSONArray(0));
	}

	@Test
	public void testToString() {
		jsonArray.add("Test");
		jsonArray.add(100);
		jsonArray.add(false);
		assertDoesNotThrow(() -> jsonArray.toString());
	}

}
