/**
 * An abstract class for all algorithms.
 * Each algorithm should implement only one function - checkIfPathExist (start, target).
 * Other functions are common to all algorithms.
 */

public abstract class Algorithm {

    protected int price;
    protected long nodes_amount;
    protected String path;
    protected double time;
    protected boolean is_path_exist;
    protected boolean with_open;
    protected int max_steps;

    public Algorithm() {
        price = 0;
        nodes_amount = 1; //start node
        path = "no path";
        time = 0;
        is_path_exist = false;
        max_steps = 0;
    }

    protected abstract boolean checkIfPathExist(Node start, Node goal);

    /**
     * This function checks if there is a solution for a given tile puzzle.
     *
     * @param tp  - a tile puzzle that needs to be checked
     * @param w_o - a boolean that determines whether to print an open list
     */
    public void checkTilePuzzle(TilePuzzle tp, boolean w_o) {
        with_open = w_o;
        max_steps = tp.getMax_steps(); //used only in the DFBnB algorithm

        if (TilePuzzle.isGoal(tp.getRoot(), tp.getGoal())) { //check if the start state equals to the goal state
            return;
        } else if (!tp.getIsPathExist()) { //check if black numbers not in right place
            return;
        } else {
            double start = System.nanoTime();

            //injection point of the algorithm -> for each algorithm will be the different implementation
            boolean answer = checkIfPathExist(tp.getRoot(), tp.getGoal());

            double finish = System.nanoTime();
            time = finish - start;
            double second = 1000000000;
            time /= second;
        }
    }

    //if current node is the target node then update path, price...
    protected boolean isGoal(Node current, Node target) {
        if (Matrix.isEqualsMatrices(current.getMatrix(), target.getMatrix())) {
            is_path_exist = true;
            path = current.getName().substring(1);
            price = current.getPrice();
            return true;
        }
        return false;
    }

    public String getPath() {
        return path;
    }

    public int getPrice() {
        return price;
    }

    public long getNodesAmount() {
        return nodes_amount;
    }

    public boolean getPathExist() {
        return is_path_exist;
    }

    public String getTime() {
        return Utils.round(time) + " seconds";
    }
}
