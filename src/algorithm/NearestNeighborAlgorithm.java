package algorithm;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Robot Tour Optimization
 * Suppose we are given a robot arm equipped with a tool, say a soldering iron.
 * In manufacturing circuit boards, all the chips and other components must be
 * fastened onto the substrate. More specifically, each chip has a set of contact points
 * or wires that must be soldered to the board. To program the robot arm for this job,
 * we must first construct an ordering of the contact points so the robot visits ad solders
 * the first contact point, then the second point, third, and so forth until the job
 * is done. The robot arm then proceeds back to the first contact point to prepare for
 * the next board, thus turning the tool-path into a closed tour or cycle.
 *
 * Robots are expensive devices, so we want the tour that minimizes the time it takes to
 * assemble the circuit board. A reasonable assumption is that the robot arm moves with
 * fixed speed, so the time to travel between two points is proportional to their distance.
 * In short, we must solve the following algorithm problem:
 *
 * Problem: Robot Tour Optimization
 * Input: A set S of n points in the plane
 * Output: What is the shortest cycle tour that visits each point in the set S?
 *
 * NearestNeighborAlgorithm class is an abstract class that will be extended by
 * an actual algorithm.
 */
public abstract class NearestNeighborAlgorithm {

    /**
     * Package-Private Member Variables
     * initialPoint: The first contact point of the robot arm
     * lowest: The lowest boundary on the circuit board.
     * highest: The highest boundary on the circuit board
     *
     * Note: There is no actual boundary on the circuit board as the path is circular.
     * So the lowest and highest are only logical boundaries. If you move 1 back from
     * the lowest point, you will end up at the highest point. If you move 1 forward from
     * the highest point, you end up at the lowest point.
     *
     *
     * Example:
     *
     *      7  8
     *    6      1
     *    5      2
     *      4  3
     *
     * In the above example diagram, the lowest point is 1 and the highest point is 8.
     * The initial point can be any one of the numbers between 1 and 8.
     *
     * The path can include negative values.
     */
    int initialPoint;
    int lowest;
    int highest;
    ArrayList<Node> nodesToVisit = null;

    /**
     * Private Member Variables
     * If there any errors in the input parameters, hasError boolean flag will be raised.
     * errorMessage will contain the description of the input error.
     *
     * Initially, hasError is assumed to be true
     */
    private boolean hasError = true;
    private String errorMessage = "";

    /**
     * Constructor
     * @param lowest The lowest point
     * @param highest The highest point
     * @param initialPoint The first contact point
     * @param pointsToVisit The subset of points that the robot arm must visit
     */
    NearestNeighborAlgorithm(int lowest, int highest, int initialPoint, int[] pointsToVisit) {
        if(!inputHasError(lowest, highest, initialPoint, pointsToVisit)) {
            hasError = false;
            nodesToVisit = new ArrayList<> ();

            this.lowest = lowest;
            this.highest = highest;
            this.initialPoint = initialPoint;

            for(int point : pointsToVisit) {
                nodesToVisit.add(new Node(point, "U"));
            }

            if(!nodesToVisit.contains(new Node(initialPoint))) {
                nodesToVisit.add(new Node(initialPoint, "U"));
            }

            nodesToVisit.sort(Comparator.comparing(Node::getPoint));
        }
    }

    /**
     * A method that checks given input for correctness
     * @param lowest The lowest point
     * @param highest The highest point
     * @param initialPoint The first contact point
     * @param pointsToVisit The subset of points that the robot arm must visit
     * @return boolean true if input has error, otherwise false
     */
    private boolean inputHasError(int lowest, int highest, int initialPoint, int[] pointsToVisit) {
        if(lowest >= highest) {
            errorMessage = "The lowest point must be less than the highest point.";
            return true;
        }

        if(initialPoint < lowest || initialPoint > highest) {
            errorMessage = "The initial point must be between the lowest point and the highest point.";
            return true;
        }

        // Container to store each point from pointsToVisit during runtime
        // so a duplicate point can be identified
        ArrayList<Integer> integerContainer = new ArrayList<> ();

        for(int point : pointsToVisit) {
            if(integerContainer.contains(point)) {
                errorMessage = "Duplicate points found in the set.";
                return true;
            } else if(point < lowest || point > highest){
                errorMessage = "Some points in the set is not between the lowest point and the highest point.";
                return true;
            } else {
                integerContainer.add(point);
            }
        }
        return false;
    }

