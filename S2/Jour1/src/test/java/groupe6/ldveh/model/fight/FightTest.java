package groupe6.ldveh.model.fight;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import groupe6.ldveh.model.entity.Entity;
import groupe6.ldveh.model.entity.Monster;
import groupe6.ldveh.model.entity.Player;

class FightTest {

    private Player player;
    private Monster monster;
    private Fight fight;

    @BeforeEach
    void setup() {
        player = new Player(100, 100, 5, 10, new ArrayList<>(), 50, 50, new ArrayList<>());
        monster = new Monster(50, 50, 2, 8, new ArrayList<>(), null);
        fight = new Fight(player, monster);
    }

    @Test
    void testBasicAttackKillsMonster() {
        monster.setCurrentHP(10);
        monster.setArmor(0);
        player.setAttack(15);

        Entity winner = fight.turn(null);
        assertEquals(player, winner);
    }

    @Test
    void testSpellNotEnoughMana() {
        player.setCurrentMana(0);
        Spell expensiveSpell = new Poison(2, 50, 5);
        Entity winner = fight.turn(expensiveSpell);

        assertEquals(42, monster.getCurrentHP());
    }
}
