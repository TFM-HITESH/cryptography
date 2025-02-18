package ModernCiphers.CipherTools;

import java.util.HashMap;

public class CipherToolkit 
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

    public static String pBox(String input, int pBox[])
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

    public static String XOR(String input, String key)
    {
        StringBuilder out = new StringBuilder();

        if(input.length() != key.length())
        {
            for(int i=0; i<=Math.abs(input.length() - key.length()); i++)
            {
                if(input.length() > key.length())
                {
                    key = "0" + key;
                }
                else
                {
                    input = "0" + input;
                }
            }
        }

        for(int i=0; i<input.length(); i++)
        {
            if(input.charAt(i) == key.charAt(i))
            {
                out.append("0");
            }
            else
            {
                out.append("1");
            }
        }

        return out.toString();
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

    public static void swap(int[] S, int i, int j) {
        int temp = S[i];
        S[i] = S[j];
        S[j] = temp;
    }

    
}
