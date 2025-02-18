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
            System.out.println(wValues[i]);
        }

        AESKeys = new String[10];

    }

    public void wValueGen()
    {

    }

    public void AESKeyGeneration()
    {
        


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
