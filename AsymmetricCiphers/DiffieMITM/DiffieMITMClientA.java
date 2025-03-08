package AsymmetricCiphers.DiffieMITM;

import java.net.Socket;
import java.util.Scanner;

import AsymmetricCiphers.HelperTools.HelperTools;

public class DiffieMITMClientA {
    public static void main(String[] args) throws Exception
    {
        Socket clientSocketA = new Socket("localhost", 5000);

        // shared values
        Scanner serverInput = new Scanner(clientSocketA.getInputStream());
        int primeValue = serverInput.nextInt();
        int generatorValue = serverInput.nextInt();
        int r2Value = serverInput.nextInt();

        Scanner clientInputA = new Scanner(System.in);
        System.out.println("Enter the private key for client A : ");
        int clientAPrivateKey = clientInputA.nextInt();

        int r1Value = HelperTools.modPowerCalculator(generatorValue, clientAPrivateKey, primeValue);
        clientSocketA.getOutputStream().write((r1Value + "\n").getBytes());

        int sharedKey1 = HelperTools.modPowerCalculator(r2Value, clientAPrivateKey, primeValue);

        System.out.println("================================================");
        System.out.println("SHARED PRIME VALUE = " + primeValue);
        System.out.println("SHARED GENERATOR VALUE = " + generatorValue);
        System.out.println("================================================");
        System.out.println("PRIVATE KEY USED AT CLIENT A SIDE = " + clientAPrivateKey);
        System.out.println("================================================");
        System.out.println("R1 VALUE SENT TO SERVER = " + r1Value);
        System.out.println("R2 RECIEVED FROM SERVER = " + r2Value);
        System.out.println("================================================");
        System.out.println("SHARED KEY VALUE DERIVED FROM CLIENT A = " + sharedKey1);
        System.out.println("================================================");

        clientSocketA.close();
        serverInput.close();
        clientInputA.close();
    }
}
