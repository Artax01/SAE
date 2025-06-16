package app.model.entity;

import app.model.fight.Spell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Entity {
    protected int currentHP;
    protected int maximumHP;
    protected int armor;
    protected int attack;
    protected List<Spell> effects;

    public Entity(int currentHP, int maximumHP, int armor, int attack, List<Spell> effects) {
        this.currentHP = currentHP;
        this.maximumHP = maximumHP;
        this.armor = armor;
        this.attack = attack;
        this.effects = effects;
    }

    public Entity(int currentHP, int maximumHP, int armor, int attack) {
        this(currentHP, maximumHP, armor, attack, new ArrayList<>());
    }

    public Entity() {
        this(100, 100, 1, 1);
    }

    public void attack(Entity other) {
        int damage = attack - other.armor;
        if (damage <= 0) {
            damage = 1;
        }

        other.deal(damage);
    }

    public boolean isAlive() {
        return this.currentHP > 0;
    }

    public boolean isDead() {
        return this.currentHP <= 0;
    }

    /**
     * @param e Effect to add.
     * @return If the target is already affected by the spell, it will return false, true otherwise.
     */
    protected boolean addEffect(Spell e) {
        return addEffect(e, true);
    }

    protected boolean addEffect(Spell e, boolean applyBoost) {
        if (effects.contains(e)) return false;

        this.effects.add(e);

        if (applyBoost && e.isBoost()) {
            e.applyEffect(this);
        }

        return true;
    }

    public void applyEffects() {
        for (int i = 0; i < effects.size(); i++) {
            Spell s = effects.get(i);
            if (s.apply(this)) {
                removeEffect(s);
                i--;
            }
        }
    }

    public void heal(int HP) {
        this.currentHP += HP;

        if (this.currentHP > this.maximumHP) {
            this.currentHP = this.maximumHP;
        }
    }

    public void deal(int HP) {
        this.currentHP -= HP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getMaximumHP() {
        return maximumHP;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public List<Spell> getEffects() {
        return effects;
    }

    public void removeEffect(Spell effect) {
        this.effects.remove(effect);
    }

    public String toString() {
        return "HP: [%d/%d], ARM: %d, ATT: %d, %s".formatted(currentHP, maximumHP, armor, attack, effects.isEmpty() ? "" : effects);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return currentHP == entity.currentHP && maximumHP == entity.maximumHP && armor == entity.armor && attack == entity.attack && Objects.equals(effects, entity.effects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentHP, maximumHP, armor, attack, effects);
    }

    public boolean isAffectedBy(Spell s) {
        return this.effects.contains(s);
    }
}
