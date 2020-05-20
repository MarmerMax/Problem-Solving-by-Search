import java.util.*;

public class TilePuzzle {

    private int[][] correct_puzzle;

    private int[][] initial_matrix;
    private Set<Integer> black_numbers;
    private Set<Integer> red_numbers;

    private Node root;

    public TilePuzzle(int[][] matrix, Set<Integer> bn, Set<Integer> rn) {
        initial_matrix = matrix;
        black_numbers = bn;
        red_numbers = rn;

        createCorrectPuzzle();

        root = new Node(initial_matrix, 0, "S");
    }

    public boolean isGoal(Node node) {
        return Utils.isEqualsMatrices(node.getMatrix(), correct_puzzle);
    }

    public Node getRoot() {
        return root;
    }

    public Set<Integer> getRedNumbers() {
        return red_numbers;
    }

    public Set<Integer> getBlackNumbers() {
        return red_numbers;
    }

    public int [][] getCorrectPuzzle() {
        return correct_puzzle;
    }

    private void createCorrectPuzzle() {
        int rows = initial_matrix.length;
        int columns = initial_matrix[0].length;
        correct_puzzle = new int[rows][columns];
        int num = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i + 1) * (j + 1) == rows * columns) {
                    correct_puzzle[i][j] = -1;
                } else {
                    correct_puzzle[i][j] = num++;
                }
            }
        }
    }


    public ArrayList<Node> createNodeNeighbours(Node node, Set<Node> closed_list, Set<Node> open_list, boolean isHeuristic) {
        ArrayList<Node> neighbours = new ArrayList<>();

        Node left = createNeighbourByActionForNode(node, 'L', isHeuristic);
        Node up = createNeighbourByActionForNode(node, 'U', isHeuristic);
        Node right = createNeighbourByActionForNode(node, 'R', isHeuristic);
        Node down = createNeighbourByActionForNode(node, 'D', isHeuristic);

        if (left != null && !closed_list.contains(left) && !open_list.contains(left)) {
            if (!Utils.checkIfNodeExistsInOpenOrClosed(left, closed_list, open_list)) {
                neighbours.add(left);
            }
        }
        if (up != null && !closed_list.contains(up) && !open_list.contains(up)) {
            if (!Utils.checkIfNodeExistsInOpenOrClosed(up, closed_list, open_list)) {
                neighbours.add(up);
            }
        }
        if (right != null && !closed_list.contains(right) && !open_list.contains(right)) {
            if (!Utils.checkIfNodeExistsInOpenOrClosed(right, closed_list, open_list)) {
                neighbours.add(right);
            }
        }
        if (down != null && !closed_list.contains(down) && !open_list.contains(down)) {
            if (!Utils.checkIfNodeExistsInOpenOrClosed(down, closed_list, open_list)) {
                neighbours.add(down);
            }
        }
//
//        if (left != null) neighbours.add(left);
//        if (up != null) neighbours.add(up);
//        if (right != null) neighbours.add(right);
//        if (down != null) neighbours.add(down);

        return neighbours;
    }

    private Node createNeighbourByActionForNode(Node node, char action, boolean isHeuristic) {
        int[] place = node.getSpaceIndexes();
        int row = place[0];
        int column = place[1];

        if (place == null) {
            return null;
        }

        int temp_row = row;
        int temp_column = column;
        char step = 'n';

        //check if step is available and does not contradict to previous step
        switch (action) {
            case 'L': {
                if (column == node.getMatrix()[0].length - 1 || node.getName().endsWith("R")) {
                    return null;
                }
                step = 'L';
                temp_column += 1;
                break;
            }
            case 'U': {
                if (row == node.getMatrix().length - 1 || node.getName().endsWith("D")) {
                    return null;
                }
                step = 'U';
                temp_row += 1;
                break;
            }
            case 'R': {
                if (column == 0 || node.getName().endsWith("L")) {
                    return null;
                }
                step = 'R';
                temp_column -= 1;
                break;
            }
            case 'D': {
                if (row == 0 || node.getName().endsWith("U")) {
                    return null;
                }
                step = 'D';
                temp_row -= 1;
                break;
            }
            default: {
                break;
            }
        }

        //can't move black number
        if (black_numbers != null && black_numbers.contains(node.getMatrix()[temp_row][temp_column])) {
            return null;
        }

        //create matrix with moved space
        int[][] new_matrix = swap(node.getMatrix(), row, column, temp_row, temp_column);

        int price = 1;
        //check price of moved number
        if (red_numbers != null && red_numbers.contains(node.getMatrix()[temp_row][temp_column])) {
            price = 30;
        }

        //if this node need to add heuristic price
        if(isHeuristic){
            price += Utils.manhattanFunction(new_matrix, red_numbers, black_numbers);
        }

        //choose number that was moved
        int num = node.getMatrix()[temp_row][temp_column];

        //create new node with new matrix, actual price and name
        Node temp = new Node(new_matrix, price, num + Character.toString(step));
        return temp;
    }


    private int[][] swap(int[][] matrix, int i, int j, int ti, int tj) {
        int[][] new_mat = copyMatrix(matrix);

        int temp = new_mat[ti][tj];
        new_mat[i][j] = temp;
        new_mat[ti][tj] = -1;

        return new_mat;
    }

    private int[][] copyMatrix(int[][] matrix) {
        int[][] new_mat = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < new_mat.length; i++) {
            for (int j = 0; j < new_mat[0].length; j++) {
                new_mat[i][j] = matrix[i][j];
            }
        }
        return new_mat;
    }
}
