package ModernCiphers.RC4;

import ModernCiphers.CipherTools.CipherToolkit;

public class KeyScheduler {

    public int[] S;
    public int[] K;
    
    public KeyScheduler(int vectorSize)
    {
        this.S = new int[vectorSize];
        this.K = new int[vectorSize];
    }

    public KeyScheduler(int vectorSize, int key[])
    {
        this.S = new int[vectorSize];
        this.K = new int[vectorSize];

        this.initializationProcess(key);
        this.permutationProcess();
    }

    public int[] getS()
    {
        return this.S;
    }

    public void initializationProcess(int[] key)
    {
        for(int i=0; i<S.length; i++)
        {
            this.S[i] = i;
            this.K[i] = key[i % key.length];
        }

        this.displayVectors(true);
    }

    public void displayVectors(boolean displayKey)
    {
        System.out.print("\n====================================");
        System.out.println("\nSTATE VECTOR :");
        System.out.print("Index : ");
        for(int i=0; i<this.S.length; i++)
        {
            System.out.print(i + " ");
        }
        System.out.print("\nValue : ");
        for(int i=0; i<this.S.length; i++)
        {
            System.out.print(this.S[i] + " ");
        }
        
        if(displayKey)
        {
            System.out.println("\nKEY VECTOR :");
            System.out.print("Index : ");
            for(int i=0; i<this.S.length; i++)
            {
                System.out.print(i + " ");
            }
            System.out.print("\nValue : ");
            for(int i=0; i<this.S.length; i++)
            {
                System.out.print(this.K[i] + " ");
            }
        }
        
        System.out.println("\n====================================\n");
    }

    public void permutationProcess()
    {
        int j = 0;
        for(int i=0; i<this.S.length; i++)
        {
            j = (j + this.S[i] + this.K[i]) % this.S.length;
            System.out.println("j = j + S[" + i + "] + K[" + i + "]  = " + j);
            System.out.println("Swap(S[" + i + "], S[" + j + "])");
            CipherToolkit.swap(this.S, i, j);
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
