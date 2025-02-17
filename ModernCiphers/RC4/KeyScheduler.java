package ModernCiphers.RC4;

import ModernCiphers.CipherTools.CipherToolkit;

public class KeyScheduler {

    public int[] S;
    public int[] K;
    
    public KeyScheduler(int vectorSize)
    {
        S = new int[vectorSize];
        K = new int[vectorSize];
    }

    public void initializationProcess(int[] key)
    {
        for(int i=0; i<S.length; i++)
        {
            S[i] = i;
            K[i] = key[i % key.length];
        }

        this.displayVectors(true);
    }

    public void displayVectors(boolean displayKey)
    {
        System.out.print("\n====================================");
        System.out.println("\nSTATE VECTOR :");
        System.out.print("Index : ");
        for(int i=0; i<S.length; i++)
        {
            System.out.print(i + " ");
        }
        System.out.print("\nValue : ");
        for(int i=0; i<S.length; i++)
        {
            System.out.print(S[i] + " ");
        }
        
        if(displayKey)
        {
            System.out.println("\nKEY VECTOR :");
            System.out.print("Index : ");
            for(int i=0; i<S.length; i++)
            {
                System.out.print(i + " ");
            }
            System.out.print("\nValue : ");
            for(int i=0; i<S.length; i++)
            {
                System.out.print(K[i] + " ");
            }
        }
        
        System.out.println("\n====================================\n");
    }

    public void permutationProcess()
    {
        int j = 0;
        for(int i=0; i<S.length; i++)
        {
            j = (j + S[i] + K[i]) % S.length;
            System.out.println("j = j + S[" + i + "] + K[" + i + "]  = " + j);
            System.out.println("Swap(S[" + i + "], S[" + j + "])");
            ModernCiphers.CipherTools.CipherToolkit.swap(S, i, j);
            this.displayVectors(false);
        }
    }
    
    public static void main(String[] args) {
        KeyScheduler randomKeys = new KeyScheduler(8);

        int[] key = {1, 2, 3, 6};

        randomKeys.initializationProcess(key);
        randomKeys.permutationProcess();
    }
}
