package app.ai;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a node in a graph for pathfinding algorithms
 * Implements the INode interface to provide common functionality for nodes
 * @param <T> The type of the path or data stored in the node
 */
public abstract class Node<T> implements INode<T> {
	
	protected int cost;
	protected INode<T> parent;
	protected T path;

	/**
     * Rebuilds the path from the root node to the current node by following parents
     * @return A list of nodes representing the path from the root to the current node
     */
	@Override
	public List<INode<T>> rebuildPath() {
		 List<INode<T>> list = new ArrayList<>();
		 INode<T> current = this;

		 while (current != null) {
			 list.add(0, current);
			 current = current.getParent();
		 }

		 return list;
	}

	/**
     * Retrieves the path data stored in the node
     * @return The path data of type T
     *
	@Override
	public T getPath() {
		return path;
	}

	/**
     * Retrieves the parent node of the current node
     * @return The parent node
     */
	@Override
	public INode<T> getParent() {
		return parent;
	}

	/**
     * Retrieves the cost to reach the current node from the root node
     * @return The cost to reach the current node
     */
	@Override
	public int getCost() {
		return cost;
	};
}
