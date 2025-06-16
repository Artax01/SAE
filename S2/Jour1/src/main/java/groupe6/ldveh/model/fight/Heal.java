package groupe6.ldveh.model.fight;

import groupe6.ldveh.model.entity.Entity;

/**
 * Heal the caster.
 */
public class Heal extends Spell {
	
    public Heal(int duration, int amount, int cost) {
        super(duration, amount, cost);
    }

    @Override
    public boolean apply(Entity caster, Entity target) {
        caster.getEffects().add(this);
        return true;
    }

    @Override
    protected boolean effect(Entity target) {
        int newHP = Math.min(target.getCurrentHP() + this.amount, target.getMaximumHP());
        target.setCurrentHP(newHP);
        return true;
    }
}
