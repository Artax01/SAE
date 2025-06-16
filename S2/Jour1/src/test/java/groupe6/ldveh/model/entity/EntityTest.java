package groupe6.ldveh.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import groupe6.ldveh.model.json.JSONObject;

class EntityTest {
	


	@Test
    public void testConstructorAndGetters() {
        Monster m = new Monster(30, 40, 5, 7, Collections.emptyList(), "Goblin");
        assertEquals(30, m.getCurrentHP());
        assertEquals(40, m.getMaximumHP());
        assertEquals(5, m.getArmor());
        assertEquals(7, m.getAttack());
	}
	
	@Test
    public void testAttack_ReducesArmorOnly() {
        Monster attacker = new Monster(20, 20, 0, 3, Collections.emptyList(), "Goblin");
        
        Monster defender = new Monster(25, 25, 5, 2, Collections.emptyList(), "Goblin");
        attacker.attack(defender);

        assertEquals(2, defender.getArmor());
        assertEquals(25, defender.getCurrentHP());
    }
	
	@Test
    public void testAttack_ReducesArmorAndHP() {
        Monster attacker = new Monster(20, 20, 0, 10, Collections.emptyList(), "Dragon");
        Monster defender = new Monster(15, 15, 4, 2, Collections.emptyList(), "Knight");
        attacker.attack(defender);

        assertEquals(0, defender.getArmor());
        assertEquals(9, defender.getCurrentHP()); // 10 - 4 = 6 damage to HP
    }
	
	
	
	@Test
    public void testCreateMonsterFromJSON() {
        JSONObject json = new JSONObject();
        json.put("name", "Zombie");
        json.put("HP", 12);
        
        json.put("Armor", 3);
        json.put("Attack", 4);

        Monster m = new Monster(0, 0, 0, 0, null, null);
        m.createMonsterFromJSON(json);
        assertEquals("Zombie", m.getName());
        assertEquals(12, m.getCurrentHP());
        
        assertEquals(3, m.getArmor());
        assertEquals(4, m.getAttack());
    }
}
