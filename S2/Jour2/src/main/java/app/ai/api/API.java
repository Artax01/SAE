package app.ai.api; 

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import app.model.parser.JSONArray;
import app.model.parser.JSONObject;
import app.model.parser.JSONParser;

public class API {
    private static final String API_KEY = "";
    private static final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

    /**
     * Sends a POST request to a remote API to generate content based on a textual prompt.
     * The request includes a generation configuration and an expected JSON schema for the response.
     * The API is expected to return a JSON-formatted string containing fields like "nom" and "texte".
     *
     * @param prompt A non-null, non-empty string describing the content to generate (e.g., "Generate a medieval place").
     * @return A JSON-formatted string returned by the API, containing the generated content.
     *
     * @throws Exception If the prompt is null or empty, if the connection fails, or if the API returns an error.
     */
    public static String generateContent(String prompt) throws Exception {
        if (prompt == null || prompt.trim().isEmpty()) {
            throw new Exception("Erreur de requête: 400 - Le prompt ne peut pas être vide ou null");
        }

        try {
            URL url = new URL(URL);
            
            JSONObject body = new JSONObject();
            JSONObject content = new JSONObject();
            JSONObject part = new JSONObject();
            JSONObject config = new JSONObject();
            
            part.put("text", prompt);
            content.put("parts", new JSONArray().add(part));
            body.put("contents", new JSONArray().add(content));
            config.put("maxOutputTokens", 200);
            config.put("responseMimeType", "application/json");
            
            JSONObject responseSchema = new JSONObject();
            responseSchema.put("type", "object");
            
            JSONObject properties = new JSONObject();
            JSONObject nom = new JSONObject();
            nom.put("type", "string");
            properties.put("nom", nom);
            
            JSONObject texte = new JSONObject();
            texte.put("type", "string");
            properties.put("texte", texte);
            
            JSONArray required = new JSONArray();
            required.add("nom");
            required.add("texte");
            
            responseSchema.put("properties", properties);
            responseSchema.put("required", required);
            config.put("responseSchema", responseSchema);
            body.put("generationConfig", config);
            
            // c'était long jpp

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try(DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(body.toString());
                wr.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String inputLine;
                StringBuilder errorResponse = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    errorResponse.append(inputLine);
                }
                in.close();

                throw new Exception("Erreur de requête: " + responseCode + " - " + errorResponse.toString());
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la requête: " + e.getMessage());
        }
    }





}
