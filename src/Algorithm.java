public abstract class Algorithm {

    protected int price;
    protected long nodes_amount;
    protected String path;
    protected double time;

    public Algorithm() {
        price = 0;
        nodes_amount = 1; //start node
        path = "no path";
        time = 0;
    }

    protected abstract boolean checkIfPathExist(Node start, Node goal);

    public void checkTilePuzzle(TilePuzzle tp) {
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

    public String getTime() {
        return Utils.round(time) + " seconds";
    }

    protected boolean isGoal(Node current, Node target) {
        if (Matrix.isEqualsMatrices(current.getMatrix(), target.getMatrix())) {
            path = current.getName();
            price = current.getPrice();
            return true;
        }
        return false;
    }
}
