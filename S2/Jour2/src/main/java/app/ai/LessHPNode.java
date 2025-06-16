package app.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import app.model.fight.Spell;
import app.model.map.Path;
import app.model.entity.Player;
import app.model.entity.Monster;

public class LessHPNode<T> extends FightNode<T> {

	/**
	 * Constructor to initialize the node with parent, cost, path, player, and monster.
	 * @param parent  The parent node.
	 * @param cost    The cost to reach this node.
	 * @param path    The path taken to reach this node.
	 * @param player  The player entity.
	 * @param monster The monster entity.
	 */
    public LessHPNode(INode<T> parent, int cost, Path path, Player player, Monster monster) {
        super(parent, cost, path, player, monster);
    }

    /**
     * Heuristic function for A* algorithm, returns the cost.

     * @return The cost of the node.
     */
    @Override
    public int getHeuristic() {
        return player.getMaximumHP() - player.getCurrentHP();
    }

    /**
     * Get the player entity associated with this node.
     *
     * @return The player entity.
     */
    public Player getPlayer() {
        return player;
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

        {
            Player p = player.clone();
            Monster m = monster.clone();

            p.applyEffects();
            m.applyEffects();

            p.attack(m);
            if (m.isAlive()) {
                m.attack(p);
            }

            LessHPNode<T> neighbor = new LessHPNode<>(this, cost + 1, path, p, m);
            neighbors.put(neighbor, 1);
        }

        for (Spell spell : player.availableSpells()) {
            Player p = player.clone();
            Monster m = monster.clone();

            boolean castSuccess;
            if (spell.isSelfSpell()) {
                castSuccess = p.castSpell(spell, p);
            } else {
                castSuccess = p.castSpell(spell, m);
            }

            if (!castSuccess) {
                continue;
            }

            p.applyEffects();
            m.applyEffects();

            if (m.isAlive()) {
                m.attack(p);
            }

            int transitionCost = 1;
            LessHPNode<T> neighbor = new LessHPNode<>(this, this.cost + transitionCost, path, p, m);
            neighbors.put(neighbor, transitionCost);


        }

        return neighbors;
    }
    
    /**
     * Check if this node is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof LessHPNode)) return false;
        LessHPNode<?> other = (LessHPNode<?>) obj;
        return Objects.equals(player, other.player) && Objects.equals(monster, other.monster);
    }
    
    /**
     * Generate a hash code for this node.
     *
     * @return The hash code of the node.
     */
    @Override
    public int hashCode() {
        return Objects.hash(player, monster);
    }
}