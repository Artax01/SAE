package groupe6.ldveh.model.fight;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import groupe6.ldveh.model.entity.Player;

class ManaGainTest {

    @Test
    void testManaGainIncreasesMana() {
        Player player = new Player(100, 100, 0, 0, new ArrayList<>(), 10, 50, new ArrayList<>());
        ManaGain gain = new ManaGain(5, 20);

        gain.apply(player, null);

        assertEquals(30, player.getCurrentMana());
    }
}