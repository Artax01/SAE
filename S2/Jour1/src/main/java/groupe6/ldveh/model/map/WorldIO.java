package groupe6.ldveh.model.map;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import groupe6.ldveh.model.entity.Monster;
import groupe6.ldveh.model.fight.Spell;
import groupe6.ldveh.model.logic.ExistPlaceException;
import groupe6.ldveh.model.logic.UnknownPlaceException;

public class WorldIO {
	public static void saveWorld(World w, File f) throws IOException {
		
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
        	System.out.println("WorldIO, écriture d'un fichier json...");
            writer.write("{\n");
            writer.write("  \"world\": \"" + escape(w.getName()) + "\",\n");

           // Ecriture de Place
            writer.write("  \"places\": [\n");
            for (int i = 0; i < w.listePlaces.size(); i++) {
                Place place = w.listePlaces.get(i);
                writer.write("    {\n");
                writer.write("      \"id\": " + place.getId() + ",\n");
                writer.write("      \"name\": \"" + escape(place.getName()) + "\",\n");
                writer.write("      \"description\": \"" + escape(place.getDescription()) + "\",\n");

                if (place.getMonster() != null) {
                    writer.write("      \"monster\": {\n");
                    writer.write("        \"name\": \"" + escape(place.getMonster().getName()) + "\",\n");
                    writer.write("        \"hp\": " + place.getMonster().getCurrentHP() + ",\n");
                    writer.write("        \"armor\": " + place.getMonster().getArmor() + ",\n");
                    writer.write("        \"attack\": " + place.getMonster().getAttack() + "\n");
                    writer.write("      },\n");
                } else {
                    writer.write("      \"monster\": null,\n");
                }
                writer.write("      \"end\": " + place.isEnd() + ",\n");
                writer.write("      \"start\": " + place.isStart() + ",\n");
                writer.write("      \"defeat\": " + place.isDefeat() + "\n");
                writer.write("    }" + (i < w.listePlaces.size() - 1 ? "," : "") + "\n");
            }
            writer.write("  ],\n");

            // Ecritured de Path
            writer.write("  \"paths\": [\n");
            for (int i = 0; i < w.listePaths.size(); i++) {
                Path path = w.listePaths.get(i);
                writer.write("    {\n");
                writer.write("      \"firstPlace\": " + path.getFirstPlace().getId() + ",\n");
                writer.write("      \"secondPlace\": " + path.getSecondePlace().getId() + ",\n");
                writer.write("      \"distance\": " + path.length + "\n");
                writer.write("    }" + (i < w.listePaths.size() - 1 ? "," : "") + "\n");
            }
            writer.write("  ]\n");
            writer.write("}\n");
            System.out.println("Ecriture du fichier terminée.");
        }
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
	
	public static void loadWorld(InputStream input) {
		// TODO
	}
	
	public static void main(String[] args) throws IOException, ExistPlaceException, UnknownPlaceException {
		World world = new World("world_test");
    	Place placeStart = new Place(1, "Point de départ", new Monster(50, 50, 100, 60, new ArrayList<Spell>(), "Ogre Malveillant"), "Bienvenue dans le monde.", false, true, false);
    	Place placeEnd = new Place(2, "Place du village", new Monster(150, 150, 25, 70, new ArrayList<Spell>(), "Gobelin suspicieux"), "Aller chercher du pain à la boulangerie et éviter le gobelin suspiceux.", true, false, false);
    	world.addPlace(placeStart);
    	world.addPlace(placeEnd);
    	Path path = new Path(placeStart, placeEnd);
    	path.setLength(24);
    	world.addPath(path);
		saveWorld(world, new File("world_test.json"));
	}
	
}
