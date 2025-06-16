package groupe6.ldveh.model.entity;

import java.util.List;

import groupe6.ldveh.model.fight.Spell;

/**
 * Represents the player character in the game.
 * The player can cast spells using mana.
 */
public class Player extends Entity {

    private int currentMana;
    private int maximumMana;
    private List<Spell> spells;

    /**
     * Constructs a new player.
     *
     * @param currentHP    Current health points
     * @param maximumHP    Maximum health points
     * @param armor        Armor value
     * @param attack       Attack value
     * @param effects      List of active effects
     * @param currentMana  Current mana
     * @param maximumMana  Maximum mana
     * @param spells       List of spells
     */
    public Player(int currentHP, int maximumHP, int armor, int attack, List<Spell> effects, int currentMana, int maximumMana, List<Spell> spells) {
        super(currentHP, maximumHP, armor, attack, effects);
        this.currentMana = currentMana;
        this.maximumMana = maximumMana;
        this.spells = spells;
    }

    /**
     * Attempts to consume mana for casting a spell.
     *
     * @param amount Mana required
     * @return true if the player had enough mana and it was consumed
     */
    public boolean spendMana(int amount) {
        if (currentMana >= amount) {
            currentMana -= amount;
            return true;
        }
        return false;
    }

    /**
     * Regains a certain amount of mana, without exceeding the maximum.
     *
     * @param amount Mana to regain
     */
    public void gainMana(int amount) {
        currentMana = Math.min(currentMana + amount, maximumMana);
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public int getMaximumMana() {
        return maximumMana;
    }

    public void setMaximumMana(int maximumMana) {
        this.maximumMana = maximumMana;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }
}
