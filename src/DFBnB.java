import java.util.*;

public class DFBnB extends Algorithm {

    public DFBnB(){
        super();
    }


    /**
     * DFBnB(Node start, Vector Goals)
     * 1. L  make_stack(start) and H  make_hash_table(start)
     * 2. result  null, t  ∞ // should be set to a strict upper bound in an infinite graph
     * 3. While L is not empty
     *      1. n  L.remove_front()
     *      2. If n is marked as “out”
     *          1. H.remove(n)
     *      3. Else
     *          1. mark n as “out” and L.insert(n)
     *          2. N  apply all of the allowed operators on n
     *          3. sort the nodes in N according to their f values (increasing order)
     *          4. For each node g from N according to the order of N
     *              1. If f(g) >= t
     *                  1. remove g and all the nodes after it from N
     *              2. Else If H contains g’=g and g’ is marked as “out”
     *                  1. remove g from N
     *              3. Else If H contains g’=g and g’ is not marked as “out”
     *                  1. If f(g’)>f(g)
     *                      1. remove g’ from L and from H
     *                  2. Else
     *                      1. remove g from N
     *              4. Else If goal(g) // if we reached here, f(g) < t
     *                  1. t  f(g)
     *                  2. result  path(g) // all the “out” nodes in L
     *                  3. remove g and all the nodes after it from N
     *          5. insert N in a reverse order to L and H
     * 4. Return result
     */

    @Override
    protected boolean checkIfPathExist(Node start, Node goal) {

        //create stack for nodes
        Stack<Node> stack = new Stack<>();
        stack.add(start);

        //create hash set for checking loop avoidance
        Set<Node> loop_avoidance_list = new HashSet<>();
        loop_avoidance_list.add(start);

        int threshold = Integer.MAX_VALUE;
        boolean result = false;


        while (!stack.empty()) {

            if (with_open) {
                Utils.pintList(loop_avoidance_list);
            }

            Node current = stack.pop();

            if (current.getOut()) {

                loop_avoidance_list.remove(current);

            } else {

                current.markAsOut();
                stack.add(current);

                //create all available neighbours for the current node
                char[] actions = {'L', 'U', 'R', 'D'};
                ArrayList<Node> neighbours = TilePuzzle.createNeighboursOfNode(current, actions);

                nodes_amount += neighbours.size();

                //create a comparator that sorts nodes by total price
                Comparator<Node> comparator = new Comparator<Node>() {
                    @Override
                    public int compare(Node n1, Node n2) {
                        return n1.getTotalPrice() - n2.getTotalPrice();
                    }
                };

                //if there is more than one neighbor, sort them by price
                if (neighbours.size() > 1) {
                    neighbours.sort(comparator);
                }

                for (int i = 0; i < neighbours.size(); i++) {

                    Node neighbour = neighbours.get(i);

                    int neighbour_total_price = neighbour.getTotalPrice();

                    if (neighbour_total_price >= threshold) {

                        while (i < neighbours.size()) {
                            neighbours.remove(i);
                        }

                    } else if (Utils.checkIfNodeExistsInList(neighbour, loop_avoidance_list)) {

                        Node same_node = Utils.getSameNode(neighbour, loop_avoidance_list);

                        if (same_node.getOut()) {
                            neighbours.remove(neighbour);
                            i--;
                        } else {

                            int same_node_total_price = same_node.getTotalPrice();

                            if (same_node_total_price > neighbour_total_price) {
                                stack.remove(same_node);
                                loop_avoidance_list.remove(same_node);
                            } else {
                                neighbours.remove(neighbour);
                                i--;
                            }

                        }

                    } else if (isGoal(neighbour, goal)) {
                        result = true;
                        threshold = neighbour_total_price;
                        neighbours.clear();
                    }

                }

                if (neighbours.size() > 0) {
                    Collections.reverse(neighbours);
                    for (Node node : neighbours) {
                        stack.push(node);
                        loop_avoidance_list.add(node);
                    }
                }
            }
        }
        return result;
    }
}
