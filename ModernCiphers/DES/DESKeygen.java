package ModernCiphers.DES;
import java.io.*;
import java.util.*;

public class DESKeygen
{
    public static String processString(String hexString)
    {
        StringBuilder out = new StringBuilder();
        for(char c : hexString.toCharArray())
        {
            if(c != ' ')
            {
                out.append(c);
            }
        }

        return out.toString();
    }

    public static String binToHex(String binString)
    {
        StringBuilder out = new StringBuilder();
        for(int i=0; i<binString.length()/4; i++)
        {
            out.append(Integer.toHexString(Integer.parseInt(binString.substring(i*4, i*4+4), 2)));
        }

        return out.toString();
    }
    public static String hexToBin(String hexString)
    {
        HashMap<String, String> hexMappings = new HashMap<String, String>();

        hexMappings.put("0", "0000");
        hexMappings.put("1", "0001");
        hexMappings.put("2", "0010");
        hexMappings.put("3", "0011");
        hexMappings.put("4", "0100");
        hexMappings.put("5", "0101");
        hexMappings.put("6", "0110");
        hexMappings.put("7", "0111");
        hexMappings.put("8", "1000");
        hexMappings.put("9", "1001");
        hexMappings.put("A", "1010");
        hexMappings.put("B", "1011");
        hexMappings.put("C", "1100");
        hexMappings.put("D", "1101");
        hexMappings.put("E", "1110");
        hexMappings.put("F", "1111");

        StringBuilder output = new StringBuilder();
        for(char hex : hexString.toCharArray())
        {
            output.append(hexMappings.get(hex + ""));
        }

        return output.toString();
    }

    public static void rectPack(String binString, int packs, int numPacks)
    {
        int pack = 1;
        numPacks++;

        for(int i=1; i<=binString.length(); i++)
        {
            System.out.print(binString.charAt(i-1));
            if(i%packs == 0)
            {
                System.out.print(" ");
                pack++;
            }
            if(pack % numPacks == 0)
            {
                System.out.println();
                pack = 1;
            }
        }
        System.out.println();
    }

    public static String compressionPBox(String input, int pBox[])
    {
        StringBuilder out = new StringBuilder();

        for(int n : pBox)
        {
            out.append(input.charAt(n-1));
        }

        return out.toString();
    }

    public static String leftHalf(String input)
    {
        return input.substring(0, input.length()/2);
    }

    public static String rightHalf(String input)
    {
        return input.substring(input.length()/2, input.length());
    }

    public static String shiftLeft(String input, int amt)
    {
        return (input.substring(amt, input.length()) + input.substring(0, amt));
    }
    
    public static void main(String[] args) 
    {  
        // Original Input
        String input = "AABB 0918 2736 CCDD";
        System.out.println("Original Input = " + input + "\n");

        // Process Input and Display Binary
        System.out.println("Binary Input = ");
        String binInput = hexToBin(processString(input));
        rectPack(binInput, 4, 4);
        
        // Perform Parity Box Drop
        int parityDropPBox[] = {
            57, 49, 41, 33, 25, 17, 9, 1, 
            58, 50, 42, 34, 26, 18, 10, 2, 
            59, 51, 43, 35, 27, 19, 11, 3, 
            60, 52, 44, 36, 63, 55, 47, 39, 
            31, 23, 15, 7, 62, 54, 46, 38, 
            30, 22, 14, 6, 61, 53, 45, 37, 
            29, 21, 13, 5, 28, 20, 12, 4
        };
        System.out.println("Cipher Key = ");
        String parityDrop = compressionPBox(binInput, parityDropPBox);
        rectPack(parityDrop, 4, 4);

        // Splitting into two halves
        String left = leftHalf(parityDrop);
        String right = rightHalf(parityDrop);

        String keys[] = new String[16];
        int amt;

        int keyPBox[] = {
            14, 17, 11, 24, 1, 5, 3, 28, 
            15, 6, 21, 10, 23, 19, 12, 4, 
            26, 8, 16, 7, 27, 20, 13, 2, 
            41, 52, 31, 37, 47, 55, 30, 40, 
            51, 45, 33, 48, 44, 49, 39, 56, 
            34, 53, 46, 42, 50, 36, 29, 32
        };

        for(int i=1; i<=16; i++)
        {
            // System.out.println("-------------------------   ROUND " + i + "   -------------------------");

            // Choosing shift amount
            if(i == 1 || i == 2 || i == 9 || i == 16)
            {
                amt = 1;
            }
            else
            {
                amt = 2;
            }
            
            // Doing left shifts
            left = shiftLeft(left, amt);
            right = shiftLeft(right, amt);

            // Moving through final P box
            String outputKey = compressionPBox(left + right, keyPBox);
            // System.out.println("Generated Key = ");
            // rectPack(outputKey, 6, 2);

            // Converting back to Hex
            String outputHexKey = binToHex(outputKey).toUpperCase();
            System.out.println("Output Hex Value " + (i+1) + " = " + outputHexKey);
            keys[i-1] = outputHexKey;
        }
    }
}