package app.ai;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DFS method in the Solver class.
 */
public class DFSTest {

    private final Solver<String> solver = new Solver<>();

    /**
     * Minimal concrete implementation of Node<String> for testing.
     * Represents a test node with a name, goal status, and neighbors.
     */
    private static class TestNode extends Node<String> {

        private final boolean goal;
        private final Map<INode<String>, Integer> neighbors = new HashMap<>();

        /**
         * Constructs a TestNode.
         * 
         * @param path the name or identifier of the node
         * @param goal true if this node is a goal node, false otherwise
         */
        public TestNode(String path, boolean goal) {
            this.path = path;
            this.goal = goal;
        }

        /**
         * Constructs a TestNode.
         * 
         * @param path the name or identifier of the node
         * @param goal true if this node is a goal node, false otherwise
         */
        public void addNeighbor(INode<String> node, int cost) {
            neighbors.put(node, cost);
        }

        /**
         * Checks if this node is a goal node.
         * 
         * @return true if this node is a goal, false otherwise
         */
        @Override
        public boolean isGoal() {
            return goal;
        }

        /**
         * Checks if this node is a deadlock.
         * 
         * @return false as deadlock state is not used in these tests
         */
        @Override
        public boolean isDeadLock() {
            return false; // not used for DFS tests
        }

        /**
         * Returns the neighbors of this node with their associated costs.
         * 
         * @return map of neighbor nodes and costs
         */
        @Override
        public Map<INode<String>, Integer> getNeighbors() {
            return neighbors;
        }

        /**
         * Returns the path or name of this node.
         * 
         * @return the path string
         */
		@Override
		public String getPath() {
			return path;
		}
    }

    /**
     * Tests that DFS returns the goal node when it is reachable.
     */
    @Test
    public void ReturnsDirectGoalNeighbor() {
        TestNode start = new TestNode("Start", false);
        TestNode goal = new TestNode("Goal", true);
        start.addNeighbor(goal, 1);

        INode<String> result = solver.DFS(start);

        assertNotNull(result);
        assertEquals("Goal", result.getPath());
    }


    /**
     * Tests that DFS returns null when no goal node is reachable.
     */
    @Test
    public void ReturnsNullWhenNoGoalInGraph() {
        TestNode start = new TestNode("Start", false);
        TestNode n1 = new TestNode("Node1", false);
        TestNode n2 = new TestNode("Node2", false);
        start.addNeighbor(n1, 1);
        n1.addNeighbor(n2, 1);

        INode<String> result = solver.DFS(start);

        assertNull(result);
    }

    /**
     * Tests that DFS avoids revisiting nodes (detects cycles correctly).
     */
    @Test
    public void FindsGoalInCyclicGraph() {
        TestNode a = new TestNode("A", false);
        TestNode b = new TestNode("B", false);
        TestNode c = new TestNode("C", true);

        a.addNeighbor(b, 1);
        b.addNeighbor(a, 1); // cycle back to A
        b.addNeighbor(c, 1);

        INode<String> result = solver.DFS(a);

        assertNotNull(result);
        assertEquals("C", result.getPath());
    }

    /**
     * Tests that DFS returns the starting node itself if it is already the goal.
     */
    @Test
    public void ReturnsStartNodeIfItIsGoal() {
        TestNode start = new TestNode("Start", true);

        INode<String> result = solver.DFS(start);

        assertNotNull(result);
        assertEquals("Start", result.getPath());
    }
}