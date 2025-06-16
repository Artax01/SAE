package groupe6.ldveh.model.fight;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import groupe6.ldveh.model.entity.Player;

class StrenghtTest {

    @Test
    void testStrengthBoostsAttack() {
        Player player = new Player(100, 100, 0, 10, new ArrayList<>(), 20, 30, new ArrayList<>());
        Strenght strength = new Strenght(3, 5, 5);

        strength.apply(player, null);
        assertEquals(15, player.getAttack());
    }
}