package ModernCiphers.RC4;
import ModernCiphers.CipherTools.CipherToolkit;

public class RC4Encryption 
{

    int[] cipherText;

    RC4Encryption(int plainTextSize)
    {
        cipherText = new int[plainTextSize];
    }

    public void encrypt(int input[], int key[])
    {
        for(int i=0; i<input.length; i++)
        {
            cipherText[i] = Integer.parseInt(CipherToolkit.XOR(Integer.toBinaryString(input[i]), Integer.toBinaryString(key[i])), 2);
        }
    }

    public void display(int input[], int key[]) {
        System.out.println("----------------------------------");
        System.out.printf("%-10s %-10s %-10s%n", "Plaintext", "Key", "Ciphertext");
        System.out.println("----------------------------------");
    
        for (int i = 0; i < this.cipherText.length; i++) {
            System.out.printf("%-10d %-10d %-10d%n", input[i], key[i], this.cipherText[i]);
        }
    
        System.out.println("----------------------------------");
    }
    

    public static void main(String[] args) {
        
        int plainText[] = {1, 2, 3, 3};
        int key[] = {1, 2, 2, 2};

        PseudoRandomGenerator pseudoKeys = new PseudoRandomGenerator(plainText.length);
        KeyScheduler randomKeys = new KeyScheduler(8, key);

        pseudoKeys.pseudoRandomKeyGenerator(randomKeys.getS());

        RC4Encryption rc4 = new RC4Encryption(plainText.length);
        rc4.encrypt(plainText, pseudoKeys.pseudoRandomKeys);
        rc4.display(plainText, pseudoKeys.pseudoRandomKeys);
    }    
}
