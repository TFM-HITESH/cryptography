package AsymmetricCiphers.HelperTools;

public class ECCPoint {
    public int xCoord;
    public int yCoord;

    public ECCPoint(int xCoord, int yCoord)
    {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public static ECCPoint addPoints(ECCPoint point1, ECCPoint point2, int ellipticAValue, int primeValue)
    {
        int lambda = 0;

        if(point1.xCoord == point2.xCoord && point1.yCoord == point2.yCoord)
        {
            lambda = specialMod(((3 * point1.xCoord * point1.xCoord + ellipticAValue) * HelperTools.inverseCalculator(2 * point1.yCoord, primeValue)), primeValue);
        }
        else
        {
            lambda = specialMod(((specialMod(point2.yCoord - point1.yCoord, primeValue)) * HelperTools.inverseCalculator(specialMod(point2.xCoord - point2.yCoord, primeValue), primeValue)), primeValue);
        }

        int resultXCoord = specialMod((lambda * lambda - point1.xCoord - point2.xCoord), primeValue);
        int resultYCoord = specialMod((lambda * (point1.xCoord - resultXCoord) - point1.yCoord), primeValue);
        
        ECCPoint result = new ECCPoint(resultXCoord, resultYCoord);

        return result;
    }

    public static int specialMod(int modInput, int modValue)
    {
        while(modInput < 0)
        {
            modInput += modValue;
        }

        return modInput % modValue;
    }
}
