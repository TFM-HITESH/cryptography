package AsymmetricCiphers.DiffieMITM;

import java.net.Socket;
import java.util.Scanner;

import AsymmetricCiphers.HelperTools.HelperTools;

public class DiffieMITMClientB {
    public static void main(String[] args) throws Exception
    {
        Socket clientSocketB = new Socket("localhost", 5001);

        // shared values
        Scanner serverInput = new Scanner(clientSocketB.getInputStream());
        int primeValue = serverInput.nextInt();
        int generatorValue = serverInput.nextInt();
        int r2Value = serverInput.nextInt();

        Scanner clientInputB = new Scanner(System.in);
        System.out.println("Enter the private key for client B : ");
        int clientBPrivateKey = clientInputB.nextInt();

        int r3Value = HelperTools.modPowerCalculator(generatorValue, clientBPrivateKey, primeValue);
        clientSocketB.getOutputStream().write((r3Value + "\n").getBytes());

        int sharedKey2 = HelperTools.modPowerCalculator(r2Value, clientBPrivateKey, primeValue);

        System.out.println("================================================");
        System.out.println("SHARED PRIME VALUE = " + primeValue);
        System.out.println("SHARED GENERATOR VALUE = " + generatorValue);
        System.out.println("================================================");
        System.out.println("PRIVATE KEY USED AT CLIENT A SIDE = " + clientBPrivateKey);
        System.out.println("================================================");
        System.out.println("R3 VALUE SENT TO SERVER = " + r3Value);
        System.out.println("R2 RECIEVED FROM SERVER = " + r2Value);
        System.out.println("================================================");
        System.out.println("SHARED KEY VALUE DERIVED FROM CLIENT A = " + sharedKey2);
        System.out.println("================================================");

        clientSocketB.close();
        serverInput.close();
        clientInputB.close();
    }
}
