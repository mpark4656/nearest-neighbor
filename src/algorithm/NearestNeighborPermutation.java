package algorithm;

import java.util.ArrayList;

/**
 * Nearest-Neighbor Permutation Algorithm
 * Enumerates all possible orderings of the set of points and then select
 * the ordering that minimizes the total length. Since all possible orderings
 * are considered, we are guaranteed to end up with the shortest possible tour.
 * But, this algorithm is extremely slow. This algorithm can't handle n > 10 in
 * a reasonable amount of time.
 */
public class NearestNeighborPermutation extends NearestNeighborAlgorithm {

    /**
     * Private member variables
     * visitedNodes: Ordered List of Nodes that represents the path the robot arm takes for the
     *               most optimal distance
     * paths: Permutations of nodes
     * solved: Flag to raise if a solution has been provided by this algorithm object so
     *         this object does not have to solve the same problem again
     */
    private ArrayList<Node> visitedNodes = new ArrayList<> ();
    private ArrayList<Path> paths = new ArrayList<> ();
    private boolean solved = false;

    /**
     * Constructor
     * @param lowest int the lowest point
     * @param highest int the highest point
     * @param initialPoint int the initial point
     * @param pointsToVisit int[] points to visit
     */
    public NearestNeighborPermutation(int lowest, int highest, int initialPoint, int[] pointsToVisit) {
        super(lowest, highest, initialPoint, pointsToVisit);
    }

    /**
     * Implement getSolution method to provide the solution from this algorithm
     * @return String solution path
     */
    @Override
    public String getSolution() {
        StringBuilder sb = new StringBuilder();

        if(!solved) {
            solve();
            solved = true;
        }

        for(Node node : visitedNodes) {
            sb.append(node.getPoint());
            sb.append(" ");
        }

        return sb.toString();
    }

    /**
     * Private method to solve the problem with this algorithm
     */
    private void solve() {
        final long startTime = System.currentTimeMillis();

        // Find the initial contact point
        Node initialNode = findNodeByPoint(nodesToVisit, initialPoint);

        // Variable used to find the shortest distance. Initialize this variable
        // to the maximum possible integer value
        int shortestDistance = Integer.MAX_VALUE;

        // The path that will be used in the first permutation method
        // Since the first contact point is always the first destination, it is added to
        // the starting path.
        Path startingPath = new Path();
        startingPath.addNode(initialNode);

        // This is the starting list of all nodes to visit
        // Remove the first contact point since it has been visited already
        // and it does not have to be visited again during permutation
        ArrayList<Node> startingNodes = new ArrayList<> (nodesToVisit);
        startingNodes.remove(initialNode);

        // Find paths permutation
        permutation(startingNodes, startingPath);

        // After permutation, add the first contact point to the end of the path
        // because the robot arm must go back to the initial point
        // At the same time, find the shortest total distance from these paths
        for(Path path : paths) {
            path.addNode(initialNode);

            if(path.getTotalDistance() < shortestDistance) {
                shortestDistance = path.getTotalDistance();
            }
        }

        // Get the first path with the shortest total distance
        Path optimalPath = findPathByTotalDistance(paths, shortestDistance);

        // Copy the nodes in the path with the shortest distance to visitedNodes
        visitedNodes.addAll(optimalPath.getNodes());

        final long endTime = System.currentTimeMillis();
        executionTime = endTime - startTime;
    }

    /**
     * Private recursive method that permutes all possible paths given the set of
     * unvisited points.
     * @param nodes ArrayList&lt;Node&gt; list of nodes
     * @param path Path path
     */
    private void permutation(ArrayList<Node> nodes, Path path) {
        // If there are no unvisited nodes, add the final path and exit permutation
        if(!unvisitedNodesExists(nodes, "U")) {
            paths.add(path);
            return;
        }

        for(Node node : nodes) {
            if(node.getFlag().equals("U")) {
                // Make a deep copy of the node array
                ArrayList<Node> newNodes = getDeepCopyOf(nodes);

                // Make a deep copy of the path
                Path newPath = new Path(path);

                // Set the flag of the corresponding node in the new copy
                Node newNode = newNodes.get(newNodes.indexOf(node));
                newNode.setFlag("V");

                // Add the node to the new copy of Path
                newPath.addNode(new Node(newNode));

                // Continue permutation with the new copies of nodes list and path
                // while this for loop continues processing next iteration with the
                // original nodes list and path
                permutation(newNodes, newPath);
            }
        }
    }

    /**
     * Private method to return a deep copy of the given node list
     * @param nodes ArrayList&lt;Node&gt; list of nodes to copy
     * @return ArrayList&lt;Node&gt; copy of the node list
     */
    private ArrayList<Node> getDeepCopyOf(ArrayList<Node> nodes) {
        ArrayList<Node> result = new ArrayList<> ();
        for(Node node : nodes) {
            result.add(new Node(node));
        }
        return result;
    }

    /**
     * Private method to return the first path in the list that has the given total distance
     * @param paths ArrayList&lt;Path&gt; Array List of Path
     * @param totalDistance int total distance
     * @return Path the first path that has the specified total distance
     */
    private Path findPathByTotalDistance(ArrayList<Path> paths, int totalDistance) {
        for(Path path : paths) {
            if(path.getTotalDistance() == totalDistance) {
                return path;
            }
        }
        return null;
    }

    /**
     * Path class encapsulates a collection of nodes in a specific order and
     * facilitates the calculation of the total distance of the path
     */
    private class Path {
        /**
         * Private member variables
         * nodes: Ordered list of nodes with the first node being the starting point and
         *        the last node being the destination
         * totalDistance: Total distance of this path. This is equal to the sum of all
         *                distances (Between P1 and P2 and between P2 and P3, ... , between Pn-1 and Pn).
         */
        private ArrayList<Node> nodes = new ArrayList<> ();
        private int totalDistance = 0;

        /**
         * Private Default Constructor
         */
        private Path() { }

        /**
         * Copy Constructor
         * @param path Path path to copy
         */
        private Path(Path path) {
            for(Node node : path.getNodes()) {
                this.addNode(new Node(node));
            }
        }

        /**
         * Private method to add a node at the end of the array list and
         * recalculate the total distance
         * @param node Node node to add at the end of the list
         */
        private void addNode(Node node) {
            if(nodes.size() > 0) {
                totalDistance += getShortestDistanceBetween(nodes.get(nodes.size() -1), node);
            }
            nodes.add(node);
        }

        /**
         * Private method to return the total distance of this path
         * @return int total distance
         */
        private int getTotalDistance() {
            return totalDistance;
        }

        /**
         * Private method to return the node list
         * @return ArrayList&lt;Node&gt; nodes
         */
        private ArrayList<Node> getNodes() {
            return nodes;
        }
    }
}
