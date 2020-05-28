import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class IDA extends Algorithm {

    public IDA(){
        super();
    }

    /**
     *
     * IDA*(Node start, Vector Goals)
     * 1. L  make_stack and H  make_hash_table
     * 2. t  h(start)
     * 3. While t ≠ ∞
     *      1. minF  ∞
     *      2. L.insert(start) and H.insert(start)
     *      3. While L is not empty
     *          1. n  L.remove_front()
     *          2. If n is marked as “out”
     *              1. H.remove(n)
     *          3. Else
     *              1. mark n as “out” and L.insert(n)
     *              2. For each allowed operator on n
     *              3. g  operator(n)
     *                  1. If f(g) > t
     *                      1. minF  min(minF, f(g))
     *                      2. continue with the next operator
     *                  2. If H contains g’=g and g’ marked “out”
     *                      1. continue with the next operator
     *                  3. If H contains g’=g and g’ not marked “out”
     *                      1. If f(g’)>f(g)
     *                          1. remove g’ from L and H
     *                      2. Else
     *                          1. continue with the next operator
     *                  4. If goal(g) then return path(g) //all the “out” nodes in L
     *                  5. L.insert(g) and H.insert(g)
     *          4. t  minF
     * 4. Return false
     */

    @Override
    protected boolean checkIfPathExist(Node start, Node goal) {
        //create stack for nodes and set for checking loop avoidance
        Stack<Node> stack = new Stack<>();
        Set<Node> loop_avoidance_list = new HashSet<>();

        int threshold = start.getHeuristicPrice();

        while (threshold != Integer.MAX_VALUE) {

            int min_f = Integer.MAX_VALUE;

            stack.add(start);
            loop_avoidance_list.add(start);

            while (!stack.empty()) {

                Node current = stack.pop();

                if (current.getOut()) {

                    loop_avoidance_list.remove(current);

                } else {

                    current.markAsOut();
                    stack.add(current);
                    loop_avoidance_list.add(current);

                    char[] actions = {'L', 'U', 'R', 'D'};

                    for (char action : actions) {
                        Node neighbour = TilePuzzle.createNeighbourByActionForNode(current, action);

                        if (neighbour != null) {
                            nodes_amount++;
                            boolean add_to_stack = true;

                            int neighbour_total_price = neighbour.getPrice() + neighbour.getHeuristicPrice();

                            if (neighbour_total_price > threshold) {
                                min_f = Math.min(min_f, neighbour_total_price);
                                add_to_stack = false;
                            } else if (Utils.checkIfNodeExistsInList(neighbour, loop_avoidance_list) && neighbour.getOut()) {
                                //continue with next neighbour
                                add_to_stack = false;
                            } else if (Utils.checkIfNodeExistsInList(neighbour, loop_avoidance_list) && !neighbour.getOut()) {

                                Node same_node = Utils.getSameNode(neighbour, loop_avoidance_list);
                                int same_node_total_price = same_node.getPrice() + same_node.getHeuristicPrice();

                                if (same_node_total_price > neighbour_total_price) {
                                    loop_avoidance_list.remove(same_node);
                                } else {
                                    //continue with next neighbour
                                    add_to_stack = false;
                                }
                            }

                            //if this node has lower or equal price to threshold and does not exists in stack
                            if (add_to_stack) {
                                if (isGoal(neighbour, goal)) {
                                    return true;
                                }
                                stack.add(neighbour);
                                loop_avoidance_list.add(neighbour);
                            }
                        }
                    }
                }
            }
            threshold = min_f;
        }
        return false;
    }
}
