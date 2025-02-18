package ModernCiphers.DES;

import ModernCiphers.CipherTools.CipherToolkit;

public class DESEncryption {

    public String[] roundOutputs;

    static int[] initialPBox = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
    };

    static int[] finalPBox = {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
    };

    static int expansionPBox[] = {
        32, 1, 2, 3, 4, 5, 
        4, 5, 6, 7, 8, 9, 
        8, 9, 10, 11, 12, 13, 
        12, 13, 14, 15, 16, 17, 
        16, 17, 18, 19, 20, 21, 
        20, 21, 22, 23, 24, 25, 
        24, 25, 26, 27, 28, 29, 
        28, 29, 30, 31, 32, 1
    };

    static int straightPBox[] = {
        16, 7, 20, 21, 29, 12, 28, 17, 
        1, 15, 23, 26, 05, 18, 31, 10, 
        2, 8, 24, 14, 32, 27, 03, 9, 
        19, 13, 30, 06, 22, 11, 04, 25
    };

    static int sBoxes[][][] = {
        {
            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
            {0, 15, 7, 4, 14, 2, 13, 10, 3, 6, 12, 11, 9, 5, 3, 8},
            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
        },
        {
            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
            {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
            {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
            {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
        },
        {
            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
            {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
            {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
            {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
        },
        {
            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
            {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
            {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
            {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
        },
        {
            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
            {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
            {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
            {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
        },
        {
            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
            {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
            {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
            {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 10, 0, 8, 13}
        },
        {
            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
        },
        {
            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
            {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 10, 14, 9, 2},
            {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 10, 15, 3, 5, 8},
            {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 9, 3, 5, 6, 11}
        }
    };

    public static String DESSBox(String input, int sBox[][])
    {
        String row = "" + input.charAt(0) + input.charAt(5);
        String col = input.substring(1, 5);

        int rowNum = Integer.parseInt(row, 2);
        int colNum = Integer.parseInt(col, 2);

        int subValue = sBox[rowNum][colNum];

        String subString = Integer.toBinaryString(subValue);
        while(subString.length() < 4)
        {
            subString = "0" + subString;
        }

        return subString;
    }

    public DESEncryption()
    {
        this.roundOutputs = new String[16];
    }

    public static String fiestelRound(String input, String roundKey)
    {
        String expandedOutput = CipherToolkit.pBox(input, expansionPBox);
        String xorOutput = CipherToolkit.XOR(expandedOutput, roundKey);

        String sBoxOutput = "";
        
        for(int i=0; i<8; i++)
        {
            sBoxOutput += DESSBox(xorOutput.substring(i*6, i*6 + 6), sBoxes[i]);
        }

        String finalOutput = CipherToolkit.pBox(sBoxOutput, straightPBox);

        return finalOutput;
    }

    
    public void DESEncryptionProcess(String input, DESKeygen keys) 
    {
        // Original Input
        System.out.println("Original Input = " + input + "\n");

        // Process Input and Display Binary
        System.out.println("Binary Input = ");
        String binInput = CipherToolkit.hexToBin(CipherToolkit.processString(input));
        CipherToolkit.rectPack(binInput, 4, 4);

        //Doing initial permutation
        String intermediateString = CipherToolkit.pBox(binInput, initialPBox);

        for(int i=1; i<=16; i++)
        {
            System.out.println("-------------------------   ROUND " + i + "   -------------------------\n");

            String left = CipherToolkit.leftHalf(intermediateString);
            String right = CipherToolkit.rightHalf(intermediateString);

            System.out.println(CipherToolkit.binToHex(left));
            System.out.println(CipherToolkit.binToHex(right));

            String processedRight = fiestelRound(right, CipherToolkit.hexToBin(keys.DESKeys[i-1]));
            String whitener = CipherToolkit.XOR(left, processedRight);

            intermediateString = right + whitener;
            System.out.println("Generated Round Cipher = ");
            CipherToolkit.rectPack(intermediateString, 4, 8);

            String outputHexValue = CipherToolkit.binToHex(intermediateString).toUpperCase();
            System.out.println("Output Hex Value " + (i) + " = " + outputHexValue + "\n");
            this.roundOutputs[i-1] = outputHexValue;
        }

        String finalOutput = CipherToolkit.pBox(binInput, finalPBox);
        CipherToolkit.rectPack(CipherToolkit.binToHex(finalOutput).toUpperCase(), 4, 4);
    }

    public static void main(String[] args) 
    {
        String key = "AABB 0918 2736 CCDD";
        DESKeygen keys = new DESKeygen();
        keys.DESKeyGeneration(key);

        String plainText = "1234 56AB CD13 2536";
        DESEncryption encryption = new DESEncryption();
        encryption.DESEncryptionProcess(plainText, keys);
    }
}
