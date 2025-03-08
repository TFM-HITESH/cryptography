package AsymmetricCiphers.RSA;

import java.util.*;

public class BasicRSA 
{
    public static void main(String[] args) {
        // take input primes from the user
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the first prime : ");
        int p = sc.nextInt();
        System.out.println("Please enter the second prime : ");
        int q = sc.nextInt();

        // calculate product
        int n = p * q;

        // calculate phi(n)
        int phi_n = phiCalculator(n);

        // System.out.println(modCalculator(13, 15, 60));
        // public key chooser
        System.out.println("Enter the public key for Reciever : ");
        int e = sc.nextInt();

        if(checkValid(e, phi_n))
        {
            int d = eInverse(e, phi_n) % phi_n;

            System.out.println("PUBLIC KEY = " + e);
            System.out.println("PRIVATE KEY = " + d);


            System.out.println();
            System.out.println("ENCRYPTION ");

            System.out.println("Please enter the plaintext message :");
            int M = sc.nextInt();
            
            int C = modCalculator(M, e, n);
            // int C = (int)Math.pow(M, e) % n;
            System.out.println("Encrypted Cipher Text  = " + C);

            System.out.println("=====================================");

            System.out.println();
            System.out.println("DECRYPTION ");

            System.out.println("Please enter the ciphertext message :");
            int C_ = sc.nextInt();  

            int P = modCalculator(C_, d, n);
            // int P = (int)Math.pow(C_, d) % n;
            System.out.println("Decrypted Plain Text  = " + P);
        }
        else
        {
            System.out.println("Public key is not valid. Choose a proper keypair !");
        }

        System.out.println("Completed !");
    }

    public static boolean checkValid(int e, int phi_n)
    {
        return (1 < e) && (e < phi_n) && (gcd(e, phi_n) == 1);
    }

    public static int gcd(int a, int b)
    {
        int min = a < b ? a : b;

        min -= 1;

        for(int i=min; i>0; i--)
        {
            if(a%i == 0 && b%i  == 0)
            {
                return i;
            }
        }

        return 1;
    }


    public static int eInverse(int e, int phi_n)
    {

        if(checkPrime(phi_n))
        {
            return modCalculator(e, phi_n - 2, phi_n);
            // return (int)Math.pow(e, phi_n - 2) % phi_n;
        }
        else
        {
            return modCalculator(e, phiCalculator(phi_n) - 1, phi_n);
            // return (int)Math.pow(e, phiCalculator(phi_n) - 1) % phi_n;
        }
    }

    public static boolean checkPrime(int n)
    {
        for(int i=2; i<n; i++)
        {
            if(n % i == 0)
            {
                return false;
            }
        }
        return true;
    }

    public static int phiCalculator(int n)
    {
        HashMap<Integer, Integer> primeFactors = new HashMap<Integer, Integer>();
        int num = n;

        if(n <= 1)
        {
            return 0;
        }

        if(checkPrime(n))
        {
            return n-1;
        }

        for(int i=2; i<=n; i++)
        {
            if(checkPrime(i) && num % i == 0)
            {
                if(primeFactors.containsKey(i))
                {
                    primeFactors.put(i, primeFactors.get(i) + 1);
                }
                else
                {
                    primeFactors.put(i, 1);
                }   
                num /= i;
                i--;
            }
        }   

        int output = 1;

        for(int i=2; i<n; i++)
        {
            if(primeFactors.containsKey(i))
            {
                if(primeFactors.get(i) == 1)
                {
                    output *= (i - 1);
                }
                else
                {
                    output *= ((int)Math.pow(i, primeFactors.get(i)) - (int)Math.pow(i, primeFactors.get(i) - 1));
                }
            }
        }

        return output;
    }

    public static int modCalculator(int e, int pow, int n)
    {
        int mod = e;

        for(int i=0; i<pow-1; i++)
        {
            mod %= n;
            mod *= e;
        }

        return mod % n;
    }
}
