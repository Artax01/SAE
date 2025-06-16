package app.ai;

import app.model.map.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.ai.INode;
import app.ai.INodeStar;
import app.model.entity.Monster;
import app.model.entity.Player;

public abstract class FightNode<T> implements INodeStar<T> {

    protected final INode<T> parent;
    protected final int cost;
    protected final Path path;
    protected final Player player;
    protected final Monster monster;

    /**
     * Constructor to initialize the node with parent, cost, path, player, and monster.
     *
     * @param parent  The parent node.
     * @param cost    The cost to reach this node.
     * @param path2   The path taken to reach this node.
     * @param player  The player entity.
     * @param monster The monster entity.
     */
    public FightNode(INode<T> parent, int cost, app.model.map.Path path2, Player player, Monster monster) {
        this.parent = parent;
        this.cost = cost;
        this.path = (Path) path2;
        this.player = player;
        this.monster = monster;
    }

    /**
     * Get the cost to reach this node.
     *
     * @return The cost of the node.
     */
    @Override
    public int getCost() {
        return cost;
    }

    /**
     * Get the parent node.
     *
     * @return The parent node.
     */
    @Override
    public INode<T> getParent() {
        return parent;
    }

    /**
     * Rebuild the path from the current node to the root node.
     *
     * @return A list of nodes representing the path from the root to this node.
     */
    @Override
    public List<INode<T>> rebuildPath() {
        List<INode<T>> pathList = new ArrayList<>();
        INode<T> current = this;
        while (current != null) {
            pathList.add(current);
            current = current.getParent();
        }
        Collections.reverse(pathList);
        return pathList;
    }

    /**
     * Get the path associated with this node.
     *
     * @return The path of type T.
     */
    @Override
    public T getPath() {
        return (T) path;
    }
}
