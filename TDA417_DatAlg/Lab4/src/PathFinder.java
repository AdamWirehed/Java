
import java.util.*;

import java.util.stream.Collectors;


public class PathFinder<V> {

    private DirectedGraph<V> graph;
    private long startTimeMillis;


    public PathFinder(DirectedGraph<V> graph) {
        this.graph = graph;
    }


    public class Result<V> {
        public final boolean success;
        public final V start;
        public final V goal;
        public final double cost;
        public final List<V> path;
        public final int visitedNodes;
        public final double elapsedTime;

        public Result(boolean success, V start, V goal, double cost, List<V> path, int visitedNodes) {
            this.success = success;
            this.start = start;
            this.goal = goal;
            this.cost = cost;
            this.path = path;
            this.visitedNodes = visitedNodes;
            this.elapsedTime = (System.currentTimeMillis() - startTimeMillis) / 1000.0;
        }

        public String toString() {
            String s = "";
            s += String.format("Visited nodes: %d\n", visitedNodes);
            s += String.format("Elapsed time: %.1f seconds\n", elapsedTime);
            if (success) {
                s += String.format("Total cost from %s -> %s: %s\n", start, goal, cost);
                s += "Path: " + path.stream().map(Object::toString).collect(Collectors.joining(" -> "));
            } else {
                s += String.format("No path found from %s", start);
            }
            return s;
        }
    }


    public Result<V> search(String algorithm, V start, V goal) {
        startTimeMillis = System.currentTimeMillis();
        switch (algorithm) {
            case "random":
                return searchRandom(start, goal);
            case "dijkstra":
                return searchDijkstra(start, goal);
            case "astar":
                return searchAstar(start, goal);
        }
        throw new IllegalArgumentException("Unknown search algorithm: " + algorithm);
    }


    public Result<V> searchRandom(V start, V goal) {
        int visitedNodes = 0;
        LinkedList<V> path = new LinkedList<>();
        double cost = 0.0;
        Random random = new Random();

        V current = start;
        path.add(current);
        while (current != null) {
            visitedNodes++;
            if (current.equals(goal)) {
                return new Result<>(true, start, current, cost, path, visitedNodes);
            }

            List<DirectedEdge<V>> neighbours = graph.outgoingEdges(start);
            if (neighbours == null || neighbours.size() == 0) {
                break;
            } else {
                DirectedEdge<V> edge = neighbours.get(random.nextInt(neighbours.size()));
                cost += edge.weight();
                current = edge.to();
                path.add(current);
            }
        }
        return new Result<>(false, start, null, -1, null, visitedNodes);
    }


    public Result<V> searchDijkstra(V start, V goal) {
        int visitedNodes = 0;
        /********************
         * TODO: Task 1 
         ********************/

        HashMap<V, Double> distTo = new HashMap<>();                // distTo[v] = distance of shortest s->v path
        HashMap<V, DirectedEdge<V>> edgeTo = new HashMap<>();          // edgeTo[v] = last edge on shortest s->v path
        ArrayList<V> visited = new ArrayList<>();

        Comparator<V> distCmp = (o1, o2) -> {
            if (distTo.get(o1) < distTo.get(o2)) {
                return -1;
            } else if (distTo.get(o1) > distTo.get(o2)) {
                return 1;
            }
            return 0;
        };

        PriorityQueue<V> pq = new PriorityQueue<>(distCmp);                // Priority queue of edges, sorted by distTo

        // Convert node to hashCode since node can be other than int
        pq.add(start);              // Adding the start node to the PQ and distTo
        distTo.put(start, 0.0);

        while (!pq.isEmpty()) {
            V v = pq.remove();
            if (!visited.contains(v)) {
                visited.add(v);
                visitedNodes++;
                if (v.equals(goal)) {
                    ArrayList<V> sp = calculatePath(start, goal, edgeTo);
                    return new Result<>(true, start, goal, distTo.get(goal), sp, visitedNodes);
                }
                for (DirectedEdge<V> e : this.graph.outgoingEdges(v)) {
                    V w = e.to();
                    if(!distTo.containsKey(w)){
                        distTo.put(w, Double.POSITIVE_INFINITY);
                    }
                    Double newdist = distTo.get(v) + e.weight();
                    if (distTo.get(w) > newdist) {
                        distTo.put(w, newdist);
                        edgeTo.put(w, e);
                        pq.add(w);
                    }
                }
            }
        }
        return new Result<>(false, start, null, -1, null, visitedNodes);
    }

    // Method for getting shortest path from start to goal in ArrayList edges
    public ArrayList<V> calculatePath(V start, V goal, HashMap<V, DirectedEdge<V>> edges){
        V cNode = goal;
        ArrayList<V> path = new ArrayList<>();
        while(!cNode.equals(start)){
            path.add(cNode);
            cNode = (edges.get(cNode)).from();
        }
        path.add(cNode);
        Collections.reverse(path);
        return path;
    }
    

    public Result<V> searchAstar(V start, V goal) {
        int visitedNodes = 0;
        /********************
         * TODO: Task 3
         ********************/
        return new Result<>(false, start, null, -1, null, visitedNodes);
    }

}
