package ModernCiphers.RC4;
import ModernCiphers.CipherTools.CipherToolkit;

public class PseudoRandomGenerator {

    int pseudoRandomKeys[];

    public PseudoRandomGenerator(int plainTextSize)
    {
        pseudoRandomKeys = new int[plainTextSize];
    }

    public void pseudoRandomKeyGenerator(int S[])
    {
        int i = 0;
        int j = 0;

        for(int k=0; k<pseudoRandomKeys.length; i++)
        {
            System.out.println("\n====================================\n");
            System.out.println("P[" + k + "] :\n");
            i = (i + 1) % S.length;
            j = (j + S[i]) % S.length;

            CipherToolkit.swap(S, i, j);
            System.out.println("Swap(S[" + i + "], S[" + j + "])");

            System.out.print("\nState Vector :");
            for(int m=0; i<S.length; i++)
            {
                System.out.print(S[m] + " ");
            }

            pseudoRandomKeys[k] = S[(S[i] + S[j]) % S.length];
            System.out.println("\nKey = S[" + ((S[i] + S[j]) % S.length) + "] = " + pseudoRandomKeys[k]);

            System.out.println("\n====================================\n");
        }

    }

    
    public static void main(String[] args) 
    {
        
    }
}
