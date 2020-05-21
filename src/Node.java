import java.util.Arrays;
import java.util.Set;

public class Node {

    private int[][] matrix;
    private int price;
    private int heuristic_price;
    private String name;

    public Node(int[][] m, int p, String n, Set<Integer> rn) {
        matrix = m;
        price = p;
        heuristic_price = Utils.manhattanFunction(m, rn);
        name = n;
//        print();
    }

    public void print() {
        System.out.println("name: " + name);
        System.out.println("price: " + price);
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

    public int getHeuristicPrice() {
        return heuristic_price;
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
}
