package TraditionalCiphers;
import java.util.Scanner;

public class VigenereCipher {

    // Method to encrypt a string using Vigenere Cipher
    public static String encryptString(String plainText, String key) {
        StringBuilder cipherText = new StringBuilder();
        System.out.println("PT   numeric value    mod process          cipher value    CT");
        key = key.toUpperCase();
        int keyIndex = 0;

        for (char ch : plainText.toCharArray()) {
            if (Character.isLetter(ch)) {
                char upperCh = Character.toUpperCase(ch);
                int numericValue = upperCh - 'A';
                int keyShift = key.charAt(keyIndex % key.length()) - 'A';
                int cipherValue = (numericValue + keyShift) % 26;
                String modProcess = String.format("(%d + %d) mod 26", numericValue, keyShift);
                char cipherChar = (char) ('A' + cipherValue);

                // Print the table row
                System.out.printf("%c    %2d              %-20s %2d              %c\n", upperCh, numericValue, modProcess, cipherValue, cipherChar);

                cipherText.append(cipherChar);
                keyIndex++;
            } else {
                // Non-letter characters remain unchanged
                System.out.printf("%c    -               -                    -               %c\n", ch, ch);
                cipherText.append(ch);
            }
        }
        return cipherText.toString();
    }

    // Method to decrypt a string using Vigenere Cipher
    public static String decryptString(String cipherText, String key) {
        StringBuilder plainText = new StringBuilder();
        System.out.println("CT   cipher value    mod process          plain value     PT");
        key = key.toUpperCase();
        int keyIndex = 0;

        for (char ch : cipherText.toCharArray()) {
            if (Character.isLetter(ch)) {
                char upperCh = Character.toUpperCase(ch);
                int cipherValue = upperCh - 'A';
                int keyShift = key.charAt(keyIndex % key.length()) - 'A';
                int numericValue = (cipherValue - keyShift + 26) % 26;
                String modProcess = String.format("(%d - %d) mod 26", cipherValue, keyShift);
                char plainChar = (char) ('A' + numericValue);

                // Print the table row
                System.out.printf("%c    %2d              %-20s %2d              %c\n", upperCh, cipherValue, modProcess, numericValue, plainChar);

                plainText.append(Character.isUpperCase(ch) ? plainChar : Character.toLowerCase(plainChar));
                keyIndex++;
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
