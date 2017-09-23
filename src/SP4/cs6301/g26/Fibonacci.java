package cs6301.g26;

import java.math.BigInteger;

public class Fibonacci {
    // Do a simple linear scan algorithm: Fib[n] = Fin[n-1] + Fin[n-2];
    // Since numbers are stored with BigInteger class, use add for "+"
    static BigInteger linearFibonacci(int n) {
        BigInteger prev = new BigInteger("1");
        BigInteger prev2 = new BigInteger("0");
        BigInteger temp ;
        for(int i =2;i<n;i++){
            temp = prev.add(prev2);
            prev2 = prev;
            prev = temp;
        }
       return prev;

    }

    public static  BigInteger[][] createMatrix(int row,int col){
        BigInteger[][] matrix = new BigInteger[row][col];
        for(int i=0;i<row;i++)
            for (int j=0;j<col;j++)
                matrix[i][j] = new BigInteger("0");
        return matrix;
    }

    // return c = a * b
    public static BigInteger[][] multiply(BigInteger[][] a, BigInteger[][] b) {
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        BigInteger[][] c = createMatrix(m1,n2);
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++)
                for (int k = 0; k < n1; k++)
                    c[i][j] = c[i][j].add(a[i][k].multiply(b[k][j]));
        return c;
    }

    static BigInteger[][]  power(BigInteger[][] x, int y)
    {
        if (y == 0) {
            return getIdentityMat(x[0].length);
        } else if (y%2 == 0)
            return multiply(power(x, y/2),power(x, y/2));
        else
            return multiply(x,multiply(power(x, y/2),power(x, y/2)));
    }

    static private BigInteger[][] getIdentityMat(int n) {
        BigInteger[][] identityMatrix = new BigInteger[n][n];
        for(int i =0;i<n;i++){
            for(int j= 0 ;j<n;j++){
                if(i==j){
                    identityMatrix[i][j] = BigInteger.valueOf(1);
                }
                else {
                    identityMatrix[i][j] = BigInteger.valueOf(0);
                }
            }
        }
        return identityMatrix;
    }

    // Implement O(log n) algorithm described in class (Sep 15)
    static BigInteger logFibonacci(int n) {
        BigInteger[][] fibMatrix = {{ BigInteger.valueOf(1), BigInteger.valueOf(1)},{ BigInteger.valueOf(1), BigInteger.valueOf(0)}};
        BigInteger[][] multiplier = {{ BigInteger.valueOf(1)},{ BigInteger.valueOf(0)}};
        BigInteger[][] result = multiply(power(fibMatrix,n-2),multiplier);
        return result[0][0];
    }

    public static void main(String [] args){
        int fibNum = 1000000;
        System.out.println(logFibonacci(fibNum));
        System.out.println(linearFibonacci(fibNum));
    }

}
