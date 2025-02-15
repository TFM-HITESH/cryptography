package TraditionalCiphers;
import java.util.Scanner;
import java.util.Arrays;

public class HillCipher {

    public static int[][] createKeyMatrix(String key, int n) {
        int[][] keyMatrix = new int[n][n];
        int k = 0;
        key = key.toUpperCase().replaceAll("[^A-Z]", "");
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                keyMatrix[i][j] = key.charAt(k++) - 'A';
            }
        }
        return keyMatrix;
    }

    public static int[] preprocessPlainText(String text, int n) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        if (text.length() % n != 0) {
            int padding = n - (text.length() % n);
            for (int i = 0; i < padding; i++) {
                text += "X";
            }
        }
        int[] plainVector = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            plainVector[i] = text.charAt(i) - 'A';
        }
        return plainVector;
    }

    public static int[][] matrixMultiply(int[][] a, int[][] b, int mod) {
        int rows = a.length;
        int cols = b[0].length;
        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = 0;
                for (int k = 0; k < b.length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
                result[i][j] %= mod;
            }
        }
        return result;
    }

    public static int[][] invertKeyMatrix(int[][] matrix, int mod) {
        int n = matrix.length;
        int[][] adj = new int[n][n];
        int[][] inv = new int[n][n];
        int det = calculateDeterminant(matrix, n) % mod;
        if (det < 0) det += mod;

        int detInverse = modularInverse(det, mod);
        if (detInverse == -1) throw new IllegalArgumentException("Matrix is not invertible");

        adjoint(matrix, adj, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inv[i][j] = (adj[i][j] * detInverse) % mod;
                if (inv[i][j] < 0) inv[i][j] += mod;
            }
        }
        return inv;
    }

    public static int calculateDeterminant(int[][] matrix, int n) {
        if (n == 1) return matrix[0][0];

        int det = 0;
        int[][] temp = new int[n][n];
        int sign = 1;

        for (int f = 0; f < n; f++) {
            getCofactor(matrix, temp, 0, f, n);
            det += sign * matrix[0][f] * calculateDeterminant(temp, n - 1);
            sign = -sign;
        }
        return det;
    }

    public static void getCofactor(int[][] matrix, int[][] temp, int p, int q, int n) {
        int i = 0, j = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp[i][j++] = matrix[row][col];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    public static void adjoint(int[][] matrix, int[][] adj, int n) {
        if (n == 1) {
            adj[0][0] = 1;
            return;
        }

        int sign = 1;
        int[][] temp = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                getCofactor(matrix, temp, i, j, n);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = (sign * calculateDeterminant(temp, n - 1));
            }
        }
    }

    public static int modularInverse(int a, int mod) {
        a = a % mod;
        for (int x = 1; x < mod; x++) {
            if ((a * x) % mod == 1) return x;
        }
        return -1;
    }

    public static String encrypt(String plainText, String key, int n) {
        int[][] keyMatrix = createKeyMatrix(key, n);
        int[] plainVector = preprocessPlainText(plainText, n);
        int[][] plainMatrix = new int[n][plainVector.length / n];
        int k = 0;

        for (int j = 0; j < plainVector.length / n; j++) {
            for (int i = 0; i < n; i++) {
                plainMatrix[i][j] = plainVector[k++];
            }
        }

        int[][] cipherMatrix = matrixMultiply(keyMatrix, plainMatrix, 26);

        StringBuilder cipherText = new StringBuilder();
        for (int j = 0; j < cipherMatrix[0].length; j++) {
            for (int i = 0; i < n; i++) {
                cipherText.append((char) (cipherMatrix[i][j] + 'A'));
            }
        }

        System.out.println("Key Matrix:");
        printMatrix(keyMatrix);

        System.out.println("Plain Text Matrix:");
        printMatrix(plainMatrix);

        System.out.println("Cipher Text Matrix:");
        printMatrix(cipherMatrix);

        return cipherText.toString();
    }

    public static String decrypt(String cipherText, String key, int n) {
        int[][] keyMatrix = createKeyMatrix(key, n);
        int[][] invKeyMatrix = invertKeyMatrix(keyMatrix, 26);

        int[] cipherVector = preprocessPlainText(cipherText, n);
        int[][] cipherMatrix = new int[n][cipherVector.length / n];
        int k = 0;

        for (int j = 0; j < cipherVector.length / n; j++) {
            for (int i = 0; i < n; i++) {
                cipherMatrix[i][j] = cipherVector[k++];
            }
        }

        int[][] plainMatrix = matrixMultiply(invKeyMatrix, cipherMatrix, 26);

        StringBuilder plainText = new StringBuilder();
        for (int j = 0; j < plainMatrix[0].length; j++) {
            for (int i = 0; i < n; i++) {
                plainText.append((char) (plainMatrix[i][j] + 'A'));
            }
        }

        System.out.println("Inverse Key Matrix:");
        printMatrix(invKeyMatrix);

        System.out.println("Cipher Text Matrix:");
        printMatrix(cipherMatrix);

        System.out.println("Decrypted Plain Text Matrix:");
        printMatrix(plainMatrix);

        return plainText.toString().replace("X", "");
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the plaintext:");
        String plainText = sc.nextLine();

        System.out.println("Enter the key:");
        String key = sc.nextLine();

        System.out.println("Enter the block size (e.g., 2 or 3):");
        int n = sc.nextInt();

        System.out.println("\nEncryption Process:");
        String cipherText = encrypt(plainText, key, n);
        System.out.println("Ciphertext: " + cipherText);

        System.out.println("\nDecryption Process:");
        String decryptedText = decrypt(cipherText, key, n);
        System.out.println("Decrypted Plaintext: " + decryptedText);
        
        sc.close();
    }
}
