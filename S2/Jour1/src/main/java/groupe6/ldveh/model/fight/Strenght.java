package groupe6.ldveh.model.fight;

import groupe6.ldveh.model.entity.Entity;

/**
 * Temporarily boost the caster attack.
 */
public class Strenght extends Spell {

    public Strenght(int duration, int amount, int cost) {
        super(duration, amount, cost);
    }

    @Override
    public boolean apply(Entity caster, Entity target) {
        caster.setAttack(caster.getAttack() + amount);
        caster.getEffects().add(this);
        return true;
    }

    @Override
    protected boolean effect(Entity target) {
        return true;
    }
}
