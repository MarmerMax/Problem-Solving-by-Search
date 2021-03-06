import java.util.*;

public class BFS extends Algorithm {

    public BFS() {
        super();
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

    @Override
    protected boolean checkIfPathExist(Node start, Node goal) {
        Queue<Node> queue = new ArrayDeque<>();
        Set<Node> open_list = new HashSet<>();
        Set<Node> closed_list = new HashSet<>();

        queue.add(start);
        open_list.add(start);

        while (!queue.isEmpty()) {

            if (with_open) {
                Utils.pintList(closed_list);
            }

            Node current = queue.poll();
            open_list.remove(current);
            closed_list.add(current);

            char[] actions = {'L', 'U', 'R', 'D'};

            for (char action : actions) {

                Node neighbour = TilePuzzle.createNeighbourForNodeByAction(current, action);

                if (neighbour != null) {
                    nodes_amount++;

                    if (!Utils.checkIfNodeExistsInList(neighbour, open_list) && !Utils.checkIfNodeExistsInList(neighbour, closed_list)) {

                        if (isGoal(neighbour, goal)) {
                            return true;
                        }

                        queue.add(neighbour);
                        open_list.add(neighbour);
                    }
                }
            }
        }

        return false;
    }
}
