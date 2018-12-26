package algorithm;

import java.util.ArrayList;

/**
 * Nearest-Neighbor Heuristic Algorithm
 * From the initial point, we walk to its nearest unvisited neighbor
 * and repeat the process until we run out of unvisited points,
 * after which we return to the initial point.
 *
 * This algorithm is simple to understand and implement. It works
 * perfectly in some instances, but in other instances, it fails to find
 * the most optimal path. Therefore, this algorithm is not correct.
 *
 * This algorithm fails to find the optimal path in the following example:
 * lowest: -21
 * highest: 11
 * initial: 0
 * points: {-21, -5, -1, 0, 1, 3, 11}
 *
 * It will hopscotch left-right-left-right on this simple circuit board
 */
public class NearestNeighborHeuristic extends NearestNeighborAlgorithm {

    /**
     * Private member variables
     * visitedNodes: Ordered List of Nodes that represents the path the robot arm takes for the
     *               most optimal distance
     * solved: Flag to raise if a solution has been provided by this algorithm object so
     *         this object does not have to solve the same problem again
     */
    private ArrayList<Node> visitedNodes = new ArrayList<> ();
    private boolean solved = false;

    /**
     * Constructor
     * @param lowest The lowest point
     * @param highest The highest point
     * @param initialPoint The first contact point
     * @param pointsToVisit The set of points to be visited by the robot arm
     */
    public NearestNeighborHeuristic(int lowest, int highest, int initialPoint, int[] pointsToVisit) {
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
     *  Method to solve the problem and add visited nodes to visitedNodes array list
     */
    private void solve() {
        // Find the initial node in the nodesToVisit list
        Node initialNode = findNodeByPoint(nodesToVisit, initialPoint);

        // Set the current node as the initial node
        Node currentNode = initialNode;

        // Visit the initial node
        currentNode.setFlag("V");
        visitedNodes.add(currentNode);

        // Initialize the shortestDistance. This is used to find the nearest neighbor from
        // the current node.
        int shortestDistance;

        // Loop while unvisited nodes exist
        while(unvisitedNodesExists(nodesToVisit, "U")) {

            // Initialize shortestDistance as the highest possible distance on the board
            shortestDistance = highest - lowest + 1;

            // Loop through nodesToVisit to calculate the distance between the current node
            // and each unvisited node and get the shortest distance.
            for(Node node : nodesToVisit) {
                if(node.getFlag().equals("U")) {
                    int distance = getShortestDistanceBetween(currentNode, node);
                    node.setDistance(distance);

                    if(shortestDistance > distance) {
                        shortestDistance = distance;
                    }
                }
            }

            // Get the node that has the shortest distance from the current node
            Node nearestNode = findNodeByFlagAndDistance(nodesToVisit,"U", shortestDistance);

            // Visit the nearest node
            nearestNode.setFlag("V");
            visitedNodes.add(nearestNode);

            // Set the current node as the nearest node
            currentNode = nearestNode;
        }

        // Return to the initial node
        visitedNodes.add(initialNode);
    }
}
