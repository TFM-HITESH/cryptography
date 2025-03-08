package AsymmetricCiphers.DiffieHellman;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import AsymmetricCiphers.HelperTools.HelperTools;

public class DiffieHellmanServer {
    public static void main(String[] args) throws Exception
    {
        ServerSocket diffieHellmanServer = new ServerSocket(5000);
        Socket serverSocket = diffieHellmanServer.accept();

        // Public key generation
        Scanner serverInput = new Scanner(System.in);
        System.out.println("Enter the common prime number : ");
        int primeValue = serverInput.nextInt();
        System.out.println("Enter the generator value : ");
        int generatorValue = serverInput.nextInt();
        System.out.println("Enter the private key for the server : ");
        int serverPrivateKey = serverInput.nextInt();

        // send public key
        serverSocket.getOutputStream().write((primeValue + "\n").getBytes());
        serverSocket.getOutputStream().write((generatorValue + "\n").getBytes());

        int r1Value = HelperTools.modPowerCalculator(generatorValue, serverPrivateKey, primeValue);
        serverSocket.getOutputStream().write((r1Value + "\n").getBytes());
        
        Scanner clientInput = new Scanner(serverSocket.getInputStream());
        int r2Value = clientInput.nextInt();

        int sharedKey = HelperTools.modPowerCalculator(r2Value, serverPrivateKey, primeValue);

        
        System.out.println("================================================");
        System.out.println("SHARED PRIME VALUE = " + primeValue);
        System.out.println("SHARED GENERATOR VALUE = " + generatorValue);
        System.out.println("================================================");
        System.out.println("PRIVATE KEY USED AT SERVER SIDE = " + serverPrivateKey);
        System.out.println("================================================");
        System.out.println("R1 VALUE SENT TO CLIENT = " + r1Value);
        System.out.println("R2 VALUE RECIEVED FROM CLIENT = " + r2Value);
        System.out.println("================================================");
        System.out.println("SHARED KEY VALUE DERIVED FROM R1, R2 = " + sharedKey);
        System.out.println("================================================");

        serverSocket.close();
        serverInput.close();
        clientInput.close();
    }
}
