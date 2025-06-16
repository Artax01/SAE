package groupe6.ldveh.model.fight;

import groupe6.ldveh.model.entity.Entity;

/**
 * Temporarily boost the caster armor.
 */
public class Shield extends Spell {

    public Shield(int duration, int amount, int cost) {
        super(duration, amount, cost);
    }

    @Override
    public boolean apply(Entity caster, Entity target) {
        caster.setArmor(caster.getArmor() + amount);
        caster.getEffects().add(this);
        return true;
    }

    @Override
    protected boolean effect(Entity target) {
        return true;
    }
}
