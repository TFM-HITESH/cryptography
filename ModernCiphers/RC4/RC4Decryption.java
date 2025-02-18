package ModernCiphers.RC4;

import ModernCiphers.CipherTools.CipherToolkit;

public class RC4Decryption {
    int[] plainText;

    RC4Decryption(int cipherTextSize)
    {
        plainText = new int[cipherTextSize];
    }

    public void encrypt(int input[], int key[])
    {
        for(int i=0; i<input.length; i++)
        {
            plainText[i] = Integer.parseInt(CipherToolkit.XOR(Integer.toBinaryString(input[i]), Integer.toBinaryString(key[i])), 2);
        }
    }

    public void display(int input[], int key[]) {
        System.out.println("----------------------------------");
        System.out.printf("%-10s %-10s %-10s%n", "Ciphertext", "Key", "Plaintext");
        System.out.println("----------------------------------");
    
        for (int i = 0; i < this.plainText.length; i++) {
            System.out.printf("%-10d %-10d %-10d%n", input[i], key[i], this.plainText[i]);
        }
    
        System.out.println("----------------------------------");
    }
    public static void main(String[] args) {
        
        int cipherText[] = {0, 3, 3, 4};
        int key[] = {1, 2, 2, 2};

        PseudoRandomGenerator pseudoKeys = new PseudoRandomGenerator(cipherText.length);
        KeyScheduler randomKeys = new KeyScheduler(8, key);

        pseudoKeys.pseudoRandomKeyGenerator(randomKeys.getS());

        RC4Encryption rc4 = new RC4Encryption(cipherText.length);
        rc4.encrypt(cipherText, pseudoKeys.pseudoRandomKeys);
        rc4.display(cipherText, pseudoKeys.pseudoRandomKeys);
    }    
}
