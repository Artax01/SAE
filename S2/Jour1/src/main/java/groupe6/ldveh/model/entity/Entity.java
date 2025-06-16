package groupe6.ldveh.model.entity;

import java.util.List;

import groupe6.ldveh.model.fight.Spell;

/**
 * Base class for all characters or creatures in the game.
 * An Entity has health, armor, attack power, and effects (spells).
 */
public abstract class Entity {
    
    protected int currentHP;
    protected int maximumHP;
    protected int armor;
	protected int attack;
    protected List<Spell> effects;
    
    /**
     * Creates an entity with the given stats.
     *
     * @param currentHP  Current health
     * @param maximumHP  Maximum health
     * @param armor      Armor value
     * @param attack     Attack value
     * @param effects    List of spell effects
     */
    public Entity(int currentHP, int maximumHP, int armor, int attack, List<Spell> effects) {
        super();
        this.currentHP = currentHP;
        this.maximumHP = maximumHP;
        this.armor = armor;
        this.attack = attack;
        this.effects = effects;
    }

    /**
     * Attacks another entity. First reduces its armor,
     * then health if the attack is stronger than the armor.
     *
     * @param other The entity being attacked
     */
    public void attack(Entity other) {
        int restant = other.armor - attack;
        if (other.armor - attack <= 0) {
            other.armor = 0;
            other.currentHP -= attack - other.armor;
        } else {
            other.armor = other.armor - attack;
        }
    }


    /**
     * Checks if the entity is alive.
     *
     * @return true if HP > 0
     */
    public boolean isAlive() {
        return this.currentHP > 0;
    }

    /**
     * Checks if the entity is dead.
     *
     * @return true if HP <= 0
     */
    public boolean isDead() {
        return this.currentHP <= 0;
    }
    
    public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}

	public int getMaximumHP() {
		return maximumHP;
	}

	public void setMaximumHP(int maximumHP) {
		this.maximumHP = maximumHP;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}


	public void setAttack(int attack) {
		this.attack = attack;
	}

	public List<Spell> getEffects() {
		return effects;
	}

	public void setEffects(List<Spell> effects) {
		this.effects = effects;
	}
	
	public int getCurrentHP() {
		return currentHP;
	}
	
	
	public int getArmor() {
		return armor;
	}

	public int getAttack() {
		return attack;
	}
}
