package AsymmetricCiphers.Elgamal;

import java.net.Socket;
import java.util.Scanner;

import AsymmetricCiphers.HelperTools.HelperTools;

public class ElgamalClient 
{
    public static void main(String[] args) throws Exception
    {
        Socket clientSocket = new Socket("localhost", 5000);

        // get public key
        Scanner serverInput = new Scanner(clientSocket.getInputStream());
        int e1Value = serverInput.nextInt();
        int e2Value = serverInput.nextInt();
        int primeValue = serverInput.nextInt();

        Scanner clientInput = new Scanner(System.in);
        System.out.println("Enter the random integer value : ");
        int randomInteger = clientInput.nextInt();
        System.out.println("Enter the plaintext value : ");
        int plainText = clientInput.nextInt();

        // Encryption
        int c1Value = HelperTools.modPowerCalculator(e1Value, randomInteger, primeValue);
        int c2Value = (plainText * HelperTools.modPowerCalculator(e2Value, randomInteger, primeValue)) % primeValue;

        clientSocket.getOutputStream().write((c1Value + "\n").getBytes());
        clientSocket.getOutputStream().write((c2Value + "\n").getBytes());


        System.out.println("================================================");
        System.out.println("E1 VALUE RECIEVED = " + e1Value);
        System.out.println("E2 VALUE RECIEVED = " + e2Value);
        System.out.println("COMMON PRIME VALUE = " + primeValue);
        System.out.println("FINAL PUBLIC KEY = (" + e1Value + ", " + e2Value + ", " + primeValue + ")");
        System.out.println("================================================");
        System.out.println("RANDOM INTEGER VALUE = " + randomInteger);
        System.out.println("PLAINTEXT VALUE = " + plainText);
        System.out.println("================================================");
        System.out.println("VALUE OF C1 = " + c1Value);
        System.out.println("VALUE OF C2 = " + c2Value);
        System.out.println("FINAL CIPHERTEXT = (" + c1Value + ", " + c2Value + ")");
        System.out.println("================================================");

    }
}
