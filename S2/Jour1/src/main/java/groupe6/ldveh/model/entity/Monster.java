package groupe6.ldveh.model.entity;

import java.util.ArrayList;

import java.util.List;

import groupe6.ldveh.model.fight.Spell;
import groupe6.ldveh.model.json.JSONObject;

/**
 * A Monster is an enemy in the game. It has health, armor, attack power,
 * magic effects (spells), and a name. Inherits from Entity.
 */
public class Monster extends Entity {

    /** The monster's name. */
    private String name;

    /**
     * Creates a monster with given stats and name.
     *
     * @param currentHP  Current health
     * @param maximumHP  Maximum health
     * @param armor      Armor value
     * @param attack     Attack value
     * @param effects    List of spells
     * @param n          Name of the monster
     */
    public Monster(int currentHP, int maximumHP, int armor, int attack, List<Spell> effects, String n) {
        super(currentHP, maximumHP, armor, attack, effects);
        this.name = n;
    }
	
	/**
     * Loads monster data from a JSON object.
     *
     * @param json JSON data
     * @return 
     */
    public Monster createMonsterFromJSON(JSONObject json) {
    	
    	int currentHP = json.getNumber("HP").intValue();
        int maximumHP = json.getNumber("HP").intValue();
        int armor = json.getNumber("Armor").intValue();
        int attack = json.getNumber("Attack").intValue();
        String name = json.getNumber("name").toString();

        List<Spell> effects = new ArrayList<>();
        
        return new Monster(currentHP, maximumHP, armor, attack, effects, name);
    }

    /**
     * Returns the monster as a JSON string.
     *
     * @return JSON string
     */
    public String asJSON() {
        return "\"name" + ": " + "\"" + name + "\",\n" +
               "\"HP" + ": " + "\"" + currentHP + "\",\n" +
               "\"Armor" + ": " + "\"" + armor + "\",\n" +
               "\"Attack" + ": " + "\"" + attack + "\",";
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}