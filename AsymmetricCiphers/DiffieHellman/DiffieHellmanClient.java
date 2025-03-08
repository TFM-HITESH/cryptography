package AsymmetricCiphers.DiffieHellman;

import java.net.Socket;
import java.util.Scanner;

import AsymmetricCiphers.HelperTools.HelperTools;

public class DiffieHellmanClient {
    public static void main(String[] args) throws Exception
    {
        Socket clientSocket = new Socket("localhost", 5000);

        Scanner serverInput = new Scanner(clientSocket.getInputStream());
        int primeValue = serverInput.nextInt();
        int generatorValue = serverInput.nextInt();
        int r1Value = serverInput.nextInt();

        Scanner clientInput = new Scanner(System.in);
        System.out.println("Enter the private key for the client :");
        int clientPrivateKey = clientInput.nextInt();

        int r2Value = HelperTools.modPowerCalculator(generatorValue, clientPrivateKey, primeValue);
        clientSocket.getOutputStream().write((r2Value + "\n").getBytes());

        int sharedKey = HelperTools.modPowerCalculator(r1Value, clientPrivateKey, primeValue);


        System.out.println("================================================");
        System.out.println("SHARED PRIME VALUE = " + primeValue);
        System.out.println("SHARED GENERATOR VALUE = " + generatorValue);
        System.out.println("================================================");
        System.out.println("PRIVATE KEY USED AT SERVER SIDE = " + clientPrivateKey);
        System.out.println("================================================");
        System.out.println("R1 VALUE RECIEVED FROM CLIENT = " + r1Value);
        System.out.println("R2 VALUE SENT TO SERVER = " + r2Value);
        System.out.println("================================================");
        System.out.println("SHARED KEY VALUE DERIVED FROM R1, R2 = " + sharedKey);
        System.out.println("================================================");

        clientSocket.close();
        serverInput.close();
        clientInput.close();
    }
}
