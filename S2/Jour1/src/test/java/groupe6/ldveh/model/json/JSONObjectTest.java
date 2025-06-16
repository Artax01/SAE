package groupe6.ldveh.model.json;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JSONObjectTest {

	private JSONObject jsonObject;

	JSONObjectTest() {
		jsonObject = new JSONObject();
	}

	@Test
	void testPutAndGetString() {
		jsonObject.put("name", "Alice");
		assertEquals("Alice", jsonObject.getString("name"));
	}

	@Test
	void testGetStringThrowsClassCastException() {
		jsonObject.put("age", 30);
		assertThrows(ClassCastException.class, () -> jsonObject.getString("age"));
	}

	@Test
	void testPutAndGetNumber() {
		jsonObject.put("age", 25);
		assertEquals(25, jsonObject.getNumber("age"));
	}

	@Test
	void testGetNumberThrowsClassCastException() {
		jsonObject.put("name", "Bob");
		assertThrows(ClassCastException.class, () -> jsonObject.getNumber("name"));
	}

	@Test
	void testPutAndGetBoolean() {
		jsonObject.put("isActive", true);
		assertTrue(jsonObject.getBoolean("isActive"));
	}

	@Test
	void testGetBooleanThrowsClassCastException() {
		jsonObject.put("isActive", "true");
		assertThrows(ClassCastException.class, () -> jsonObject.getBoolean("isActive"));
	}

	@Test
	void testPutAndGetJSONObject() {
		JSONObject nested = new JSONObject();
		nested.put("key", "value");
		jsonObject.put("nested", nested);
		assertEquals("value", jsonObject.getJSONObject("nested").getString("key"));
	}

	@Test
	void testGetJSONObjectThrowsClassCastException() {
		jsonObject.put("nested", "not a JSONObject");
		assertThrows(ClassCastException.class, () -> jsonObject.getJSONObject("nested"));
	}

	@Test
	void testPutAndGetJSONArray() {
		JSONArray array = new JSONArray();
		jsonObject.put("array", array);
		assertEquals(array, jsonObject.getJSONArray("array"));
	}

	@Test
	void testGetJSONArrayThrowsClassCastException() {
		jsonObject.put("array", 123);
		assertThrows(ClassCastException.class, () -> jsonObject.getJSONArray("array"));
	}

}
