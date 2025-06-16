package groupe6.ldveh.model.fight;

import groupe6.ldveh.model.entity.Entity;

/**
 * Apply poison to a target.
 */
public class Poison extends Spell {

    public Poison(int duration, int cost, int amount) {
        super(duration, amount, cost);
    }

    @Override
    public boolean apply(Entity caster, Entity target) {
        target.getEffects().add(this);
        return true;
    }

    @Override
    protected boolean effect(Entity target) {
        int newHP = Math.max(target.getCurrentHP() - amount, 0);
        target.setCurrentHP(newHP);
        return true;
    }
}
