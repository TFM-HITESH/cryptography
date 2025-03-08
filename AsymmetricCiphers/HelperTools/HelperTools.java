package AsymmetricCiphers.HelperTools;

import java.util.HashMap;
import java.util.Map;

public class HelperTools 
{   
    // Compute base^exponent % modValue
    public static int modPowerCalculator(int base, int exponent, int modValue)
    {
        int tempMod = base;

        for(int i=0; i<exponent - 1; i++)
        {
            tempMod %= modValue;
            tempMod *= base;
        }

        return tempMod % modValue;
    }   

    // Computes inverseValue^-1 % modValue
    public static int inverseCalculator(int inverseValue, int modValue)
    {
        if(checkPrime(modValue))
        {
            return modPowerCalculator(inverseValue, modValue - 2, modValue);
        }
        else
        {
            return modPowerCalculator(inverseValue, modValue - 1, modValue);
        }
    }   

    // Checks if n is prime or not
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

    // Calculates euler totien value
    public static int phiTotientCalculator(int n)
    {
        HashMap<Integer, Integer> primeFactors = new HashMap<Integer, Integer>();
        int tempNum = n;

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
            if(checkPrime(i) && tempNum % i == 0)
            {
                if(primeFactors.containsKey(i))
                {
                    primeFactors.put(i, primeFactors.get(i) + 1);
                }
                else
                {
                    primeFactors.put(i, 1);
                }   
                tempNum /= i;
                i--;
            }
        } 

        int outputTotient = 1;

        for(Map.Entry<Integer,Integer> primeFactor : primeFactors.entrySet())
        {
            if(primeFactor.getValue() == 1)
            {
                outputTotient *= (primeFactor.getKey() - 1);
            }
            else
            {
                outputTotient *= (int)(Math.pow(primeFactor.getKey(), primeFactor.getValue()) - Math.pow(primeFactor.getKey(), primeFactor.getValue() - 1));
            }
        }

        return outputTotient;
    }

}
