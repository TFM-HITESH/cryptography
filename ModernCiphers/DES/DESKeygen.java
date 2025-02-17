package ModernCiphers.DES;
import java.util.*;
import ModernCiphers.CipherTools.CipherToolkit;

public class DESKeygen
{
    public String[] DESKeys;
    static int keyPBox[] = {
        14, 17, 11, 24, 1, 5, 3, 28, 
        15, 6, 21, 10, 23, 19, 12, 4, 
        26, 8, 16, 7, 27, 20, 13, 2, 
        41, 52, 31, 37, 47, 55, 30, 40, 
        51, 45, 33, 48, 44, 49, 39, 56, 
        34, 53, 46, 42, 50, 36, 29, 32
    };

    static int parityDropPBox[] = {
        57, 49, 41, 33, 25, 17, 9, 1, 
        58, 50, 42, 34, 26, 18, 10, 2, 
        59, 51, 43, 35, 27, 19, 11, 3, 
        60, 52, 44, 36, 63, 55, 47, 39, 
        31, 23, 15, 7, 62, 54, 46, 38, 
        30, 22, 14, 6, 61, 53, 45, 37, 
        29, 21, 13, 5, 28, 20, 12, 4
    };

    public DESKeygen()
    {
        DESKeys = new String[16];
    }

    public void DESKeyGeneration(String input)
    {
        // Original Input
        System.out.println("Original Input = " + input + "\n");

        // Process Input and Display Binary
        System.out.println("Binary Input = ");
        String binInput = CipherToolkit.hexToBin(CipherToolkit.processString(input));
        CipherToolkit.rectPack(binInput, 4, 4);
        
        // Perform Parity Box Drop
        System.out.println("Cipher Key = ");
        String parityDrop = CipherToolkit.pBox(binInput, parityDropPBox);
        CipherToolkit.rectPack(parityDrop, 4, 4);

        // Splitting into two halves
        String left = CipherToolkit.leftHalf(parityDrop);
        String right = CipherToolkit.rightHalf(parityDrop);

        int amt;

        for(int i=1; i<=16; i++)
        {
            System.out.println("-------------------------   ROUND " + i + "   -------------------------\n");

            // Choosing shift amount
            amt = (i == 1 || i == 2 || i == 9 || i == 16) ? 1 : 2;
            
            // Doing left shifts
            left = CipherToolkit.shiftLeft(left, amt);
            right = CipherToolkit.shiftLeft(right, amt);

            // Moving through final P box
            String outputKey = CipherToolkit.pBox(left + right, keyPBox);
            System.out.println("Generated Key = ");
            CipherToolkit.rectPack(outputKey, 6, 4);

            // Converting back to Hex
            String outputHexKey = CipherToolkit.binToHex(outputKey).toUpperCase();
            System.out.println("Output Hex Key " + (i) + " = " + outputHexKey + "\n");
            this.DESKeys[i-1] = outputHexKey;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the input Cipher Key");
        String inputKey = sc.nextLine();
        sc.close();

        DESKeygen keys = new DESKeygen();
        keys.DESKeyGeneration(inputKey);
    }
}