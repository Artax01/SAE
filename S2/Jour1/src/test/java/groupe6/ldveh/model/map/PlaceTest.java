package groupe6.ldveh.model.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import groupe6.ldveh.model.entity.Monster;

class PlaceTest {

	Monster monster;
	Place place;
	
	PlaceTest() {
		monster = new Monster(0, 0, 0, 0, null, "monstre");
		place = new Place(1, "Place de départ", monster, "Une place très calme.", false, true, false);
	}

	@Test
	void testGetName() {
		assertEquals("Place de départ", place.getName(), "Le nom de la place devrait être 'Place de départ'.");
	}

	@Test
	void testGetId() {
		assertEquals(1, place.getId(), "L'ID de la place devrait être 1.");
	}

	@Test
	void testIsEnd() {
		assertFalse(place.isEnd(), "La place ne doit pas être une place de fin.");
	}

	@Test
	void testIsStart() {
		assertTrue(place.isStart(), "La place doit être une place de départ.");
	}

	@Test
	void testIsDefeat() {
		assertFalse(place.isDefeat(), "La place ne doit pas être une place de défaite.");
	}

	@Test
	void testGetMonster() {
		assertNotNull(place.getMonster(), "Le monstre ne doit pas être nul.");
	}

	@Test
	void testGetDescription() {
		assertEquals("Une place très calme.", place.getDescription(),
				"La description de la place devrait être correcte.");
	}

	@Test
	void testSetDescription() {
		place.setDescription("Une place dangereuse.");
		assertEquals("Une place dangereuse.", place.getDescription(),
				"La description de la place devrait être modifiée.");
	}

	@Test
	void testSetStart() {
		place.setStart(false);
		assertFalse(place.isStart(), "La place ne doit plus être une place de départ.");
	}

	@Test
	void testSetDefeat() {
		place.setDefeat(true);
		assertTrue(place.isDefeat(), "La place doit maintenant être une place de défaite.");
	}

	@Test
	void testSetMonster() {
		Monster newMonster = new Monster(0, 0, 0, 0, null, "Goblin");
		place.setMonster(newMonster);
	}

}
