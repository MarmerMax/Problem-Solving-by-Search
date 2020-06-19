import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class DFID extends Algorithm {

    //class for represent the result
    //cutoff=false and node=null => fail
    private class Result {

        protected boolean cutoff;
        protected Node node;

        Result(boolean cutoff, Node node) {
            this.cutoff = cutoff;
            this.node = node;
        }
    }

    public DFID() {
        super();
    }

    /**
     * DFID(Node start, Vector Goals)
     * 1. For depth=1 to ∞
     *      1. H  make_hash_table
     *      2. result  Limited_DFS(start,Goals,depth,H)
     *      3. If result ≠ cutoff then return result
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
     *      5. If isCutoff = true
     *           1. return cutoff
     *      6. Else
     *           1. return fail
     **/

    @Override
    protected boolean checkIfPathExist(Node start, Node goal) {
        for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
            Set<Node> loop_avoidance_list = new HashSet<>();
            Result result = Limited_DFS(start, goal, depth, loop_avoidance_list);

            if (!result.cutoff) {
                //if result node is null then this is fail, otherwise true
                return result.node != null;
            }
        }
        return false;
    }

    private Result Limited_DFS(Node current, Node goal, int limit, Set<Node> loop_avoidance_list) {
        System.out.println(nodes_amount);
        if (isGoal(current, goal)) {
            return new Result(false, current);
        } else if (limit == 0) {
            return new Result(true, null);
        } else {

            loop_avoidance_list.add(current);
            boolean is_cutoff = false;

            char[] actions = {'L', 'U', 'R', 'D'};

            for (char action : actions) {

                Node neighbour = TilePuzzle.createNeighbourForNodeByAction(current, action);

                if (neighbour != null) {
                    nodes_amount++;

                    if (Utils.checkIfNodeExistsInList(neighbour, loop_avoidance_list)) {
                        continue;
                    }

                    if (with_open) {
                        neighbour.print();
                    }

                    Result temp_result = Limited_DFS(neighbour, goal, limit - 1, loop_avoidance_list);

                    if (temp_result.cutoff) {                 //if the result was cutoff then increase the limit
                        is_cutoff = true;
                    } else if (temp_result.node != null) {    //if the result node is not null then this is the solution
                        return temp_result;
                    }
                }
            }

            loop_avoidance_list.remove(current);

            if (is_cutoff) {
                return new Result(true, null);
            } else {
                return new Result(false, null); //fail
            }
        }
    }
}