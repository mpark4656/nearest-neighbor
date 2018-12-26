# Nearest Neighbor Algorithm

## Robot Tour Optimization
Suppose we are given a robot arm equipped with a tool, say a soldering iron.
In manufacturing circuit boards, all the chips and other components must be
fastened onto the substrate. More specifically, each chip has a set of contact points
or wires that must be soldered to the board. To program the robot arm for this job,
we must first construct an ordering of the contact points so the robot visits ad solders
the first contact point, then the second point, third, and so forth until the job
is done. The robot arm then proceeds back to the first contact point to prepare for
the next board, thus turning the tool-path into a closed tour or cycle.

Robots are expensive devices, so we want the tour that minimizes the time it takes to
assemble the circuit board. A reasonable assumption is that the robot arm moves with
fixed speed, so the time to travel between two points is proportional to their distance.
In short, we must solve the following algorithm problem:

Problem: Robot Tour Optimization
Input: A set S of n points in the plane
Output: What is the shortest cycle tour that visits each point in the set S?

NearestNeighborAlgorithm class is an abstract class that will be extended by
an actual algorithm.

## Sample Input
Lowest Point: -21

Highest Point: 11

Initial Point: 0

Points To Visit: -21 -5 -1 0 1 3 11

## Sample Output
The heuristic algorithm returned the following path:

0 -1 1 3 -5 -21 11 0

The permutation algorithm returned the following path:

0 -1 -5 -21 11 3 1 0