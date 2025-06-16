package groupe6.ldveh.model.fight;

import java.util.Iterator;

import groupe6.ldveh.model.entity.Entity;
import groupe6.ldveh.model.entity.Monster;
import groupe6.ldveh.model.entity.Player;

/**
 * Manage a turn after turn fight between a Player and a Monster.
 */
public class Fight {
    private final Player player;
    private final Monster monster;

    public Fight(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
    }

    /**
     * Apply effects for both entity at the beginning turn.
     *
     * @return The winner the last survivor, null otherwise
     */
    public Entity newTurn() {
        applyEffects(player);
        applyEffects(monster);

        if (player.isDead()) return monster;
        if (monster.isDead()) return player;
        return null;
    }

    /**
     * Execute a turn of the fight.
     *
     * @param spell The spell the player want to cast, or null for a basic attack
     * @return The winner is the last survivor or null otherwise
     */
    public Entity turn(Spell spell) {
        if (spell != null) {
            if (player.spendMana(spell.getManaCost())) {
                spell.apply(player, monster);
            } else {
                System.out.println("Pas assez de mana pour utiliser le sort");
                player.attack(monster);
            }
        } else {
            player.attack(monster);
        }

        if (monster.isDead()) return player;

        monster.attack(player);

        if (player.isDead()) return monster;

        return null;
    }

    private void applyEffects(Entity entity) {
        Iterator<Spell> it = entity.getEffects().iterator();
        while (it.hasNext()) {
            Spell spell = it.next();
            if (!spell.applyEffect(entity)) {
                it.remove();
            }
        }
    }
}
