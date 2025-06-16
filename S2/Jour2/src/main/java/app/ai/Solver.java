package app.ai;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

import app.model.entity.Monster;
import app.model.entity.Player;
import app.model.fight.Spell;

public class Solver<T> {
		
	/**
     * Performs a depth first search (DFS) on a graph starting from a given node
     *
     * @param <T> The type of the path or data stored in the node
     * @param startingNode The node from which the DFS starts
     * @return The goal node if found, otherwise null
     */
	public INode<T> DFS(INode<T> startingNode) {
		Stack<INode<T>> stack = new Stack<>();
		HashSet<INode<T>> visited = new HashSet<>();
        stack.push(startingNode);

        while (!stack.isEmpty()) {
            INode<T> currentNode = stack.pop();
            if (currentNode.isGoal()) {
                return currentNode;
            }
            visited.add(currentNode);
            for (INode<T> neighbor : currentNode.getNeighbors().keySet()) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }
        return null;
	}
	
	 /**
     * Performs an A* search on a graph starting from a given node
     *
     * @param <T> The type of the path or data stored in the node
     * @param startingNode The node from which the A* search starts
     * @return The goal node if found, otherwise null
     */
	public INodeStar<T> AStar(INodeStar<T> startingNode) {
		HashMap<INodeStar<T>, Integer> costs = new HashMap<INodeStar<T>, Integer>();
		PriorityQueue<INodeStar<T>> queue = new PriorityQueue<>(Comparator.comparingInt(INodeStar::getHeuristic));
		HashSet<INodeStar<T>> visited = new HashSet<INodeStar<T>>();
		
		costs.put(startingNode, 0);
		queue.add(startingNode);
		
		while (!queue.isEmpty()) {
			INodeStar<T> current = queue.poll();
			
			if (current.isGoal()) { 
				return current;
			}
			
			visited.add(current);
			
			for (Map.Entry<INode<T>, Integer> entry : current.getNeighbors().entrySet()) {
				INodeStar<T> neighbor = (INodeStar<T>) entry.getKey();
				int cost = entry.getValue();
				
				if (visited.contains(neighbor)) continue;
				
				int tentativeG = costs.get(current) + cost;

                if (!costs.containsKey(neighbor) || tentativeG < costs.get(neighbor)) {
                	costs.put(neighbor, tentativeG);
                	queue.add(neighbor);
                }
			}
			
		}
		
		return null;
	}
	
	/**
     * Checks if a player can beat a given monster
     *
     * @param p The player attempting to beat the monster
     * @param m The monster to be beaten
     * @return true if the player can beat the monster, otherwise false
     */
	
	public static boolean canBeat(Player p, Monster m) {
        int turnsToDefeat  = (int) Math.ceil((double) m.getCurrentHP() / p.getAttack());
        int turnsToSurvive = (int) Math.ceil((double) p.getCurrentHP() / m.getAttack());

        if (turnsToDefeat  <= turnsToSurvive) {
            return true;
        }

        for (Spell spell : p.availableSpells()) {
            int damage = spell.getCost();
            int turnsWithSpell  = (int) Math.ceil((double) m.getCurrentHP() / damage);

            if (turnsWithSpell  <= turnsToSurvive) {
                return true;
            }
        }

        return false;
	}

}
