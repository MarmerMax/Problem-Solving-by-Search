import java.util.*;

public class A extends Algorithm {

    private TilePuzzle tile_puzzle;

    private int price = 0;
    private int nodes_amount = 0;
    private String path = "no path";
    private double time = 0;

    private Queue<Node> queue;

    public A() {
        Comparator<Node> comparator = new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return (n1.getPrice() + n1.getHeuristicPrice()) - (n2.getPrice() + n2.getHeuristicPrice());
            }
        };
        queue = new PriorityQueue<>(comparator);
    }

    /**
     * UCS(Node start, Vector goal)
     * 1. L  make_priority_queue (start) and make_hash_table
     * 2. C  make_hash_table
     * 3. While L not empty loop
     *      1. n  L.remove_front
     *      2. If goal(n) return path(n)
     *      3. C  n
     *      4. For each allowed operator on n
     *          1. x  operator(n)
     *          2. If x not in C and not in L
     *              1. L.insert (
     *          3. Else if x in L with higher path cost
     *              1. Replace the node in L with x
     * 4. Return false
     */

    private boolean checkIfPathExist() {
        if (tile_puzzle.isGoal(tile_puzzle.getRoot())) {
            path = "";
            return true;
        }

        //check if black numbers not in right place
        if (!tile_puzzle.isPathExist()) {
            return false;
        }

        Set<Node> openList = new HashSet<>();
        Set<Node> closedList = new HashSet<>();

        queue.add(tile_puzzle.getRoot());
        openList.add(tile_puzzle.getRoot());

        while (!queue.isEmpty()) {

            Node current = queue.poll();
            openList.remove(current);

            if (tile_puzzle.isGoal(current)) {
                path = Path.buildPath(current);
                price = current.getPrice();
                return true;
            }

            closedList.add(current);

            ArrayList<Node> neighbours = tile_puzzle.createNodeNeighbours(current);
            nodes_amount += neighbours.size();

            for (Node neighbour : neighbours) {
                if (!Utils.checkIfNodeExistsInList(neighbour, openList)
                        && !Utils.checkIfNodeExistsInList(neighbour, closedList)) {

                    queue.add(neighbour);
                    openList.add(neighbour);

                } else if (Utils.checkIfNodeExistsInList(neighbour, openList)) {
                    queue = Utils.changeBetweenNodesInQueue(queue, openList, neighbour);
                }
            }
        }
        return false;
    }

    @Override
    public void setTilePuzzle(TilePuzzle tp) {
        tile_puzzle = tp;
        double start = System.nanoTime();
        checkIfPathExist();
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
    public int getPrice() {
        return price;
    }

    @Override
    public int getNodesAmount() {
        return nodes_amount;
    }

    @Override
    public String getTime() {
        return Utils.round(time) + "seconds";
    }
}
