package ModernCiphers.AES;

import ModernCiphers.CipherTools.CipherToolkit;

public class AESEncryption 
{
    String[] roundOutputs;

    static String sBox[][] = {
        {"63", "7C", "77", "7B", "F2", "6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76"},
        {"CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "D4", "A2", "AF", "9C", "A4", "72", "C0"},
        {"B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15"},
        {"04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75"},
        {"09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84"},
        {"53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF"},
        {"D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8"},
        {"51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2"},
        {"D4", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73"},
        {"60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB"},
        {"E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79"},
        {"B7", "CB", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08"},
        {"BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A"},
        {"70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E"},
        {"E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF"},
        {"8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16"}
    };

    static String mixConstant[][] = {
        {"02", "03", "01", "01"},
        {"01", "02", "03", "01"},
        {"01", "01", "02", "03"},
        {"03", "01", "01", "02"}
    };

    public AESEncryption()
    {
        roundOutputs = new String[11];
    }

    public String[][] stateVectorBuilder(String input)
    {
        String output[][] = new String[4][4];
        int count = 0;

        for(int i=0; i<output.length; i++)
        {
            for(int j=0; j<output.length; j++)
            {
                output[j][i] = input.substring(count, count + 2);
                count += 2;
            }
        }
        return output;
    }

    public String stateVectorReader(String state[][])
    {
        String output = "";

        for(int i=0; i<state.length; i++)
        {
            for(int j=0; j<state.length; j++)
            {
                output += state[j][i];
            }
        }

        return output;
    }

    public void displayState(String state[][])
    {
        for(String row[] : state)
        {
            for(String s : row)
            {
                System.out.print(s.toUpperCase() + " ");
            }
            System.out.println();
        }
    }

    public String[][] shiftRows(String[][] state)
    {
        String output[][] = new String[state.length][state.length];

        for(int i=0; i<output.length; i++)
        {
            for(int j=0; j<output.length; j++)
            {
                output[i][j] = state[i][(j + i) % output.length];
            }
        }

        return output;
    }

    public String galoisMultiplication(String hex1, String hex2) {
        String num1 = CipherToolkit.hexToBin(hex1);
        String num2 = CipherToolkit.hexToBin(hex2);
        String out = "00000000";
        String irreducible = "00011011"; // 0x1B in binary

        for (int i = 0; i < 8; i++) {
            if (num2.charAt(7) == '1') {
                out = CipherToolkit.XOR(out, num1);
            }
            boolean highBitSet = num1.charAt(0) == '1';
            num1 = num1.substring(1) + "0";
            if (highBitSet) {
                num1 = CipherToolkit.XOR(num1, irreducible);
            }
            num2 = "0" + num2.substring(0, 7);
        }
        return CipherToolkit.binToHex(out);
    }

    public String matrixMultiply(String state[][], int row, int col)
    {
        String stateRow[] = new String[state.length];
        String stateCol[] = new String[state.length];

        for(int i=0; i<state.length; i++)
        {
            stateRow[i] = state[row][i];
        }

        for(int i=0; i<state.length; i++)
        {
            stateCol[i] = state[i][col];
        }

        String output = "00";
        for(int i=0; i<state.length; i++)
        {
            output = CipherToolkit.binToHex(CipherToolkit.XOR(CipherToolkit.hexToBin(output), CipherToolkit.hexToBin(galoisMultiplication(stateCol[i], stateRow[i]))));
        }

        return output;
    }

    public String[][] mixCols(String state[][])
    {
        String output[][] = new String[state.length][state.length];

        for(int i=0; i<output.length; i++)
        {
            for(int j=0; j<output.length; j++)
            {
                output[i][j] = matrixMultiply(state, i, j);
            }
        }

        return output;
    }

    public String[][] subBytes(String state[][])
    {  
        String output[][] = new  String[state.length][state.length];

        for(int i=0; i<output.length; i++)
        {
            for(int j=0; j<output.length; j++)
            {
                output[i][j] = sBox[Integer.parseInt(state[i][j].charAt(0) + "", 16)][Integer.parseInt(state[i][j].charAt(1) + "", 16)];
            }
        }

        return output;
    }

    public String[][] addRoundKey(String[][] input, String[][] key)
    {
        String output[][] = new String[input.length][input.length];

        for(int i=0; i<output.length; i++)
        {
            for(int j=0; j<output.length; j++)
            {
                output[i][j] = CipherToolkit.binToHex(CipherToolkit.XOR(CipherToolkit.hexToBin(input[i][j]), CipherToolkit.hexToBin(key[i][j])));
            }
        }
        return output;
    }

    public String[][] preRoundTransformation(String[][] state, String[][] key)
    {
        displayState(state);
        displayState(key);
        String output[][] = addRoundKey(state, key);
        this.roundOutputs[0] = stateVectorReader(output);

        System.out.println("PRE ROUND TRANSFORMATION :");
        displayState(output);
        return output;
    }

    public String[][] midRoundTransformation(String[][] state, String[][] roundKey)
    {
        String[][] subByteState = subBytes(state);
        displayState(subByteState);
        String[][] shiftRowState = shiftRows(subByteState);
        displayState(shiftRowState);
        String[][] mixColState = mixCols(shiftRowState);
        displayState(mixColState);
        String[][] addRoundKeyState = addRoundKey(mixColState, roundKey);

       
        displayState(addRoundKeyState);
        return addRoundKeyState;
    }

    public String[][] lastRoundTransformation(String[][] state, String[][] roundKey)
    {
        String[][] subByteState = subBytes(state);
        String[][] shiftRowState = shiftRows(subByteState);
        String[][] addRoundKeyState = addRoundKey(shiftRowState, roundKey);

        System.out.println("LAST ROUND OUTPUT : ");
        displayState(addRoundKeyState);
        return addRoundKeyState;
    }

    public void AESEncryptionProcess(String plainText, AESKeyExpansion keys, String key)
    {
        plainText = CipherToolkit.processString(plainText);
        String initialVector[][] = stateVectorBuilder(plainText);

        String preRoundVector[][] = preRoundTransformation(initialVector, stateVectorBuilder(CipherToolkit.processString(key)));
        this.roundOutputs[0] = stateVectorReader(preRoundVector);

        String midRoundVector[][] = new String[initialVector.length][initialVector.length];

        for(int i=1; i<11; i++)
        {
            System.out.println("STATE VECTOR AFTER ROUND " + i + " :");
            if(i == 1)
            {
                midRoundVector = midRoundTransformation(preRoundVector, stateVectorBuilder(keys.AESKeys[i-1]));
            }
            else
            {
                midRoundVector = midRoundTransformation(midRoundVector, stateVectorBuilder(keys.AESKeys[i-1]));
            }
            
            this.roundOutputs[i] = stateVectorReader(midRoundVector);
        }

        String lastRoundVector[][] = lastRoundTransformation(midRoundVector, stateVectorBuilder(keys.AESKeys[keys.AESKeys.length - 1]));
        this.roundOutputs[this.roundOutputs.length - 1]  = stateVectorReader(lastRoundVector);
        // displayState(output);
    }

    public static void main(String[] args) {
        
        String key = "1112 1314 1516 1718 1920 2122 2324 2526";
        AESKeyExpansion keys = new AESKeyExpansion(key);
        keys.AESKeyGeneration();

        String plainText = "0102 0304 0506 0708 0910 1112 1314 1516";
        AESEncryption encryption = new AESEncryption();
        encryption.AESEncryptionProcess(plainText, keys, key);
    }    
}
