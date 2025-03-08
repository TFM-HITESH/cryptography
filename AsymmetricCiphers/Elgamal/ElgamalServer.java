package AsymmetricCiphers.Elgamal;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import AsymmetricCiphers.HelperTools.HelperTools;

public class ElgamalServer 
{
    public static void main(String[] args) throws Exception 
    {
        ServerSocket elgamalServer = new ServerSocket(5000);
        Socket serverSocket = elgamalServer.accept();

        // Key Generation
        Scanner serverInput = new Scanner(System.in);
        System.out.println("Enter the prime number : ");
        int primeValue = serverInput.nextInt();
        System.out.println("Enter the generator value : ");
        int e1Value = serverInput.nextInt();
        System.out.println("Enter the private key :");
        int privateKey = serverInput.nextInt();

        int e2Value = HelperTools.modPowerCalculator(e1Value, privateKey, primeValue);

        serverSocket.getOutputStream().write((e1Value + "\n").getBytes());
        serverSocket.getOutputStream().write((e2Value + "\n").getBytes());
        serverSocket.getOutputStream().write((primeValue + "\n").getBytes());


        // Decryption
        Scanner clientInput = new Scanner(serverSocket.getInputStream());
        int c1Value = clientInput.nextInt();
        int c2Value = clientInput.nextInt();

        int plainText = (c2Value * HelperTools.modPowerCalculator(c1Value, primeValue - privateKey - 1, primeValue)) % primeValue;

        
        System.out.println("================================================");
        System.out.println("COMMON PRIME VALUE = " + primeValue);
        System.out.println("FINAL PRIVATE KEY VALUE = " + privateKey);
        System.out.println("E1 GENERATOR VALUE = " + e1Value);
        System.out.println("E2 GENERATOR VALUE = " + e2Value);
        System.out.println("FINAL PUBLIC KEY = (" + e1Value + ", " + e2Value + ", " + primeValue + ")");
        System.out.println("================================================");
        System.out.println("VALUE OF C1 = " + c1Value);
        System.out.println("VALUE OF C2 = " + c2Value);
        System.out.println("FINAL RECIEVED CIPHERTEXT = (" + c1Value + ", " + c2Value + ")");
        System.out.println("================================================");
        System.out.println("FINAL DECRYPTED PLAINTEXT = " + plainText);
        System.out.println("================================================");


        elgamalServer.close();
        serverSocket.close();
        clientInput.close();
        serverInput.close();
    }
}
