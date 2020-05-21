import java.util.*;

public class A extends Algorithm {

    private int price;
    private int nodes_amount;
    private String path;
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

            if (TilePuzzle.isGoal(current, goal)) {
                path = current.getName();
                price = current.getPrice();
                return true;
            }

            closedList.add(current);

            ArrayList<Node> neighbours = TilePuzzle.createNodeNeighbours(current);
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
