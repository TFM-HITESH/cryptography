import java.util.Scanner;

public class CaesarCipher {

    // Method to encrypt a string
    public static String encryptString(String plainText, int key) {
        StringBuilder cipherText = new StringBuilder();
        System.out.println("PT   plain value    mod process          cipher value    CT");
        for (char ch : plainText.toCharArray()) {
            if (Character.isLetter(ch)) {
                char upperCh = Character.toUpperCase(ch);
                int numericValue = upperCh - 'A';
                int cipherValue = (numericValue + key) % 26;
                String modProcess = String.format("(%d + %d) mod 26", numericValue, key);
                char cipherChar = (char) ('A' + cipherValue);

                // Print the table row
                System.out.printf("%c    %2d              %-20s %2d              %c\n", upperCh, numericValue, modProcess, cipherValue, cipherChar);

                cipherText.append(cipherChar);
            } else {
                // Non-letter characters remain unchanged
                System.out.printf("%c    -               -                    -               %c\n", ch, ch);
                cipherText.append(ch);
            }
        }
        return cipherText.toString();
    }

    // Method to decrypt a string
    public static String decryptString(String cipherText, int key) {
        StringBuilder plainText = new StringBuilder();
        System.out.println("CT   cipher value    mod process          plain value     PT");
        for (char ch : cipherText.toCharArray()) {
            if (Character.isLetter(ch)) {
                char upperCh = Character.toUpperCase(ch);
                int cipherValue = upperCh - 'A';
                int numericValue = (cipherValue - key + 26) % 26;
                String modProcess = String.format("(%d - %d) mod 26", cipherValue, key);
                char plainChar = (char) ('A' + numericValue);

                // Print the table row
                System.out.printf("%c    %2d              %-20s %2d              %c\n", upperCh, cipherValue, modProcess, numericValue, plainChar);

                plainText.append(Character.isUpperCase(ch) ? plainChar : Character.toLowerCase(plainChar));
            } else {
                // Non-letter characters remain unchanged
                System.out.printf("%c    -               -                    -               %c\n", ch, ch);
                plainText.append(ch);
            }
        }
        return plainText.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the plaintext:");
        String plainText = sc.nextLine();

        int key = 3; // Example key value
        System.out.println("\nEncryption Process:");
        String cipherText = encryptString(plainText, key);

        System.out.println("\nCiphertext: " + cipherText);

        System.out.println("\nDecryption Process:");
        String newPlainText = decryptString(cipherText, key);

        System.out.println("Decrypted Plaintext: " + newPlainText);
    }
}
