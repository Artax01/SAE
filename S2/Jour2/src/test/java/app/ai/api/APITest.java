package app.ai.api;

import org.junit.jupiter.api.Test;

import app.model.parser.JSONObject;
import app.model.parser.JSONParser;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the API class, specifically the generateContent method.
 * These tests validate the behavior of the content generation feature
 * under various input scenarios, including valid, empty, and null prompts.
 */
public class APITest {

    /**
     * Tests the generateContent method with a valid prompt.
     * Ensures the response is not null and contains expected JSON structure and keys.
     *
     * @throws Exception if parsing the response fails or if the API call throws an exception
     * @return void
     */
    @Test
    public void testGenerateContentValidPrompt() throws Exception {
        String prompt = "Génère un lieu médiéval sous forme JSON avec nom et texte";
        String response = API.generateContent(prompt);

        assertNotNull(response);
        
        JSONParser parser = new JSONParser(response);
        JSONObject json = parser.parse();
        
        assertTrue(json.containsKey("candidates"));
        assertTrue(response.contains("nom"));
        assertTrue(response.contains("parts"));
        assertTrue(response.contains("text"));
    }

    /**
     * Tests the generateContent method with an empty string as prompt.
     * Expects the method to throw an Exception.
     *
     * @return void
     */
    @Test
    public void testGenerateContentEmptyPrompt() {
        Exception exception = assertThrows(Exception.class, () -> {
            API.generateContent("");
        });

        assertNotNull(exception);
    }

    /**
     * Tests the generateContent method with a null prompt.
     * Expects the method to throw an Exception.
     *
     * @return void
     */
    @Test
    public void testGenerateContentNullPrompt() {
        Exception exception = assertThrows(Exception.class, () -> {
            API.generateContent(null);
        });

        assertNotNull(exception);
    }
}

