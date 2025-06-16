package app.ai;

import app.model.entity.Monster;
import app.model.entity.Player;
import app.model.fight.Spell;
import app.model.fight.spells.*;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the {@link Solver} class.
 * This class contains unit tests to evaluate the performance of the A* algorithm
 * implemented with different node types: minimizing mana consumption, minimizing HP loss,
 * and minimizing the number of turns to defeat a monster.
 * Each test sets up a player, a monster, and a specific start node type,
 * then applies the A* algorithm to verify that the goal is successfully achieved.
 *
 * @see Solver
 * @see LessManaNode
 * @see LessHPNode
 * @see LessTurnNode
 * @see Spell
 */
public class SolverTest {

    /**
     * Tests the A* algorithm using a {@link LessManaNode} as the starting point.
     * <p>
     * The goal is to minimize mana consumption.
     * This test verifies that the total mana cost to reach the goal is zero,
     * meaning no spells were needed to win.
     */
    @Test
    public void testLessManaNode1AStar() {
        Player player = new Player(100, 100, 10, 20, 100);
        Monster monster = new Monster("", 100, 100, 10, 20);
        LessManaNode start = new LessManaNode(null, 0, null, player, monster);

        Solver<Spell> solver = new Solver<>();
        List<INode<Spell>> l = solver.AStar(start).rebuildPath();

        assertEquals(l.stream().map(INode::getCost).reduce(0, Integer::sum), 0);
    }

    /**
     * Tests the A* algorithm using a {@link LessHPNode} as the starting point.
     * <p>
     * The goal is to minimize health loss while defeating the monster.
     * This test verifies that:
     * <ul>
     *     <li>The player is still alive at the end of the path,</li>
     *     <li>The goal condition is satisfied,</li>
     *     <li>The total cost (representing HP loss) is as expected (17).</li>
     * </ul>
     */
    @Test
    public void testLessHPNode1AStar() {
        Player player = new Player(100, 100, 10, 20, 100, 100,
                new Poison(4, 20, 80),
                new ManaGain(4, 20, 30),
                new Shield(4, 10, 25));

        Monster monster = new Monster("", 210, 210, 10, 20);

        LessHPNode<Spell> start = new LessHPNode<>(null, 0, null, player, monster);

        Solver<Spell> solver = new Solver<>();

        List<INode<Spell>> l = solver.AStar(start).rebuildPath();

        LessHPNode solution = (LessHPNode) l.get(l.size() - 1);
        assertTrue(solution.getPlayer().isAlive());
        assertTrue(solution.isGoal());
        assertEquals(17, solution.getCost());
    }

    /**
     * Tests the A* algorithm using a {@link LessTurnNode} as the starting point.
     * <p>
     * The goal is to defeat the monster in the minimum number of turns.
     * This test verifies that the number of steps in the solution path is exactly 11.
     */
    @Test
    public void testLessTurnNode1AStar() {
        Player player = new Player(100, 100, 10, 20, 100, 100,
                new Poison(4, 20, 80),
                new ManaGain(4, 20, 30),
                new Shield(4, 10, 25));

        Monster monster = new Monster("", 210, 210, 10, 20);

        LessTurnNode start = new LessTurnNode(null, 0, null, player, monster);

        Solver<Spell> solver = new Solver<>();

        List<INode<Spell>> l = solver.AStar(start).rebuildPath();

        assertEquals(l.size(), 11);
    }

    /**
     * Tests the static method {@link Solver#canBeat(Player, Monster)} which determines whether
     * a player can theoretically defeat a given monster.
     * <p>
     * The test verifies various scenarios:
     * <ul>
     *     <li>The player can beat a reasonable monster,</li>
     *     <li>The player cannot beat an overwhelmingly strong monster,</li>
     *     <li>The player cannot beat a monster with extremely high damage,</li>
     *     <li>The player can beat a very weak monster.</li>
     * </ul>
     */
    @Test
    public void canBeat() {
        Player player = new Player(100, 100, 10, 20, 100, 100,
                new Poison(4, 20, 80),
                new ManaGain(4, 20, 30),
                new Shield(4, 10, 25));

        Monster monster = new Monster("", 210, 210, 10, 20);

        assertTrue(Solver.canBeat(player, monster));
        assertFalse(Solver.canBeat(player, new Monster("", 2500, 2500, 10, 20)));
        assertFalse(Solver.canBeat(player, new Monster("", 100, 100, 10, 500)));
        assertTrue(Solver.canBeat(player, new Monster("", 1, 1, 1, 5000)));
    }
}
