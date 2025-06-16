package app.ai;

/**
 * Interface representing a node in a graph for pathfinding algorithms with heuristic support
 * Extends the INode interface to include heuristic evaluation
 * @param <T> The type of the path or data stored in the node
 */
public interface INodeStar<T> extends INode<T> {

	/**
     * Retrieves the heuristic value for the current node
     * The heuristic is used to estimate the cost from the current node to the goal node
     * @return The heuristic value for the current node
     */
	public int getHeuristic();
	
}
