import java.util.Arrays;
import java.util.Set;

public class Node {

    private int[][] matrix;
    private int price;
    private int heuristic_price;
    private String name;
    private boolean out;

    /**
     * @param m  - initial matrix for Node
     * @param p  - price of action
     * @param n  - name (actual path to this node)
     * @param rn - red numbers set for create the right heuristic price
     */
    public Node(int[][] m, int p, String n, Set<Integer> rn) {
        matrix = m;
        price = p;
        heuristic_price = Utils.manhattanFunction(m, rn);
        name = n;
        out = false;
//        print();
    }

    public void print() {
        System.out.println("name: " + name);
//        System.out.println("price: " + price);
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
        System.out.println("________"+ price +"_________" + heuristic_price);
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

    public void markAsOut() {
        out = true;
    }

    public boolean getOut() {
        return out;
    }

    //find place of space
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
