/**
 * Class for working with the matrices.
 */

public class Matrix {

    //create a matrix for the target state of the tile puzzle
    public static int[][] createCorrectTilePuzzle(int[][] mat) {
        int rows = mat.length;
        int columns = mat[0].length;
        int[][] matrix = new int[rows][columns];
        int num = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i + 1) * (j + 1) == rows * columns) {
                    matrix[i][j] = -1;
                } else {
                    matrix[i][j] = num++;
                }
            }
        }
        return matrix;
    }

    //compare two matrices by places of the numbers
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

    //create a new matrix by swapping the number to a new place
    public static int[][] swap(int[][] matrix, int i1, int j1, int i2, int j2) {
        int[][] new_mat = copyMatrix(matrix);

        int temp = new_mat[i2][j2];
        new_mat[i1][j1] = temp;
        new_mat[i2][j2] = -1;

        return new_mat;
    }

    //copy matrix by creating a new instance
    public static int[][] copyMatrix(int[][] matrix) {
        int[][] new_mat = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < new_mat.length; i++) {
            for (int j = 0; j < new_mat[0].length; j++) {
                new_mat[i][j] = matrix[i][j];
            }
        }
        return new_mat;
    }

    //find the space in the matrix ( _ == -1 in this implementation )
    public static int[] getSpaceIndexes(int[][] matrix) {
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
