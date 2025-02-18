package ModernCiphers.AES;

import ModernCiphers.CipherTools.CipherToolkit;

public class AESKeyExpansion 
{

    static String[] RConstants = {
        "01 00 00 00", "02 00 00 00",
        "04 00 00 00", "08 00 00 00",
        "10 00 00 00", "20 00 00 00",
        "40 00 00 00", "80 00 00 00",
        "1B 00 00 00", "36 00 00 00"
    };

    static String keySBox[][] = {
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

    public String tValues[];
    public String wValues[];
    public String AESKeys[];

    public AESKeyExpansion(String cipherKey)
    {
        this.tValues = new String[10];
        this.wValues = new String[4];

        cipherKey = CipherToolkit.processString(cipherKey);

        for(int i=0; i<this.wValues.length; i++)
        {
            this.wValues[i] = cipherKey.substring(i*8, i*8 + 8);
        }

        this.AESKeys = new String[10];
    }

    public void tValueGen(String w, int t)
    {
        String rotatedString = CipherToolkit.shiftLeft(w, 2);
        String substitutedString = "";
        
        for(int i=0; i<4; i++)
        {
            substitutedString = substitutedString + keySBox[Integer.parseInt(rotatedString.substring(i*2, i*2 + 2).charAt(0) + "", 16)][Integer.parseInt(rotatedString.substring(i*2, i*2 + 2).charAt(1) + "", 16)];
        }

        String xorString = CipherToolkit.binToHex(CipherToolkit.XOR(CipherToolkit.hexToBin(substitutedString), CipherToolkit.hexToBin(CipherToolkit.processString(RConstants[t]))));
        this.tValues[t] = xorString.toUpperCase();

        System.out.println(tValues[t]);
    }

    public String wValueGen(int t)
    {
        this.wValues[0] = CipherToolkit.binToHex(CipherToolkit.XOR(CipherToolkit.hexToBin(this.tValues[t]), CipherToolkit.hexToBin(this.wValues[0])));
        String output = wValues[0];

        for(int i=1; i<4; i++)
        {
            this.wValues[i] = CipherToolkit.binToHex(CipherToolkit.XOR(CipherToolkit.hexToBin(this.wValues[i]), CipherToolkit.hexToBin(this.wValues[i-1])));
            output += this.wValues[i];
        }

        return output.toUpperCase();
    }

    public void AESKeyGeneration()
    {
        for(int i=0; i<10; i++)
        {
            tValueGen(wValues[0], i);
            this.AESKeys[i] = this.wValueGen(i);
            System.out.println(AESKeys[i]);
        }
    }   

    public void displayKeyTable()
    {

    }

    public static void main(String[] args) 
    {
        String cipherKey = "24 75 A2 B3 34 75 56 88 31 E2 12 00 13 AA 54 87";

        AESKeyExpansion keyGen = new AESKeyExpansion(cipherKey);
        keyGen.AESKeyGeneration();
        keyGen.displayKeyTable();
    }
}
