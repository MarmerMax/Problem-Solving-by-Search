public class Matrix {

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

    public static int[][] swap(int[][] matrix, int i, int j, int ti, int tj) {
        int[][] new_mat = copyMatrix(matrix);

        int temp = new_mat[ti][tj];
        new_mat[i][j] = temp;
        new_mat[ti][tj] = -1;

        return new_mat;
    }

    public static int[][] copyMatrix(int[][] matrix) {
        int[][] new_mat = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < new_mat.length; i++) {
            for (int j = 0; j < new_mat[0].length; j++) {
                new_mat[i][j] = matrix[i][j];
            }
        }
        return new_mat;
    }
}
