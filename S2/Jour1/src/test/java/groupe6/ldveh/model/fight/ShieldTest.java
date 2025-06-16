package groupe6.ldveh.model.fight;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import groupe6.ldveh.model.entity.Player;

class ShieldTest {

    @Test
    void testShieldBoostsArmor() {
        Player player = new Player(100, 100, 5, 0, new ArrayList<>(), 20, 30, new ArrayList<>());
        Shield shield = new Shield(2, 5, 10);

        shield.apply(player, null);
        assertEquals(15, player.getArmor());
    }
}