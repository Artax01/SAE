package app.ai.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.model.entity.Monster;
import app.model.entity.Player;
import app.model.map.Path;
import app.model.map.Place;
import app.model.map.World;

/**
 * Generates a random world with the given parameters
 */
public class WorldGenerator {

    /**
     * Generates a random world with the given parameters
     *
     * @param name                  The name of the world
     * @param nbPlace               The number of places in the world
     * @param p                     The player (not used but included for potential extension)
     * @param percentageEndPoint   Percentage of end points (between 0 and 1)
     * @param percentageStartPoint Percentage of start points (between 0 and 1)
     * @param percentageDefeatPoint Percentage of defeat points (between 0 and 1)
     * @param percentageCoverage   Percentage of coverage (between 0 and 1)
     * @return The generated world
     * @throws Exception 
     * @throws IllegalArgumentException If the number of places is less than 2 or if the percentages are invalid
     */
    public static World createWorld(String name, int nbPlace, Player p,
                                     float percentageEndPoint, float percentageStartPoint,
                                     float percentageDefeatPoint, float percentageCoverage) throws Exception {

    	if (nbPlace < 2) {
			throw new IllegalArgumentException("nbPlace ne peut être inférieur à 2");
		}

    	if (percentageEndPoint < 0 || percentageEndPoint > 1) {
			throw new IllegalArgumentException("percentageEndPoint est compris entre 0 et 1");
		}
		
		if (percentageStartPoint < 0 || percentageStartPoint > 1) {
			throw new IllegalArgumentException("percentageStartPoint est compris entre 0 et 1");
		}
		
		if (percentageDefeatPoint < 0 || percentageDefeatPoint > 1) {
			throw new IllegalArgumentException("percentageDefeatPoint est compris entre 0 et 1");
		}
		
		if (percentageCoverage < 0 || percentageCoverage > 1) {
			throw new IllegalArgumentException("percentageCoverage est compris entre 0 et 1");
		}

		if ((percentageEndPoint + percentageStartPoint + percentageDefeatPoint) < 0 || (percentageEndPoint + percentageStartPoint + percentageDefeatPoint) > 1) {
			throw new IllegalArgumentException("la somme de percentageEndPoint, percentageStartPoint et percentageDefeatPoint est compris entre 0 et 1");
		}

        Random random = new Random();
        List<Place> places = new ArrayList<>();
        List<Path> paths = new ArrayList<>();

        for (int i = 0; i < nbPlace; i++) {
            boolean isStart = random.nextFloat() < percentageStartPoint;
            boolean isEnd = random.nextFloat() < percentageEndPoint;
            boolean isDefeat = random.nextFloat() < percentageDefeatPoint;
            Monster monster = random.nextBoolean() ? new Monster("Monster " + i, 100, 100, 10, 10) : null;

            Place place = new Place(i, "Place " + i, monster, "Description of Place " + i, null, isStart, isEnd, isDefeat);
            places.add(place);
        }
        
        if (p == null) {
		    throw new IllegalArgumentException("Player cannot be null");
		}

        if (places.stream().noneMatch(Place::isStart)) {
            places.get(0).isStartProperty().set(true);
        }

        if (places.stream().noneMatch(Place::isEnd)) {
            places.get(places.size() - 1).isEndProperty().set(true);
        }
        
        if (places.stream().noneMatch(Place::isDefeat)) {
            places.get(0).isDefeatProperty().set(true);
        }

        for (int i = 0; i < nbPlace; i++) {
            for (int j = i + 1; j < nbPlace; j++) {
                if (random.nextFloat() < percentageCoverage) {
                    Path path = new Path(places.get(i), places.get(j), random.nextInt(10) + 1);
                    paths.add(path);
                }
            }
        }

        World world = new World(name);
        WorldAnalyzer analyzer = new WorldAnalyzer(world);
       
        if (!analyzer.isConnexe()) {
        	throw new Exception("Le monde n'est pas connexe, on ne peut donc pas aller partout");
        }
        
        places.forEach(world::addPlace);
        paths.forEach(world::addPath);

        return world;
    }
}
