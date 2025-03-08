package AsymmetricCiphers.DiffieMITM;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import AsymmetricCiphers.HelperTools.HelperTools;

public class DiffieMITMServer {
    public static void main(String[] args) throws Exception {
        ServerSocket diffieMITMServerA = new ServerSocket(5000);
        Socket serverSocketA = diffieMITMServerA.accept();

        ServerSocket diffieMITMServerB = new ServerSocket(5001);
        Socket serverSocketB = diffieMITMServerB.accept();


        // Key generation
        Scanner serverInput = new Scanner(System.in);
        System.out.println("Enter the common prime value : ");
        int primeValue = serverInput.nextInt();
        System.out.println("Enter the generator value : ");
        int generatorValue = serverInput.nextInt();

        System.out.println("Enter the private key for the server : ");
        int serverPrivateKey = serverInput.nextInt();

        serverSocketA.getOutputStream().write((primeValue + "\n").getBytes());
        serverSocketA.getOutputStream().write((generatorValue + "\n").getBytes());
        serverSocketB.getOutputStream().write((primeValue + "\n").getBytes());
        serverSocketB.getOutputStream().write((generatorValue + "\n").getBytes());


        int r2Value = HelperTools.modPowerCalculator(generatorValue, serverPrivateKey, primeValue);
        serverSocketA.getOutputStream().write((r2Value + "\n").getBytes());
        serverSocketB.getOutputStream().write((r2Value + "\n").getBytes());


        Scanner clientInputA = new Scanner(serverSocketA.getInputStream());
        int r1Value = clientInputA.nextInt();
        Scanner clientInputB = new Scanner(serverSocketB.getInputStream());
        int r3Value = clientInputB.nextInt();


        int sharedKey1 = HelperTools.modPowerCalculator(r1Value, serverPrivateKey, primeValue);
        int sharedKey2 = HelperTools.modPowerCalculator(r3Value, serverPrivateKey, primeValue);


        System.out.println("================================================");
        System.out.println("SHARED PRIME VALUE = " + primeValue);
        System.out.println("SHARED GENERATOR VALUE = " + generatorValue);
        System.out.println("================================================");
        System.out.println("PRIVATE KEY USED AT SERVER SIDE = " + serverPrivateKey);
        System.out.println("================================================");
        System.out.println("R1 VALUE RECIEVED FROM CLIENT A = " + r1Value);
        System.out.println("R2 VALUE SENT TO CLIENT A AND B = " + r2Value);
        System.out.println("R1 VALUE RECIEVED FROM CLIENT B = " + r3Value);
        System.out.println("================================================");
        System.out.println("SHARED KEY VALUE DERIVED FROM CLIENT A = " + sharedKey1);
        System.out.println("SHARED KEY VALUE DERIVED FROM CLIENT B = " + sharedKey2);
        System.out.println("================================================");

        diffieMITMServerA.close();
        diffieMITMServerB.close();
        serverSocketA.close();
        serverSocketB.close();
        serverInput.close();
        clientInputA.close();
        clientInputB.close();
    }
}
