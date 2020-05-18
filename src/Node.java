import java.util.Arrays;

public class Node {

    private int[][] matrix;
    private int price;
    private String name;
    private char previous_step;

    public Node(int[][] m, int p, String n, char ps) {
        matrix = m;
        price = p;
        name = n;
//        previous_step = ps;
//        print();
    }

    private void print() {
        System.out.println("______" + name + "______");
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
        System.out.println("______________________");
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int[] getSpaceIndexes() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == -1) {
                    int[] ans = {i, j};
                    return ans;
                }
            }
        }
        return null;
    }

    public char getPreviousStep() {
        return previous_step;
    }
}