    /**
     * Abstract method that will be implemented by concrete algorithm class
     * to provide solution to the problem.
     *
     * The solution will be a string representation of points in the order that
     * the robot arm must visit that ensures the most optimal path
     *
     * Example:
     * 0 3 5 7 0
     *
     * @return String representation of the most optimal path
     */
    public abstract String getSolution();

    /**
     * Public get method for hasError member variable
     * @return boolean true if input error exists, otherwise false
     */
    public boolean getHasError() {
        return hasError;
    }

    /**
     * Public get method for errorMessage member variable
     * @return String error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Public method to return input parameters for debugging purposes
     * @return String representation of input parameters
     */
    public String getInputParameters() {
        StringBuilder sb = new StringBuilder();

        sb.append("Lowest Point: ");
        sb.append(lowest);
        sb.append(System.getProperty("line.separator"));

        sb.append("Highest Point: ");
        sb.append(highest);
        sb.append(System.getProperty("line.separator"));

        sb.append("Initial Point: ");
        sb.append(initialPoint);
        sb.append(System.getProperty("line.separator"));

        sb.append("Points To Visit: ");
        sb.append(System.getProperty("line.separator"));
        for(Node node : nodesToVisit) {
            sb.append(node.getPoint());
            sb.append(" ");
        }

        return sb.toString();
    }

    /**
     * Generic package-private method that checks for unvisited nodes in
     * the given set of nodes
     * @param nodes A set of nodes
     * @param unvisitedFlag String flag that identifies an unvisited node
     * @return boolean true if an unvisited node exists, otherwise false
     */
    boolean unvisitedNodesExists(ArrayList<Node> nodes, String unvisitedFlag) {
        for(Node node : nodes) {
            if(node.getFlag().equals(unvisitedFlag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generic package-private method that calculates the shortest distance between
     * two nodes
     * @param firstNode The first node
     * @param secondNode The second node
     * @return int the shortest distance between the two nodes
     */
    int getShortestDistanceBetween(Node firstNode, Node secondNode) {
        // Largest Distance is the largest possible distance between any two given nodes
        // with the given lowest and highest point.
        // Because the path is circular, the distance between two nodes can be calculated two
        // different ways or directions.
        int largestDistance = highest - lowest + 1;

        // Calculate the distance in one direction with a simple subtraction
        int difference = Math.abs(firstNode.getPoint() - secondNode.getPoint());

        // Calculate the distance in the other direction by subtracting the difference
        // from the largest distance and then return the shortest of the two
        return Math.min(difference, largestDistance - difference);
    }

    /**
     * Node is a data structure for the algorithm problem.
     * Node is each point on the circuit board and it encapsulates other information
     * about the point that the program may need such as distance and flag.
     */
    class Node {

        /**
         * Private member variables
         * point: A point on the circuit board
         * flag: a generic flag for this node. In this exercise, "U" will denote
         *       an unvisited node and "V" will denote a visited node.
         * distance: A relative distance between this node and another reference node.
         */
        private int point;
        private String flag;
        private int distance;

        /**
         * Package-private constructor with point parameter
         * @param point Initial point
         */
        Node(int point) {
            this.point = point;
            this.flag = "";
            this.distance = 0;
        }

        /**
         * Package-private constructor with point and flag parameters
         * @param point Initial point
         * @param flag Initial flag
         */
        Node(int point, String flag) {
            this.point = point;
            this.flag = flag;
            this.distance = 0;
        }

        /**
         * Package-private get method for point
         * @return int point
         */
        int getPoint() {
            return point;
        }

        /**
         * Package-private get method for flag
         * @return String flag
         */
        String getFlag() {
            return flag;
        }

        /**
         * Package-private get method for distance
         * @return int distance
         */
        int getDistance() {
            return this.distance;
        }

        /**
         * Package-private set method for flag
         * @param flag String flag
         */
        void setFlag(String flag) {
            this.flag = flag;
        }

        /**
         * Package-private set method for distance
         * @param distance int distance
         */
        void setDistance(int distance) {
            this.distance = distance;
        }

        /**
         * Override public equals method to compare two node objects.
         * Only the points are compared for equality. The flag and relative distance are not used
         * for equality comparison.
         *
         * @param obj obj to compare with this node
         * @return boolean true if the obj is equal to this node, otherwise false
         */
        @Override
        public boolean equals(Object obj) {
            if(obj == this) {
                return true;
            }

            if(!(obj instanceof Node)) {
                return false;
            }

            Node nodeObj = (Node) obj;
            return this.point == nodeObj.point;
        }
    }
}

