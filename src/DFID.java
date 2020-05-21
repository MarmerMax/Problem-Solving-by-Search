import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DFID extends Algorithm {

    private int price;
    private int nodes_amount;
    private String path;
    private double time = 0;

    public DFID() {}

    /**
     * DFID(Node start, Vector Goals)
     * 1. For depth=1 to ∞
     *      1. H  make_hash_table
     *      2. result  Limited_DFS(start,Goals,depth,H)
     * 2. If result ≠ cutoff then return result
     *
     * Limited_DFS(Node n, Vector Goals, int limit, hash H)
     * 1. If goal(n) then return path(n) //use the back pointers or the recursion tail
     * 2. Else if limit = 0 then return cutoff
     * 3. Else
     *      1. H.insert(n)
     *      2. isCutoff  false
     *      3. For each allowed operator on n
     *          1. g  operator(n)
     *          2. If H contains g
     *              1. continue with the next operator
     *          3. result  Limited_DFS(g,Goals,limit-1,H)
     *          4. If result = cutoff
     *              1. isCutoff  true
     *          5. Else if result ≠ fail
     *              1. return result
     *      4. H.remove(n) //the memory for n should be also released
     * 5. If isCutoff = true
     *      1. return cutoff
     * 6. Else
     *      1. return fail
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

        for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
            Set<Node> loop_avoidance_list = new HashSet<>();
            Node temp = Limited_DFS(start, goal, depth, loop_avoidance_list);
            if (temp != null) {
                path = temp.getName();
                price = temp.getPrice();
                return true;
            }
        }
        return false;
    }

    private Node Limited_DFS(Node current, Node goal, int limit, Set<Node> loop_avoidance_list) {
        if (TilePuzzle.isGoal(current, goal)) {
            return current;
        } else if (limit == 0) {
            return null;
        } else {

            loop_avoidance_list.add(current);
            ArrayList<Node> neighbours = TilePuzzle.createNodeNeighbours(current);
            nodes_amount += neighbours.size();

            for (Node neighbour : neighbours) {
                if (!Utils.checkIfNodeExistsInList(neighbour, loop_avoidance_list)) {
                    Node temp = Limited_DFS(neighbour, goal, limit - 1, loop_avoidance_list);
                    if (temp != null) {
                        return temp;
                    }
                }
            }
        }

        return null;
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
        return Utils.round(time) + " seconds";
    }
}
