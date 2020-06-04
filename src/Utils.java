import java.util.*;

public class Utils {

    //return number with only 3 digits after dot (example: 0.1023423 -> 0.102)
    //for time illustration
    public static double round(double value) {
        int places = 3;
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    //get the node that have same matrix
    public static Node getSameNode(Node node, Set<Node> set) {
        for (Node temp : set) {
            if (Matrix.isEqualsMatrices(node.getMatrix(), temp.getMatrix())) {
                return temp;
            }
        }
        return null;
    }

    //Check if node exists in set (open list or closed list) by checking the matrix only.
    //Default contains methods of set will return false because it check similarity by all variables of object.
    //In my implementation of Node all Node store not only matrix so if two matrices will have
    //same numbers places it will also check their other variables and it can be problematic and can return false
    //even if their matrices same. So this function resolve this problem.
    public static boolean checkIfNodeExistsInList(Node node, Set<Node> set) {
        for (Node temp : set) {
            if (Matrix.isEqualsMatrices(node.getMatrix(), temp.getMatrix())) {
                return true;
            }
        }
        return false;
    }

    //Check if the queue contains the same node with the higher price then change it by cheaper node
    public static void changeBetweenNodesInQueue(Queue<Node> queue, Set<Node> open_list, Node node) {
        for (Node temp : queue) {
            if (Matrix.isEqualsMatrices(temp.getMatrix(), node.getMatrix())) {
                if (temp.getTotalPrice() > node.getTotalPrice()) {

                    queue.remove(temp);             //remove expensive node from priority queue
                    open_list.remove(temp);         //remove expensive node from open list

                    queue.add(node);                //add cheap node to priority queue
                    open_list.add(node);            //add cheap node to open list
                }
                break;
            }
        }
    }

    //This function calculates the steps that the algorithm must do for change the matrix to the correct state
    public static int manhattanFunction(int[][] actual, Set<Integer> red_numbers) {

        //array with steps to each number (can be useful in feature)
        int[] all_steps = new int[actual.length * actual[0].length];

        //general steps
        int distance = 0;

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[0].length; j++) {
                int temp = actual[i][j];

                if (temp < 0) {
                    all_steps[0] = 0;
                } else {
                    //find row steps for the actual number
                    int goal_row = (temp - 1) / actual[0].length;

                    //find column steps for the actual number
                    int goal_column = (temp - 1) % actual[0].length;

                    //calculate all steps for the actual number
                    int steps = Math.abs(goal_column - j) + Math.abs(goal_row - i);

                    all_steps[temp] = steps;

                    //if the actual number signed as red then multiply steps by 30
                    if (red_numbers != null && red_numbers.contains(temp)) {
                        steps *= TilePuzzle.RED_PRICE;
                    }

                    distance += steps;
                }
            }
        }

        return distance;

    }

    //print all nodes of list
    public static void pintList(Set<Node> list) {
        for (Node node : list) {
            if (!node.getOut()) {
                node.print();
            }
        }
    }
}
