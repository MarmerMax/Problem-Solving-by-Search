import java.util.Arrays;

public class Node {

    private int[][] matrix;
    private int price;
//    private int price_with_manhattan;
    private String name;

    public Node(int[][] m, int p, String n) {
        matrix = m;
        price = p;
//        price_with_manhattan = price;
        name = n;
    }

    public void print() {
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

    public void calculatePriceWithHeuristic(int manhattan) {
        price += manhattan;
    }
//
//    public int getPriceWithManhattan() {
//        return price_with_manhattan;
//    }
}
