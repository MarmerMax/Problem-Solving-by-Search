public abstract class Algorithm {

    protected int price;
    protected long nodes_amount;
    protected String path;
    protected double time;
    protected boolean is_path_exist;
    protected boolean with_open;

    public Algorithm() {
        price = 0;
        nodes_amount = 1; //start node
        path = "no path";
        time = 0;
        is_path_exist = false;
    }

    protected abstract boolean checkIfPathExist(Node start, Node goal);

    public void checkTilePuzzle(TilePuzzle tp, boolean w_o) {
        with_open = w_o;
        if (TilePuzzle.isGoal(tp.getRoot(), tp.getGoal())) { //check if the start state equals to the goal state
            return;
        } else if (!TilePuzzle.isPathExist(tp.getRoot())) { //check if black numbers not in right place
            return;
        } else {
            double start = System.nanoTime();
            if (checkIfPathExist(tp.getRoot(), tp.getGoal())) {
                path = path.substring(1);
            }
            double finish = System.nanoTime();
            time = finish - start;
            double second = 1000000000;
            time /= second;
        }
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

    protected boolean isGoal(Node current, Node target) {
        if (Matrix.isEqualsMatrices(current.getMatrix(), target.getMatrix())) {
            is_path_exist = true;
            path = current.getName();
            price = current.getPrice();
            return true;
        }
        return false;
    }
}
