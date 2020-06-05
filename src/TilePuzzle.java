import java.util.*;

/**
 * Class for representation Tile puzzle game
 */

public class TilePuzzle {

    public static final int RED_PRICE = 30;

    private static Set<Integer> black_numbers;
    private static Set<Integer> red_numbers;
    private Node root;
    private Node goal;
    private int max_steps;

    public TilePuzzle(int[][] matrix, Set<Integer> bn, Set<Integer> rn) {
        int[][] correct_puzzle = Matrix.createCorrectTilePuzzle(matrix);

        black_numbers = bn;
        red_numbers = rn;
        root = new Node(matrix, 0, "", red_numbers);
        goal = new Node(correct_puzzle, 0, "", red_numbers);
        max_steps = calculateMaxStepsAmount(matrix);
    }

    //calculate maximum steps of algorithm
    private int calculateMaxStepsAmount(int[][] matrix) {
        int matrix_cell_amount = matrix.length * matrix[0].length;
        if (black_numbers != null) {
            int black_list_size = black_numbers.size();
            return Utils.factorial(matrix_cell_amount - black_list_size);
        }
        return Utils.factorial(matrix_cell_amount);
    }

    //if the start state is the goal state
    public static boolean isGoal(Node node, Node target) {
        return Matrix.isEqualsMatrices(node.getMatrix(), target.getMatrix());
    }

    public Node getRoot() {
        return root;
    }

    public Node getGoal() {
        return goal;
    }

    //if there is a black number that is out of place, then there is no solution
    public static boolean isPathExist(Node node) {
        if (black_numbers == null) {
            return true;
        }
        for (int temp : black_numbers) {
            for (int i = 0; i < node.getMatrix().length; i++) {
                for (int j = 0; j < node.getMatrix()[0].length; j++) {
                    int goal_row = (temp - 1) / node.getMatrix()[0].length;
                    int goal_column = (temp - 1) % node.getMatrix()[0].length;
                    int steps = Math.abs(goal_column - j) + Math.abs(goal_row - i);
                    if (steps > 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //create all available neighbours of node
    public static ArrayList<Node> createNeighboursOfNode(Node current, char[] actions) {
        ArrayList<Node> neighbours = new ArrayList<>();

        for (char action : actions) {
            Node neighbour = createNeighbourByActionForNode(current, action);
            if (neighbour != null) {
                neighbours.add(neighbour);
            }
        }

        return neighbours;
    }

    //create neighbour by actual action of moving
    public static Node createNeighbourByActionForNode(Node node, char action) {
        int[] place = Matrix.getSpaceIndexes(node.getMatrix());
        int row = place[0];
        int column = place[1];

        if (place == null) {
            return null;
        }

        int new_row = row;
        int new_column = column;
        char step = 'n';

        //check if the step is available and does not contradict to previous step
        switch (action) {
            case 'L': {
                if (column == node.getMatrix()[0].length - 1 || node.getName().endsWith("R")) {
                    return null;
                }
                step = 'L';
                new_column += 1;
                break;
            }
            case 'U': {
                if (row == node.getMatrix().length - 1 || node.getName().endsWith("D")) {
                    return null;
                }
                step = 'U';
                new_row += 1;
                break;
            }
            case 'R': {
                if (column == 0 || node.getName().endsWith("L")) {
                    return null;
                }
                step = 'R';
                new_column -= 1;
                break;
            }
            case 'D': {
                if (row == 0 || node.getName().endsWith("U")) {
                    return null;
                }
                step = 'D';
                new_row -= 1;
                break;
            }
            default: {
                break;
            }
        }

        //if number that function have to move is black return null
        if (black_numbers != null && black_numbers.contains(node.getMatrix()[new_row][new_column])) {
            return null;
        }

        int price = 1;
        //check price of moved number
        if (red_numbers != null && red_numbers.contains(node.getMatrix()[new_row][new_column])) {
            price = 30;
        }

        //create matrix with moved space (matrix with moved number)
        int[][] new_matrix = Matrix.swap(node.getMatrix(), row, column, new_row, new_column);

        //choose number that was moved
        int num = node.getMatrix()[new_row][new_column];

        //create new node with new matrix, actual price and name
        Node temp = new Node(new_matrix, node.getPrice() + price, node.getName() + "-" + num + Character.toString(step), red_numbers);
        return temp;
    }

    public int getMax_steps() {
        return max_steps;
    }
}
