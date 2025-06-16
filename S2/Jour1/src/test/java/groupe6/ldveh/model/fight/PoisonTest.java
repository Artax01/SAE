package groupe6.ldveh.model.fight;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import groupe6.ldveh.model.entity.Monster;

class PoisonTest {

    @Test
    void testPoisonReducesHealth() {
        Monster monster = new Monster(30, 30, 0, 0, new ArrayList<>(), null);
        Poison poison = new Poison(3, 5, 4);
        poison.apply(null, monster);

        assertEquals(1, monster.getEffects().size());

        poison.applyEffect(monster);
        assertEquals(26, monster.getCurrentHP());
    }
}