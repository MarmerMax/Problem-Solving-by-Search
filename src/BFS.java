import java.util.*;

public class BFS extends Algorithm{

    private TilePuzzle tilePuzzle;
    private int price;
    private int nodesAmount;
    private String path;
    private double time;
    private Queue<Node> queue;

    public BFS(){
        queue = new ArrayDeque<>();
    }

    public void setTilePuzzle(TilePuzzle tp){
        tilePuzzle = tp;
    }


    @Override
    public boolean findPath() {

//        if(tilePuzzle.isGoal(start)){
//            return "stay home";
//        }

        Set<Node> openList = new HashSet<>();
        Set<Node> closedList = new HashSet<>();

        ArrayList<ArrayList<Node>> allRoutes = new ArrayList<>();
        ArrayList<Node> root = new ArrayList<>();
        root.add(tilePuzzle.getRoot());
        allRoutes.add(root);

        queue.add(tilePuzzle.getRoot());
        int count = 1;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            openList.add(current);

            ArrayList<Node> neighbours = tilePuzzle.createNodeNeighbours(current, closedList, openList);
            count += neighbours.size();

            for (Node neighbour : neighbours) {
                if (!closedList.contains(neighbour) && !openList.contains(neighbour)) {

                    addToRoutes(current, neighbour, allRoutes);

                    if (tilePuzzle.isGoal(neighbour)) {
//                        path = buildPath(goal, allRoutes);
//                        System.out.println(count);
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

    private String buildPath(Node target, ArrayList<ArrayList<Node>> allRoutes) {
        String path = "";
        ArrayList<Node> route;
        for (ArrayList<Node> currentRoute : allRoutes) {
            if (currentRoute.get(currentRoute.size() - 1).equals(target)) {
                route = new ArrayList<>(currentRoute);
                for (Node node : route) {
                    if (path.equals("")) {
                        path = node.getName();
                    } else {
                        path = path + " -> " + node;
                    }
                }
                break;
            }
        }

        if (path.endsWith(" -> ")) {
            path = path.substring(0, path.length() - 4);
        }

        return path;
    }

    public String getPath() {
        return path;
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
