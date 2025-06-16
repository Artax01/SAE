package app.ai;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.Main;
import app.ai.world.WorldAnalyzer;
import app.model.map.Place;
import app.model.map.World;
import app.model.parser.WorldIO;

/**
 * Unit test class to validate the functionality of the WorldAnalyzer,
 * including graph connectivity and shortest path calculations using Dijkstra's algorithm.
 */
public class DijkstraTest {

	World w;
    WorldAnalyzer analyzer;

    /**
     * Sets up the test environment by loading the world from a JSON file
     * and initializing the world analyzer.
     *
     * @throws Exception if the world fails to load.
     */
    @BeforeEach
    public void setUp() throws Exception {
        w = WorldIO.loadWorld(Main.class.getResourceAsStream("Monde1.json"));
        analyzer = new WorldAnalyzer(w);
    }
    
    /**
     * Tests whether the world is connected,
     * meaning all places can be reached from any other place.
     */
    @Test
    public void testConnex() {
        assertTrue(analyzer.isConnexe());
    }

    /**
     * Tests that the shortest distance between two specific places is correctly calculated.
     *
     * @return the minimum distance expected between place with ID 1 and place with ID 6, which is 12.
     */
    @Test
    public void testLessDistanceToReach() {
        assertEquals(12, analyzer.lessDistanceToReach(w.getPlaceFromId(1), w.getPlaceFromId(6)));
    }

    /**
     * Tests that Dijkstra's algorithm returns a non-empty list of steps from a starting point.
     *
     * @return a list of steps where each step is a map of places and their computed distances.
     */
    @Test
    public void testDijkstraPremiereEtape(){
        List<Map<Place, Integer>> etapes = analyzer.dijkstraWithSteps(w.getPlaceFromId(1));

        assertFalse(etapes.isEmpty(), "La liste des étapes ne doit pas être vide");
    }
    
    /**
     * Tests that the specific place "tour du Salut" is reachable from the starting point.
     *
     * @return true if the place named "tour du Salut" is found in any of the steps.
     */
    @Test
    public void testDijkstraDernierEtapePasAccessible(){
        List<Map<Place, Integer>> etapes = analyzer.dijkstraWithSteps(w.getPlaceFromId(1));


        boolean destinationTrouvee = etapes.stream().anyMatch(etape -> etape.keySet().stream().anyMatch(p -> p.getName().equals("tour du Salut")));
        assertTrue(destinationTrouvee, "Le lieu 'tour du Salut' doit être atteignable depuis le point de départ");
    }

    @Test
    public void testLessDistanceToReach2() {
        assertEquals(16, analyzer.lessDistanceToReach(w.getPlaceFromId(1), w.getPlaceFromId(8))); // la distance minimale pour aller de 1 à 8 est 16
    }

    @Test
    public void testLessDistanceToReach3() {
        assertEquals(9, analyzer.lessDistanceToReach(w.getPlaceFromId(1), w.getPlaceFromId(9))); // la distance minimale pour aller de 1 à 9 est 9
    }

    @Test
    public void testLessDistanceToReach4() {
        assertEquals(21, analyzer.lessDistanceToReach(w.getPlaceFromId(2), w.getPlaceFromId(10))); // la distance minimale pour aller de 2 à 10 est 21
    }

    @Test
    public void testDijkstra() {

        List<Map<Place,Integer>> liste = analyzer.dijkstraWithSteps(w.getPlaceFromId(1));


        assertEquals(14,liste.get(2).get(w.getPlaceFromId(6))); // à l'étape 3, la valeur pour aller de 1 à 6 est de 14
        assertEquals(12,liste.get(19).get(w.getPlaceFromId(6))); // à l'étape 20, la valeur pour aller de 1 à 6 est de 12



    }
}
