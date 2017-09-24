package cs6301.g26;
import java.math.BigInteger;

/**
 * Finds the fibonacci numbers
 * @author Sharath
 */
public class Fibonacci {
    /**
     * A simple linear scan algorithm: Fib[n] = Fin[n-1] + Fin[n-2]; to find the asked
     * fibonacci number
     *
     * @param n : The fibonacci number position in the series
     * @return : The fibonacci number
     */
    static BigInteger linearFibonacci(int n) {
        BigInteger prev = new BigInteger("1");
        BigInteger prev2 = new BigInteger("0");
        BigInteger temp;
        for (int i = 2; i < n; i++) {
            temp = prev.add(prev2);
            prev2 = prev;
            prev = temp;
        }
        return prev;

    }

    /**
     * Create a zero matrix of specified size
     * @param row : Required number of rows
     * @param col : Required number of column
     * @return : Zero matrix of size row x col
     */
    public static BigInteger[][] createMatrix(int row, int col) {
        BigInteger[][] matrix = new BigInteger[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                matrix[i][j] = new BigInteger("0");
        return matrix;
    }

    /**
     * Performs multiplication of 2 matrices
     * @param a : matrix a
     * @param b : matrix b
     * @return : product of A and b
     */
    public static BigInteger[][] multiply(BigInteger[][] a, BigInteger[][] b) {
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        BigInteger[][] c = createMatrix(m1, n2);
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++)
                for (int k = 0; k < n1; k++)
                    c[i][j] = c[i][j].add(a[i][k].multiply(b[k][j]));
        return c;
    }

    /**
     * Finds the power of a matrix by using divide and conquer algorithm
     * @param x : the matrix whose power have to me found
     * @param y : power
     * @return : a matrix which is multiplied with itself y times.
     */
    static BigInteger[][] power(BigInteger[][] x, int y) {
        if (y == 0) {
            return getIdentityMat(x[0].length);
        } else if (y % 2 == 0)
            return multiply(power(x, y / 2), power(x, y / 2));
        else
            return multiply(x, multiply(power(x, y / 2), power(x, y / 2)));
    }

    /**
     * Return an Identity matrix of the required size.
     * @param n : the size of the square matrix required.
     * @return : Identity matrix of size n x n.
     */
    static private BigInteger[][] getIdentityMat(int n) {
        BigInteger[][] identityMatrix = new BigInteger[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                identityMatrix[i][j] = i == j ? new BigInteger("1") : new BigInteger("0");
            }
        }
        return identityMatrix;
    }

    /**
     * A simple log(n) algorithm to find the asked
     * fibonacci number
     * @param n : The fibonacci number position in the series
     * @return : The fibonacci number
     */
    static BigInteger logFibonacci(int n) {
        BigInteger[][] fibMatrix = {{BigInteger.valueOf(1), BigInteger.valueOf(1)}, {BigInteger.valueOf(1), BigInteger.valueOf(0)}};
        BigInteger[][] multiplier = {{BigInteger.valueOf(1)}, {BigInteger.valueOf(0)}};
        BigInteger[][] result = multiply(power(fibMatrix, n - 2), multiplier);
        return result[0][0];
    }

    public static void main(String[] args) {
        int fibNum = 1000000;
        System.out.println(logFibonacci(fibNum));
        System.out.println(linearFibonacci(fibNum));
    }

}
