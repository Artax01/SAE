package app.ai;

import java.util.HashMap;
import java.util.Map;

import app.model.map.Path;
import app.ai.INode;
import app.model.entity.Monster;
import app.model.entity.Player;
import app.model.fight.Spell;

public class LessTurnNode<T> extends FightNode<T> {

    /**
     * Constructor to initialize the node with parent, cost, path, player, and monster.
     *
     * @param parent  The parent node.
     * @param cost    The cost to reach this node.
     * @param path    The path taken to reach this node.
     * @param player  The player entity.
     * @param monster The monster entity.
     */
    public LessTurnNode(INode<T> parent, int cost, Path path, Player player, Monster monster) {
        super(parent, cost, path, player, monster);
    }

    /**
     * Heuristic function for A* algorithm, returns the cost.
     *
     * @return The cost of the node.
     */
    @Override
    public int getHeuristic() {
        return cost;
    }

    /**
     * Check if the current node is a goal state.
     *
     * @return True if the monster is dead and the player is alive, false otherwise.
     */
    @Override
    public boolean isGoal() {
        return monster.isDead() && player.isAlive();
    }

    /**
     * Check if the current node is a deadlock state.
     *
     * @return True if the monster is alive and the player is dead, false otherwise.
     */
    @Override
    public boolean isDeadLock() {
        return monster.isAlive() && player.isDead();
    }

    /**
     * Generate neighboring nodes for the A* algorithm.
     *
     * @return A map of neighboring nodes and their associated costs.
     */
    @Override
    public Map<INode<T>, Integer> getNeighbors() {
        Map<INode<T>, Integer> neighbors = new HashMap<>();

        // Simulate a turn where the player attacks without using a spell
        {
            Player p = player.clone();
            Monster m = monster.clone();

            p.applyEffects();
            m.applyEffects();

            p.attack(m);
            if (m.isAlive()) {
                m.attack(p);
            }

            LessTurnNode<T> neighbor = new LessTurnNode<>(this, cost + 1, path, p, m);
            neighbors.put(neighbor, 1);
        }

        // Simulate turns where the player uses each available spell
        for (Spell spell : player.availableSpells()) {
            Player p = player.clone();
            Monster m = monster.clone();

            boolean castSuccess = spell.isSelfSpell() ? p.castSpell(spell, p) : p.castSpell(spell, m);
            if (!castSuccess) continue;

            p.applyEffects();
            m.applyEffects();

            if (m.isAlive()) m.attack(p);

            LessTurnNode<T> neighbor = new LessTurnNode<>(this, cost + 1, path, p, m);
            neighbors.put(neighbor, 1);
        }

        return neighbors;
    }
}
