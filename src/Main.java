import algorithm.NearestNeighborAlgorithm;
import algorithm.NearestNeighborHeuristic;
import algorithm.NearestNeighborPermutation;

/**
 * Robot Tour Optimization
 *
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
 */
public class Main {

    /**
     * Main Method
     * @param args arguments
     */
    public static void main(String[] args) {

        // Set input parameters
        int lowest = -21;
        int highest = 11;
        int initialPoint = 0;
        int[] points = {-21, -11, -6, -5, -1, 0, 1, 5, 7, 11};

        // Instantiate NearestNeighborHeuristic and NearestNeighborPermutation
        NearestNeighborAlgorithm heuristicAlgorithm =
                new NearestNeighborHeuristic(lowest, highest, initialPoint, points);

        NearestNeighborAlgorithm permutationAlgorithm =
                new NearestNeighborPermutation(lowest, highest, initialPoint, points);

        // Check for input errors
        if(heuristicAlgorithm.getHasError()) {
            System.out.println(heuristicAlgorithm.getErrorMessage());
            System.out.println();
        } else if(permutationAlgorithm.getHasError()) {
            System.out.println(permutationAlgorithm.getErrorMessage());
            System.out.println();
        } else {
            // Display Input Parameters
            System.out.println(heuristicAlgorithm.getInputParameters());
            System.out.println();

            // Display the result of the heuristic algorithm
            System.out.println("The heuristic algorithm returned the following path:");
            System.out.println(heuristicAlgorithm.getSolution());
            System.out.println("Execution Time: " + heuristicAlgorithm.getExecutionTime() + "ms");
            System.out.println();

            // Display the result of the permutation algorithm
            System.out.println("The permutation algorithm returned the following path:");
            System.out.println(permutationAlgorithm.getSolution());
            System.out.println("Execution Time: " + permutationAlgorithm.getExecutionTime() + "ms");
            System.out.println();
        }
    }
}
