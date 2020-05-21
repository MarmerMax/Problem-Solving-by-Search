import java.util.*;

public class BFS extends Algorithm {

    private TilePuzzle tile_puzzle;
    private int price = 0;
    private int nodes_amount = 0;
    private String path = "no path";
    private double time = 0;

    private Queue<Node> queue;

    public BFS() {
        queue = new ArrayDeque<>();
    }

    public void setTilePuzzle(TilePuzzle tp) {
        tile_puzzle = tp;
        double start = System.nanoTime();
        checkIfPathExist();
        double finish = System.nanoTime();
        time = finish - start;
        double second = 1000000000;
        time /= second;
    }

    private boolean checkIfPathExist() {
        if (tile_puzzle.isGoal(tile_puzzle.getRoot())) {
            return true;
        }

        Set<Node> openList = new HashSet<>();
        Set<Node> closedList = new HashSet<>();

        queue.add(tile_puzzle.getRoot());

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            openList.add(current);

            ArrayList<Node> neighbours = tile_puzzle.createNodeNeighbours(current);
            nodes_amount += neighbours.size();

            for (Node neighbour : neighbours) {
                if (!closedList.contains(neighbour) && !openList.contains(neighbour)) {


                    if (tile_puzzle.isGoal(neighbour)) {
                        path = Path.buildPath(neighbour);
                        price = neighbour.getPrice();
                        return true;
                    }

                    queue.add(neighbour);
                }
            }
            closedList.add(current);
            openList.remove(current);
        }

        return false;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getTime() {
        return Utils.round(time) + " seconds";
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getNodesAmount() {
        return nodes_amount;
    }

}
