import java.util.*;

public class BFS extends Algorithm {

    private int price;
    private int nodes_amount;
    private String path;
    private double time = 0;

    private Queue<Node> queue;

    public BFS() {
        queue = new ArrayDeque<>();
    }

    /**
     * BFS(Node start, Vector goal)
     * 1. L  make_queue (start) and make_hash_table
     * 2. C  make_hash_table
     * 3. While L not empty loop
     *      1. n  L.remove_front
     *      2. C  n
     *      3. For each allowed operator on n
     *          1. g  operator(n)
     *          2. If g not in C and not in L
     *              1. If goal(g) return path(g)
     *              2. L.insert (g)
     * 4. Return false
     **/

    private boolean checkIfPathExist(Node start, Node goal) {
        if (TilePuzzle.isGoal(start, goal)) {
            path = "";
            return true;
        }

        //check if black numbers not in right place
        if (!TilePuzzle.isPathExist(start)) {
            return false;
        }

        Set<Node> openList = new HashSet<>();
        Set<Node> closedList = new HashSet<>();

        queue.add(start);
        openList.add(start);

        while (!queue.isEmpty()) {

            Node current = queue.poll();
            openList.remove(current);

            ArrayList<Node> neighbours = TilePuzzle.createNodeNeighbours(current);
            nodes_amount += neighbours.size();

            for (Node neighbour : neighbours) {
                if (!closedList.contains(neighbour) && !openList.contains(neighbour)) {


                    if (TilePuzzle.isGoal(neighbour, goal)) {
                        path = neighbour.getName();
                        price = neighbour.getPrice();
                        return true;
                    }

                    queue.add(neighbour);
                }
            }
            closedList.add(current);
            openList.remove(current);
        }

        return false;
    }

    @Override
    public void checkTilePuzzle(TilePuzzle tp) {
        double start = System.nanoTime();
        if (checkIfPathExist(tp.getRoot(), tp.getGoal())) {
            path = Path.buildPath(path);
        } else {
            path = "no path";
            price = 0;
            nodes_amount = 0;
        }
        double finish = System.nanoTime();
        time = finish - start;
        double second = 1000000000;
        time /= second;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getTime() {
        return Utils.round(time) + " seconds";
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getNodesAmount() {
        return nodes_amount;
    }

}
