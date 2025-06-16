package app.ai;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import app.model.map.Path;
import app.ai.INode;
import app.model.entity.Monster;
import app.model.entity.Player;
import app.model.fight.Spell;

/**
 * A node used in the A* algorithm that minimizes mana consumption
 * while resolving a fight between a player and a monster.
 *
 * @param <T> The type used in the pathfinding graph.
 */
public class LessManaNode<T> extends FightNode<T> {

    /**
     * Constructs a new LessManaNode with the given parameters.
     *
     * @param parent  The parent node from which this node is generated.
     * @param cost    The accumulated mana cost to reach this node.
     * @param path    The path taken to reach this node (can be null).
     * @param player  A copy of the player in the current state.
     * @param monster A copy of the monster in the current state.
     */
    public LessManaNode(INode<T> parent, int cost, Path path, Player player, Monster monster) {
        super(parent, cost, path, player, monster);
    }

    /**
     * Returns the heuristic used by the A* algorithm for this node.
     * Here, the heuristic is simply the current cost (mana used so far).
     *
     * @return The current cost of reaching this node.
     */
    @Override
    public int getHeuristic() {
        return cost;
    }

    /**
     * Determines whether this node represents a goal state.
     * The goal is reached when the monster is dead and the player is still alive.
     *
     * @return true if the monster is dead and the player is alive, false otherwise.
     */
    @Override
    public boolean isGoal() {
        return monster.isDead() && player.isAlive();
    }

    /**
     * Determines whether this node is a deadlock state.
     * A deadlock occurs when the player is dead and the monster is still alive.
     *
     * @return true if the player is dead and the monster is alive, false otherwise.
     */
    @Override
    public boolean isDeadLock() {
        return monster.isAlive() && player.isDead();
    }

    /**
     * Generates all valid neighbor nodes from the current game state.
     * This includes basic attacks and all castable spells.
     * Each neighbor is created using clones of the player and monster to ensure
     * state isolation and prevent side effects.
     *
     * @return A map of neighboring nodes and their associated move costs.
     */
    @Override
    public Map<INode<T>, Integer> getNeighbors() {
        Map<INode<T>, Integer> neighbors = new HashMap<>();
        Set<String> visitedStates = new HashSet<>();

        // Simulate a basic attack (no spell)
        {
            Player p = player.clone();
            Monster m = monster.clone();

            p.applyEffects();
            m.applyEffects();

            p.attack(m);
            if (m.isAlive()) m.attack(p);

            String state = encodeState(p, m);
            if (visitedStates.add(state)) {
                LessManaNode<T> neighbor = new LessManaNode<>(this, cost, path, p, m);
                neighbors.put(neighbor, 1);
            }
        }

        // Simulate all usable spells
        for (Spell spell : player.availableSpells()) {
            Player p = player.clone();
            Monster m = monster.clone();

            boolean castSuccess = spell.isSelfSpell() ? p.castSpell(spell, p) : p.castSpell(spell, m);
            if (!castSuccess) continue;

            p.applyEffects();
            m.applyEffects();

            if (m.isAlive()) m.attack(p);

            String state = encodeState(p, m);
            if (visitedStates.add(state)) {
                LessManaNode<T> neighbor = new LessManaNode<>(this, cost + spell.getCost(), path, p, m);
                neighbors.put(neighbor, 1);
            }
        }

        return neighbors;
    }

    /**
     * Encodes the current state of the player and monster into a string
     * to detect duplicate states during neighbor generation.
     *
     * @param p The player object.
     * @param m The monster object.
     * @return A unique string representing the simplified state.
     */
    private String encodeState(Player p, Monster m) {
        return p.getCurrentHP() + "," + p.getCurrentMana() + "," + m.getCurrentHP();
    }

    /**
     * Determines whether this node is equal to another object.
     * Two nodes are considered equal if the player and monster states are equal.
     * The cost is not considered in equality.
     *
     * @param obj The object to compare with.
     * @return true if the other object is a LessManaNode with the same player and monster state.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LessManaNode<?> that = (LessManaNode<?>) obj;
        return player.equals(that.player) && monster.equals(that.monster);
    }

    /**
     * Returns the hash code for this node.
     * The hash code is based on the player and monster states.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(player, monster);
    }
}