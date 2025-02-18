package ModernCiphers.RC4;
import ModernCiphers.CipherTools.CipherToolkit;

public class PseudoRandomGenerator {

    int pseudoRandomKeys[];

    public PseudoRandomGenerator(int plainTextSize)
    {
        this.pseudoRandomKeys = new int[plainTextSize];
    }

    public void pseudoRandomKeyGenerator(int S[])
    {
        int i = 0;
        int j = 0;

        for(int k=0; k<this.pseudoRandomKeys.length; k++)
        {
            System.out.println("\n====================================\n");
            System.out.println("P[" + k + "] :\n");
            i = (i + 1) % S.length;
            j = (j + S[i]) % S.length;

            CipherToolkit.swap(S, i, j);
            System.out.println("Swap(S[" + i + "], S[" + j + "])");

            System.out.print("\nState Vector : ");
            for(int m=0; m<S.length; m++)
            {
                System.out.print(S[m] + " ");
            }

            this.pseudoRandomKeys[k] = S[(S[i] + S[j]) % S.length];
            System.out.println("\nKey = S[" + ((S[i] + S[j]) % S.length) + "] = " + this.pseudoRandomKeys[k]);

            System.out.println("\n====================================\n");
        }

    }

    
    public static void main(String[] args) 
    {
        PseudoRandomGenerator pseudoKeys = new PseudoRandomGenerator(4);
        KeyScheduler randomKeys = new KeyScheduler(8, new int[] {1, 2, 3, 6});

        pseudoKeys.pseudoRandomKeyGenerator(randomKeys.getS());
    }
}
