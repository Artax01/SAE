package groupe6.ldveh.model.map;

import org.junit.jupiter.api.Test;

import groupe6.ldveh.model.entity.Monster;
import groupe6.ldveh.model.logic.ExistPlaceException;
import groupe6.ldveh.model.logic.UnknownPlaceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

/**
 * Classe de test unitaire pour la classe World.
 * Elle teste l'ajout, la récupération de lieux, la gestion des chemins
 * ainsi que le comportement du cache.
 */
class WorldTest {
	
	 /**
     * Vérifie que l'on peut ajouter une place à un monde sans erreur.
     */
	@Test
    void testAddPlace() throws ExistPlaceException {
		World world = new World("monde test");
		Place place = new Place(1, "Start", new Monster(0, 0, 0, 0, null, "monstre"), "description de cette place", false, true, false);
        world.addPlace(place);
        assertTrue(world.listePlaces.contains(place)); // Vérifie que la place est bien ajoutée à la liste
    }
	
	/**
     * Vérifie qu'une exception est levée lorsqu'on ajoute deux fois la même place.
     */
    @Test
    void testAddExistingPlace() throws ExistPlaceException {
    	World world = new World("monde test");
    	Place place = new Place(1, "Start", new Monster(0, 0, 0, 0, null, "monstre"), "description de cette place", false, true, false);
        world.addPlace(place);
     // Ajout d'une place déjà existante doit lever une ExistPlaceException
        assertThrows(ExistPlaceException.class, () -> world.addPlace(place));
    }

    /**
     * Vérifie qu'on peut récupérer une place via son nom.
     */
    @Test
    void testGetPlaceFromName() throws ExistPlaceException {
    	World world = new World("monde test");
    	Place place = new Place(1, "Start", new Monster(0, 0, 0, 0, null, "monstre"), "description de cette place", false, true, false);
        world.addPlace(place);
        Place result = world.getPlaceFromName("Start");
        assertNotNull(result); // Le résultat ne doit pas être null
        assertEquals(place, result); // La place retournée doit être celle ajoutée
    }

    /**
     * Vérifie qu'on peut récupérer une place via son identifiant.
     */
    @Test
    void testGetPlaceFromId() throws ExistPlaceException {
    	World world = new World("monde test");
    	Place place = new Place(1, "Start", new Monster(0, 0, 0, 0, null, "monstre"), "description de cette place", false, true, false);
        world.addPlace(place);
        Place result = world.getPlaceFromId(1);
        assertNotNull(result); 
        assertEquals(place, result); 
    }

    /**
     * Vérifie que l'on peut ajouter un chemin entre deux lieux déjà existants.
     */
    @Test
    void testAddPathWithKnownPlaces() throws ExistPlaceException, UnknownPlaceException {
    	World world = new World("monde test");
    	Place placeStart = new Place(1, "Start", new Monster(0, 0, 0, 0, null, "monstre"), "description de cette place", false, true, false);
    	Place placeEnd = new Place(1, "End", new Monster(0, 0, 0, 0, null, "monstre"), "description de cette place", true, false, false);
    	world.addPlace(placeStart);
    	world.addPlace(placeEnd);
    	Path path = new Path(placeStart, placeEnd);
        world.addPath(path);
        assertTrue(world.listePaths.contains(path)); // Le chemin doit être ajouté à la liste
    }

    /**
     * Vérifie qu'une exception est levée si un chemin contient un lieu inconnu du monde.
     */
    @Test
    void testAddPathWithUnknownPlace() {
    	World world = new World("monde test");
    	Place placeStart = new Place(1, "Start", new Monster(0, 0, 0, 0, null, "monstre"), "description de cette place", false, true, false);
    	Place placeEnd = new Place(1, "End", new Monster(0, 0, 0, 0, null, "monstre"), "description de cette place", true, false, false);
    	Path path = new Path(placeStart, placeEnd);
    	// Les lieux ne sont pas encore ajoutés au monde -> exception attendue
        assertThrows(UnknownPlaceException.class, () -> world.addPath(path));
    }

    /**
     * Vérifie le comportement de getPathsFrom lorsqu'un cache vide est présent pour une place.
     * Dans ce cas, la méthode devrait retourner null.
     */
    @Test
    void testGetPathsFromWithCacheAndEndPlace() throws ExistPlaceException, UnknownPlaceException {
    	World world = new World("monde test");
    	Place placeStart = new Place(1, "Start", new Monster(0, 0, 0, 0, null, "monstre"), "description de cette place", false, true, false);
    	Place placeEnd = new Place(1, "End", new Monster(0, 0, 0, 0, null, "monstre"), "description de cette place", true, false, false);
        world.addPlace(placeStart);
        world.addPlace(placeEnd);
        Path path = new Path(placeStart, placeEnd);
        world.addPath(path);

        // Injection manuelle d'un cache vide pour placeEnd
        world.cache.put(placeEnd, new HashMap<>());
        HashMap<Path, Place> result = world.getPathsFrom(placeEnd);
        assertNull(result); // La méthode retourne null si aucun chemin depuis placeEnd
    }

    /**
     * Vérifie que getPathsFrom fonctionne correctement quand aucun cache n'existe encore.
     * Le chemin ajouté doit être retourné.
     */
    @Test
    void testGetPathsFromWithoutCache() throws ExistPlaceException, UnknownPlaceException {
    	World world = new World("monde test");
    	Place placeStart = new Place(1, "Start", new Monster(0, 0, 0, 0, null, "monstre"), "description de cette place", false, true, false);
    	Place placeEnd = new Place(1, "End", new Monster(0, 0, 0, 0, null, "monstre"), "description de cette place", true, false, false);
        world.addPlace(placeStart);
        world.addPlace(placeEnd);
        Path path = new Path(placeStart, placeEnd);
        world.addPath(path);
        HashMap<Path, Place> paths = world.getPathsFrom(placeStart);
        assertNotNull(paths); // Le résultat ne doit pas être null
        assertEquals(1, paths.size()); // Un seul chemin attendu
        assertTrue(paths.containsKey(path)); // Le chemin ajouté doit être dans les résultats
        assertEquals(placeEnd, paths.get(path)); // Le chemin doit mener à placeEnd
    }

}
