package groupe6.ldveh.model.fight;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import groupe6.ldveh.model.entity.Player;

class HealTest {

    @Test
    void testHealEffect() {
        Player player = new Player(50, 100, 0, 0, new ArrayList<>(), 30, 30, new ArrayList<>());
        Heal heal = new Heal(2, 10, 20);

        heal.apply(player, null);
        assertTrue(player.getEffects().contains(heal));

        heal.applyEffect(player);
        assertEquals(70, player.getCurrentHP());
    }
}