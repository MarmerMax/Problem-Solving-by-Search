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
                return (n1.getPrice() + n1.getHeuristicPrice()) - (n2.getPrice() + n2.getHeuristicPrice());
            }
        };
        Queue<Node> queue = new PriorityQueue<>(comparator);
        Set<Node> openList = new HashSet<>();
        Set<Node> closedList = new HashSet<>();

        queue.add(start);
        openList.add(start);

        while (!queue.isEmpty()) {

            Node current = queue.poll();
            openList.remove(current);

            if(isGoal(current, goal)){
                return true;
            }

            closedList.add(current);

            char[] actions = {'L', 'U', 'R', 'D'};

            for (char action : actions) {
                Node neighbour = TilePuzzle.createNeighbourByActionForNode(current, action);
                if (neighbour != null) {
                    nodes_amount++;
                    if (!Utils.checkIfNodeExistsInList(neighbour, openList)
                            && !Utils.checkIfNodeExistsInList(neighbour, closedList)) {

                        queue.add(neighbour);
                        openList.add(neighbour);

                    } else if (Utils.checkIfNodeExistsInList(neighbour, openList)) {
                        queue = Utils.changeBetweenNodesInQueue(queue, openList, neighbour);
                    }
                }
            }
        }
        return false;
    }
}
