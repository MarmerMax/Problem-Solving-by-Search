import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Utils {
    public static double round(double value) {
        int places = 3;
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static boolean checkIfNodeExistsInOpenOrClosed(Node node, Set<Node> set1, Set<Node> set2) {
        for (Node temp : set1) {
            if (isEqualsMatrices(node.getMatrix(), temp.getMatrix())) {
                return true;
            }
        }
        for (Node temp : set2) {
            if (isEqualsMatrices(node.getMatrix(), temp.getMatrix())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfNodeExistsInList(Node node, Set<Node> set) {
        for (Node temp : set) {
            if (isEqualsMatrices(node.getMatrix(), temp.getMatrix())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEqualsMatrices(int[][] m1, int[][] m2) {
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[0].length; j++) {
                if (m1[i][j] != m2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Queue<Node> changeBetweenNodesInQueue(Queue<Node> queue, Set<Node> set, Node node) {
        int size = queue.size();

        Queue<Node> new_queue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return n1.getPrice() - n2.getPrice();
            }
        });

        //check if node has cheaper price than equal exists node
        while (size > 0) {
            Node temp = queue.poll();
            if (isEqualsMatrices(node.getMatrix(), temp.getMatrix())
                    && temp.getPrice() > node.getPrice()) {

                set.remove(temp); //remove expensive node from open list
                set.add(node);    //add cheap node to open list
                new_queue.add(node); //add cheap node to priority queue
            } else {
                new_queue.add(temp);
            }
            size--;
        }
        return new_queue;
    }

    public static int manhattanFunction(int[][] actual, Set<Integer> red_numbers, Set<Integer> black_numbers) {

        int[] all_steps = new int[actual.length * actual[0].length];
        int distance = 0;

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[0].length; j++) {
                int red_price = 30;
                int black_price = Integer.MAX_VALUE;
                int temp = actual[i][j];

                if (temp < 0) {
                    all_steps[0] = 0;
                } else {
                    int goal_row = (temp - 1) / actual[0].length;
                    int goal_column = (temp - 1) % actual[0].length;
                    int steps = Math.abs(goal_column - j) + Math.abs(goal_row - i);
                    all_steps[temp] = steps;

                    if(red_numbers != null && red_numbers.contains(temp)){
                        steps *= red_price;
                    }
                    if(black_numbers != null && black_numbers.contains(temp)){
                        steps *= black_price;
                    }

                    distance += steps;
                }
            }
        }

        return distance;

    }
}
