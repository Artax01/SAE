package app;

import app.ai.api.API;
import app.ai.world.WorldGenerator;
import app.controller.Game;
import app.model.parser.JSONArray;
import app.model.parser.JSONObject;
import app.model.parser.JSONParser;
import app.model.parser.WorldIO;
import app.model.entity.Player;
import app.model.map.World;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {

    	String prompt = "Génère un lieu médiéval sous forme JSON avec nom et texte";
    	String response = "";
    	String nom = "";
    	World gen = null;
    	
    	try {
    		response = API.generateContent(prompt);

    		JSONParser parser = new JSONParser(response);
            JSONObject json = parser.parse();
            
            JSONArray candidatesArray = json.getJSONArray("candidates");
            JSONObject candidatesObj = candidatesArray.getJSONObject(0);
            
            JSONObject contentObj = candidatesObj.getJSONObject("content");
            
            JSONArray partsArray = contentObj.getJSONArray("parts");
            JSONObject partsObj = partsArray.getJSONObject(0);
            
            String textString = partsObj.getString("text");
            
            JSONParser p = new JSONParser(textString);
            JSONObject pObj = p.parse();
            
            nom = pObj.getString("nom"); // le nom du monde par Gemini
            
            gen = WorldGenerator.createWorld(nom, 5, new Player(), 0.3f, 0.2f, 0.3f, 0.2f);
    	}
    	catch (Exception e) {
    		System.out.println("Impossible de contacter l'API de Gemini.");
    	}
    		
    	// on pourrait mettre le nom extrait comme nom de fichier mais avec les accents c'est complexe.
    	WorldIO.saveWorld(gen, new File("src/main/resources/app/MondeSAE.json"));
 
    	/*
    	DES FOIS IL FAUT REFRESH SES FICHIERS PARFOIS 
    	POUR QUE LA LIGNE DU DESSOUS FONCTIONNE !!!!!!!!
    	*/
    	
        World world = WorldIO.loadWorld(Main.class.getResourceAsStream("/app/MondeSAE.json"));
        Player player = new Player(100,100,20,20, 100);
        Game game = new Game(world, player);

        game.play();
    }
}