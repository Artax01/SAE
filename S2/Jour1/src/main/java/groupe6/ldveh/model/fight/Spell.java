package groupe6.ldveh.model.fight;

import groupe6.ldveh.model.entity.Entity;

/**
 * Abstract class for spells that can be cast in combat.
 */
public abstract class Spell {
    protected int duration;
    protected int nbTurnLeft;
    protected int amount;
    protected int cost;

    public Spell(int duration, int amount, int cost) {
        this.duration = duration;
        nbTurnLeft = duration;
        this.amount = amount;
        this.cost = cost;
    }

    public int getManaCost() {
        return cost;
    }

    /**
     * Apply the spell on cast (add it to effects or immediate effect).
     */
    public abstract boolean apply(Entity caster, Entity target);

    /**
     * Apply the effect for this turn, return false if duration has expired.
     */
    public boolean applyEffect(Entity target) {
        if (duration <= 0) return false;
        duration--;
        return effect(target);
    }

    /**
     * Effect logic on the entity.
     */
    protected abstract boolean effect(Entity target);
}
