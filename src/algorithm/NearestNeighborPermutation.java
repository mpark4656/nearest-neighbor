package algorithm;

import java.util.ArrayList;

public class NearestNeighborPermutation extends NearestNeighborAlgorithm {

    private ArrayList<Node> visitedNodes = new ArrayList<> ();
    private ArrayList<Path> paths = new ArrayList<> ();
    private boolean solved = false;

    public NearestNeighborPermutation(int lowest, int highest, int initialPoint, int[] pointsToVisit) {
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

        int shortestDistance = Integer.MAX_VALUE;

        Path initialPath = new Path();
        initialPath.addNode(initialNode);

        ArrayList<Node> initialNodes = new ArrayList<> (nodesToVisit);

        initialNodes.remove(initialNode);
        permutation(initialNodes, initialPath);

        for(Path path : paths) {
            path.addNode(initialNode);

            if(path.getTotalDistance() < shortestDistance) {
                shortestDistance = path.getTotalDistance();
            }
        }

        for(Path path : paths) {
            if(path.getTotalDistance() == shortestDistance) {
                for(Node node : path.getNodes()) {
                    visitedNodes.add(new Node(node.getPoint(), node.getFlag()));
                }
                return;
            }
        }
    }

    private void permutation(ArrayList<Node> nodes, Path path) {
        // If there are no unvisited nodes, add the final path and exit permutation
        if(!unvisitedNodesExists(nodes, "U")) {
            paths.add(path);
            return;
        }

        for(Node node : nodes) {
            if(node.getFlag().equals("U")) {
                // Make a separate deep copy of the node array
                ArrayList<Node> newNodes = new ArrayList<> ();
                for(Node eachNode : nodes) {
                    newNodes.add(new Node(eachNode.getPoint(), eachNode.getFlag()));
                }

                // Make a separate deep copy of the path
                Path newPath = new Path();
                for(Node eachNode : path.getNodes()) {
                    newPath.addNode(new Node(eachNode.getPoint(), eachNode.getFlag()));
                }

                newNodes.get(newNodes.indexOf(node)).setFlag("V");
                newPath.addNode(new Node(node.getPoint(), "V"));
                permutation(newNodes, newPath);
            }
        }
    }

    class Path {
        private ArrayList<Node> nodes = new ArrayList<> ();
        private int totalDistance = 0;

        Path() { }

        void addNode(Node node) {
            if(nodes.size() > 0) {
                totalDistance += getShortestDistanceBetween(nodes.get(nodes.size() -1), node);
            }
            nodes.add(node);
        }

        int getTotalDistance() {
            return totalDistance;
        }

        ArrayList<Node> getNodes() {
            return nodes;
        }
    }
}
