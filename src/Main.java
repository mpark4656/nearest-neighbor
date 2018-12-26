import algorithm.NearestNeighborAlgorithm;
import algorithm.NearestNeighborHeuristic;
import algorithm.NearestNeighborPermutation;

public class Main {

    public static void main(String[] args) {

        int lowest = -21;
        int highest = 11;
        int initialPoint = 0;
        int[] points = {-21, -5, -1, 0, 1, 3, 11};

        NearestNeighborAlgorithm heuristicAlgorithm =
                new NearestNeighborHeuristic(lowest, highest, initialPoint, points);

        NearestNeighborAlgorithm permutationAlgorithm =
                new NearestNeighborPermutation(lowest, highest, initialPoint, points);

        if(heuristicAlgorithm.getHasError()) {
            System.out.println(heuristicAlgorithm.getErrorMessage());
            System.out.println();
        } else if(permutationAlgorithm.getHasError()) {
            System.out.println(permutationAlgorithm.getErrorMessage());
            System.out.println();
        } else {
            System.out.println("Heuristic Algorithm Successfully Initialized");
            System.out.println("Permutation Algorithm Successfully Initialized");
            System.out.println();

            System.out.println(heuristicAlgorithm.getInputParameters());
            System.out.println();

            System.out.println("The heuristic algorithm returned the following path.");
            System.out.println(heuristicAlgorithm.getSolution());
            System.out.println();

            System.out.println("The permutation algorithm returned the following path.");
            System.out.println(permutationAlgorithm.getSolution());
            System.out.println();
        }
    }
}
