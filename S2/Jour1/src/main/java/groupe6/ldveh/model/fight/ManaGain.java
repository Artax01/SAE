package groupe6.ldveh.model.fight;

import groupe6.ldveh.model.entity.Entity;
import groupe6.ldveh.model.entity.Player;

/**
 * Immediately restore mana to the player.
 */
public class ManaGain extends Spell {

    public ManaGain(int cost, int amount) {
        super(0, amount, cost); // no ongoing effect
    }

    @Override
    public boolean apply(Entity caster, Entity target) {
        if (caster instanceof Player player) {
            player.gainMana(amount);
            return true;
        }
        return false;
    }

    @Override
    protected boolean effect(Entity target) {
        return false;
    }
}
