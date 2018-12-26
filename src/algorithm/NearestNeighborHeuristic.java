package algorithm;

import java.util.ArrayList;

public class NearestNeighborHeuristic extends NearestNeighborAlgorithm {
    private ArrayList<Node> visitedNodes = new ArrayList<> ();
    private boolean solved = false;

    public NearestNeighborHeuristic(int lowest, int highest, int initialPoint, int[] pointsToVisit) {
        super(lowest, highest, initialPoint, pointsToVisit);
    }

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

    private void solve() {

        int initialPointIndex = nodesToVisit.indexOf(new Node(initialPoint));
        Node initialNode = nodesToVisit.get(initialPointIndex);

        Node currentNode = initialNode;

        currentNode.setFlag("V");
        visitedNodes.add(currentNode);

        int shortestDistance = highest - lowest;

        while(unvisitedNodesExists(nodesToVisit, "U")) {
            for(Node node : nodesToVisit) {
                if(node.getFlag().equals("U")) {
                    int distance = getShortestDistanceBetween(currentNode, node);
                    node.setDistance(distance);

                    if(shortestDistance > distance) {
                        shortestDistance = distance;
                    }
                }
            }

            for(Node node : nodesToVisit) {
                if(node.getFlag().equals("U") && node.getDistance() == shortestDistance) {
                    currentNode = node;
                    currentNode.setFlag("V");
                    visitedNodes.add(currentNode);
                    shortestDistance = highest - lowest;
                    break;
                }
            }
        }

        visitedNodes.add(initialNode);
    }
}
