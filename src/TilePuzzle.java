import java.util.*;

public class TilePuzzle {

    public static final int RED_PRICE = 30;

    private int[][] correct_puzzle;
    private Set<Integer> black_numbers;
    private Set<Integer> red_numbers;
    private Node root;

    public TilePuzzle(int[][] matrix, Set<Integer> bn, Set<Integer> rn) {
        black_numbers = bn;
        red_numbers = rn;
        root = new Node(matrix, 0, "", red_numbers);

        createCorrectPuzzle();

    }

    //if exists a black number that is not in its row or column then there is no solution
    public boolean isPathExist() {
        if(black_numbers == null){
            return true;
        }
        for (int temp : black_numbers) {
            for (int i = 0; i < root.getMatrix().length; i++) {
                for (int j = 0; j < root.getMatrix()[0].length; j++) {
                    int goal_row = (temp - 1) / root.getMatrix()[0].length;
                    int goal_column = (temp - 1) % root.getMatrix()[0].length;
                    int steps = Math.abs(goal_column - j) + Math.abs(goal_row - i);
                    if (steps > 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isGoal(Node node) {
        return Matrix.isEqualsMatrices(node.getMatrix(), correct_puzzle);
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

    public int[][] getCorrectPuzzle() {
        return correct_puzzle;
    }

    private void createCorrectPuzzle() {
        int rows = root.getMatrix().length;
        int columns = root.getMatrix()[0].length;
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


    public ArrayList<Node> createNodeNeighbours(Node node) {
        ArrayList<Node> neighbours = new ArrayList<>();

        Node left = createNeighbourByActionForNode(node, 'L');
        Node up = createNeighbourByActionForNode(node, 'U');
        Node right = createNeighbourByActionForNode(node, 'R');
        Node down = createNeighbourByActionForNode(node, 'D');

        if (left != null) neighbours.add(left);
        if (up != null) neighbours.add(up);
        if (right != null) neighbours.add(right);
        if (down != null) neighbours.add(down);

        return neighbours;
    }

    private Node createNeighbourByActionForNode(Node node, char action) {
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

        int price = 1;
        //check price of moved number
        if (red_numbers != null && red_numbers.contains(node.getMatrix()[temp_row][temp_column])) {
            price = 30;
        }

        //create matrix with moved space
        int[][] new_matrix = Matrix.swap(node.getMatrix(), row, column, temp_row, temp_column);

        //choose number that was moved
        int num = node.getMatrix()[temp_row][temp_column];

        //create new node with new matrix, actual price and name
        Node temp = new Node(new_matrix, node.getPrice() + price, node.getName() + "-" + num + Character.toString(step), red_numbers);
        return temp;
    }



}
