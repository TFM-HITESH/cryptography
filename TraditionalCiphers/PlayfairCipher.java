package TraditionalCiphers;
import java.util.Scanner;

public class PlayfairCipher {

    public static char[][] createKeyMatrix(String key) {
        boolean[] used = new boolean[26];
        char[][] matrix = new char[5][5];
        key = key.toUpperCase().replaceAll("J", "I");

        int row = 0, col = 0;
        for (char ch : key.toCharArray()) {
            if (Character.isLetter(ch) && !used[ch - 'A']) {
                matrix[row][col++] = ch;
                used[ch - 'A'] = true;
                if (col == 5) {
                    col = 0;
                    row++;
                }
            }
        }

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            if (ch == 'J') continue;
            if (!used[ch - 'A']) {
                matrix[row][col++] = ch;
                used[ch - 'A'] = true;
                if (col == 5) {
                    col = 0;
                    row++;
                }
            }
        }
        return matrix;
    }

    public static String preprocessPlainText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replaceAll("J", "I");
        StringBuilder processed = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            processed.append(text.charAt(i));
            if (i < text.length() - 1 && text.charAt(i) == text.charAt(i + 1)) {
                processed.append('X');
            }
        }
        if (processed.length() % 2 != 0) {
            processed.append('X');
        }
        return processed.toString();
    }

    public static int[] findPosition(char[][] matrix, char ch) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (matrix[row][col] == ch) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    public static String encryptString(String plainText, String key) {
        char[][] matrix = createKeyMatrix(key);
        plainText = preprocessPlainText(plainText);
        StringBuilder cipherText = new StringBuilder();

        System.out.println("PT Digraph   Positions    Substitution CT");

        for (int i = 0; i < plainText.length(); i += 2) {
            char a = plainText.charAt(i);
            char b = plainText.charAt(i + 1);
            int[] posA = findPosition(matrix, a);
            int[] posB = findPosition(matrix, b);

            if (posA[0] == posB[0]) {
                a = matrix[posA[0]][(posA[1] + 1) % 5];
                b = matrix[posB[0]][(posB[1] + 1) % 5];
            } else if (posA[1] == posB[1]) {
                a = matrix[(posA[0] + 1) % 5][posA[1]];
                b = matrix[(posB[0] + 1) % 5][posB[1]];
            } else {
                a = matrix[posA[0]][posB[1]];
                b = matrix[posB[0]][posA[1]];
            }

            cipherText.append(a).append(b);
            System.out.printf("%c%c          [%d,%d] [%d,%d]    %c%c\n", 
                plainText.charAt(i), plainText.charAt(i + 1), posA[0], posA[1], posB[0], posB[1], a, b);
        }
        return cipherText.toString();
    }

    public static String decryptString(String cipherText, String key) {
        char[][] matrix = createKeyMatrix(key);
        StringBuilder plainText = new StringBuilder();
    
        System.out.println("CT Digraph   Positions    Substitution PT");
    
        for (int i = 0; i < cipherText.length(); i += 2) {
            char a = cipherText.charAt(i);
            char b = cipherText.charAt(i + 1);
            int[] posA = findPosition(matrix, a);
            int[] posB = findPosition(matrix, b);
    
            if (posA[0] == posB[0]) {
                a = matrix[posA[0]][(posA[1] + 4) % 5];
                b = matrix[posB[0]][(posB[1] + 4) % 5];
            } else if (posA[1] == posB[1]) {
                a = matrix[(posA[0] + 4) % 5][posA[1]];
                b = matrix[(posB[0] + 4) % 5][posB[1]];
            } else {
                a = matrix[posA[0]][posB[1]];
                b = matrix[posB[0]][posA[1]];
            }
    
            plainText.append(a).append(b);
            System.out.printf("%c%c          [%d,%d] [%d,%d]    %c%c\n", 
                cipherText.charAt(i), cipherText.charAt(i + 1), posA[0], posA[1], posB[0], posB[1], a, b);
        }
    
        // Post-process to remove padding 'X' characters
        String result = plainText.toString();
        result = result.replaceAll("X(?=[A-Z])", ""); // Remove 'X' between letters
        if (result.endsWith("X")) {
            result = result.substring(0, result.length() - 1); // Remove trailing 'X' if any
        }
        return result;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the plaintext:");
        String plainText = sc.nextLine();

        System.out.println("Enter the key:");
        String key = sc.nextLine();

        System.out.println("\nEncryption Process:");
        String cipherText = encryptString(plainText, key);

        System.out.println("\nCiphertext: " + cipherText);

        System.out.println("\nDecryption Process:");
        String newPlainText = decryptString(cipherText, key);
        System.out.println("Decrypted Plaintext: " + newPlainText);

        sc.close();
    }
}
