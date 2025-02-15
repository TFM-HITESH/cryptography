package ModernCiphers.DES;
import java.io.*;
import java.util.*;
import ModernCiphers.CipherTools.CipherToolkit;

public class DESKeygen
{
    public String[] DESKeys;
    int keyPBox[] = {
        14, 17, 11, 24, 1, 5, 3, 28, 
        15, 6, 21, 10, 23, 19, 12, 4, 
        26, 8, 16, 7, 27, 20, 13, 2, 
        41, 52, 31, 37, 47, 55, 30, 40, 
        51, 45, 33, 48, 44, 49, 39, 56, 
        34, 53, 46, 42, 50, 36, 29, 32
    };

    int parityDropPBox[] = {
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
        String binInput = ModernCiphers.CipherTools.CipherToolkit.hexToBin(ModernCiphers.CipherTools.CipherToolkit.processString(input));
        ModernCiphers.CipherTools.CipherToolkit.rectPack(binInput, 4, 4);
        
        // Perform Parity Box Drop
        System.out.println("Cipher Key = ");
        String parityDrop = ModernCiphers.CipherTools.CipherToolkit.pBox(binInput, parityDropPBox);
        ModernCiphers.CipherTools.CipherToolkit.rectPack(parityDrop, 4, 4);

        // Splitting into two halves
        String left = ModernCiphers.CipherTools.CipherToolkit.leftHalf(parityDrop);
        String right = ModernCiphers.CipherTools.CipherToolkit.rightHalf(parityDrop);

        int amt;

        for(int i=1; i<=16; i++)
        {
            System.out.println("-------------------------   ROUND " + i + "   -------------------------\n");

            // Choosing shift amount
            amt = (i == 1 || i == 2 || i == 9 || i == 16) ? 1 : 2;
            
            // Doing left shifts
            left = ModernCiphers.CipherTools.CipherToolkit.shiftLeft(left, amt);
            right = ModernCiphers.CipherTools.CipherToolkit.shiftLeft(right, amt);

            // Moving through final P box
            String outputKey = ModernCiphers.CipherTools.CipherToolkit.pBox(left + right, this.keyPBox);
            System.out.println("Generated Key = ");
            ModernCiphers.CipherTools.CipherToolkit.rectPack(outputKey, 6, 4);

            // Converting back to Hex
            String outputHexKey = ModernCiphers.CipherTools.CipherToolkit.binToHex(outputKey).toUpperCase();
            System.out.println("Output Hex Key " + (i) + " = " + outputHexKey + "\n");
            this.DESKeys[i-1] = outputHexKey;
        }
    }
}