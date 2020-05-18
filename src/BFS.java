import java.util.*;

public class BFS extends Algorithm {

    private TilePuzzle tile_puzzle;
    private int price = 0;
    private int nodes_amount = 1;
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
        if(tile_puzzle.isGoal(tile_puzzle.getRoot())){
            return true;
        }

        Set<Node> openList = new HashSet<>();
        Set<Node> closedList = new HashSet<>();

        ArrayList<ArrayList<Node>> allRoutes = new ArrayList<>();
        ArrayList<Node> root = new ArrayList<>();
        root.add(tile_puzzle.getRoot());
        allRoutes.add(root);

        queue.add(tile_puzzle.getRoot());

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            openList.add(current);

            ArrayList<Node> neighbours = tile_puzzle.createNodeNeighbours(current, closedList, openList);
            nodes_amount += neighbours.size();

            for (Node neighbour : neighbours) {
                if (!closedList.contains(neighbour) && !openList.contains(neighbour)) {

                    addToRoutes(current, neighbour, allRoutes);

                    if (tile_puzzle.isGoal(neighbour)) {
                        path = buildPath(allRoutes);
                        return true;
                    }

                    queue.add(neighbour);
                }
            }
            removeOldRoute(current, allRoutes);
            closedList.add(current);
            openList.remove(current);
        }

        return false;
    }

    private void addToRoutes(Node current, Node next, ArrayList<ArrayList<Node>> allRoutes) {
        ArrayList<Node> newRoute;
        for (ArrayList<Node> currentRoute : allRoutes) {
            if (currentRoute.get(currentRoute.size() - 1).equals(current)) {
                newRoute = new ArrayList<>(currentRoute);
                newRoute.add(next);
                allRoutes.add(newRoute);
                break;
            }
        }
    }

    private void removeOldRoute(Node current, ArrayList<ArrayList<Node>> allRoutes) {
        int index = 0;
        for (ArrayList<Node> currentPath : allRoutes) {
            if (currentPath.get(currentPath.size() - 1).equals(current)) {
                break;
            } else {
                index++;
            }
        }
        allRoutes.remove(index);
    }

    private String buildPath(ArrayList<ArrayList<Node>> allRoutes) {
        String path = "";
        ArrayList<Node> route;
        for (ArrayList<Node> currentRoute : allRoutes) {
            if (tile_puzzle.isGoal(currentRoute.get(currentRoute.size() - 1))) {
                route = new ArrayList<>(currentRoute);
                for (Node node : route) {
                    if (node.getName() != null) {
                        path = path + "-" + node.getName();
                        price += node.getPrice();
                    }
                }
                break;
            }
        }

        if (path.startsWith("-")) {
            path = path.substring(1);
        }

        return path;
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
    public int getPrice(){
        return price;
    }

    @Override
    public int getNodesAmount(){
        return nodes_amount;
    }

//    @Override
//    public int findNodeAmount() {
//        return 0;
//    }
//
//    @Override
//    public int findCost() {
//        return 0;
//    }
//
//    @Override
//    public double findTime() {
//        return 0;
//    }
}
