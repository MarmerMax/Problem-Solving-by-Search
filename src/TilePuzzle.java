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

        createCorrectPuzzle(initial_matrix);

        root = new Node(initial_matrix, 0, null, 'n');
    }

    public ArrayList<Node> createNodeNeighbours(Node node, Set<Node> closed_list, Set<Node> open_list) {
        ArrayList<Node> neighbours = new ArrayList<>();

        Node left = createNeighbourByActionForNode(node, 'L');
        Node up = createNeighbourByActionForNode(node, 'U');
        Node right = createNeighbourByActionForNode(node, 'R');
        Node down = createNeighbourByActionForNode(node, 'D');

        if (left != null && !closed_list.contains(left) && !open_list.contains(left)) {
            if (!isMatrixAlreadyExists(left, closed_list, open_list)) {
                neighbours.add(left);
            }
        }
        if (up != null && !closed_list.contains(up) && !open_list.contains(up)) {
            if (!isMatrixAlreadyExists(up, closed_list, open_list)) {
                neighbours.add(up);
            }
        }
        if (right != null && !closed_list.contains(right) && !open_list.contains(right)) {
            if (!isMatrixAlreadyExists(right, closed_list, open_list)) {
                neighbours.add(right);
            }
        }
        if (down != null && !closed_list.contains(down) && !open_list.contains(down)) {
            if (!isMatrixAlreadyExists(down, closed_list, open_list)) {
                neighbours.add(down);
            }
        }

//        if (left != null) neighbours.add(left);
//        if (up != null) neighbours.add(up);
//        if (right != null) neighbours.add(right);
//        if (down != null) neighbours.add(down);

//        Collections.sort(neighbours, new Comparator<Node>() {
//            @Override
//            public int compare(Node n1, Node n2) {
//                return n1.getPrice() - n2.getPrice();
//            }
//        });
//        for (Node n : neighbours) System.out.print(n.getPrice()+ " ");
//        System.out.println();

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
//                if (temp_column == 0 || node.getPreviousStep() == 'R') {
                if (temp_column == 0) {
                    return null;
                }
                step = 'R';
                temp_column -= 1;
                break;
            }
            case 'U': {
//                if (temp_row == 0 || node.getPreviousStep() == 'D') {
                if (temp_row == 0) {
                    return null;
                }
                step = 'D';
                temp_row -= 1;
                break;
            }
            case 'R': {
//                if (temp_column == node.getMatrix()[0].length - 1 || node.getPreviousStep() == 'L') {
                if (temp_column == node.getMatrix()[0].length - 1) {
                    return null;
                }
                step = 'L';
                temp_column += 1;
                break;
            }
            case 'D': {
//                if (temp_row == node.getMatrix().length - 1 || node.getPreviousStep() == 'U') {
                if (temp_row == node.getMatrix().length - 1) {
                    return null;
                }
                step = 'U';
                temp_row += 1;
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
        int[][] new_matrix = swap(node.getMatrix(), row, column, temp_row, temp_column);

        //choose number that was moved
        int num = node.getMatrix()[temp_row][temp_column];

        //create new node with new matrix, actual price and name
//        System.out.println(price);
        Node temp = new Node(new_matrix, price, num + Character.toString(step), action);
        return temp;
    }

    private void createCorrectPuzzle(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
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

    public boolean isGoal(Node node) {
        return isEqualsMatrices(node.getMatrix(), correct_puzzle);
    }

    private boolean isMatrixAlreadyExists(Node node, Set<Node> closed_list, Set<Node> open_list) {
        for (Node temp : closed_list) {
            if (isEqualsMatrices(node.getMatrix(), temp.getMatrix())) {
                return true;
            }
        }
        for (Node temp : open_list) {
            if (isEqualsMatrices(node.getMatrix(), temp.getMatrix())) {
                return true;
            }
        }
        return false;
    }

    private boolean isEqualsMatrices(int[][] m1, int[][] m2) {
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[0].length; j++) {
                if (m1[i][j] != m2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] swap(int[][] matrix, int i, int j, int ti, int tj) {

//        System.out.println(count + ") " + i + ", " + j + " -> " + ti + ", " + tj);
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

    public Node getRoot() {
        return root;
    }

}
