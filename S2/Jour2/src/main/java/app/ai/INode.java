package app.ai;

import java.util.List;
import java.util.Map;

import app.model.entity.Player;


/**
 * Interface representing a node in a graph for pathfinding algorithms.
 * @param <T> The type of the path or data stored in the node.
 */
public interface INode<T> {
	
	/**
     * Checks if the current node is a goal node.
     * @return true if the current node is a goal node, otherwise false.
     */
	public boolean isGoal();
	
	/**
     * Checks if the current node is in a deadlock state.
     * @return true if the current node is in a deadlock state, otherwise false.
     */
	public boolean isDeadLock();
	
	/**
     * Retrieves the neighbors of the current node along with the cost to reach each neighbor.
     * @return A map of neighboring nodes and their associated costs.
     */
	public Map<INode<T>, Integer> getNeighbors();
	
	/**
     * Rebuilds the path from the root node to the current node.
     * @return A list of nodes representing the path from the root to the current node.
     */
	public List<INode<T>> rebuildPath();
	
	/**
     * Retrieves the path data stored in the node.
     * @return The path data of type T.
     */
	public T getPath();
	
	/**
     * Retrieves the parent node of the current node.
     * @return The parent node.
     */
	public INode<T> getParent();
	
	/**
     * Retrieves the cost to reach the current node from the root node.
     * @return The cost to reach the current node.
     */
	public int getCost();
}
