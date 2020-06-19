import java.util.*;

public class A extends Algorithm {

    public A() {
        super();
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

    @Override
    public boolean checkIfPathExist(Node start, Node goal) {
        Comparator<Node> comparator = new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return n1.getTotalPrice() - n2.getTotalPrice();
            }
        };
        Queue<Node> queue = new PriorityQueue<>(comparator);
        Set<Node> open_list = new HashSet<>();
        Set<Node> close_list = new HashSet<>();

        queue.add(start);
        open_list.add(start);

        while (!queue.isEmpty()) {

            if (with_open) {
                Utils.pintList(open_list);
            }

            Node current = queue.poll();
            open_list.remove(current);

            if (isGoal(current, goal)) {
                return true;
            }

            close_list.add(current);

            char[] actions = {'L', 'U', 'R', 'D'};

            for (char action : actions) {
                Node neighbour = TilePuzzle.createNeighbourForNodeByAction(current, action);
                if (neighbour != null) {
                    nodes_amount++;
                    if (!Utils.checkIfNodeExistsInList(neighbour, open_list)
                            && !Utils.checkIfNodeExistsInList(neighbour, close_list)) {

                        queue.add(neighbour);
                        open_list.add(neighbour);

                    } else if (Utils.checkIfNodeExistsInList(neighbour, open_list)) {

                        Utils.changeBetweenNodesInQueue(queue, open_list, neighbour);

                    }
                }
            }
        }
        return false;
    }
}
