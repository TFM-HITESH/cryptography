package AsymmetricCiphers.RSA;

import java.net.Socket;
import java.util.Scanner;

import AsymmetricCiphers.HelperTools.HelperTools;

public class RSAClient {

    public static int rsaEncrypt(int plainText, int publicKey, int commonNValue)
    {
        return HelperTools.modPowerCalculator(plainText, publicKey, commonNValue);
    }

    public static void main(String[] args) throws Exception 
    {
        Socket clientSocket = new Socket("localhost", 5000);

        Scanner serverInput = new Scanner(clientSocket.getInputStream());
        int publicKey = serverInput.nextInt();
        int commonNValue = serverInput.nextInt();

        Scanner clientInput = new Scanner(System.in);
        System.out.println("Enter the Plaintext : ");
        int plainText = clientInput.nextInt();
        
        int cipherText = rsaEncrypt(plainText, publicKey, commonNValue);
        clientSocket.getOutputStream().write((cipherText + "\n").getBytes());

        System.out.println("================================================");
        System.out.println("PUBLIC KEY RECIEVED FROM SERVER = " + publicKey);
        System.out.println("COMMON N VALUE RECIEVED FROM SERVER = " + commonNValue);
        System.out.println("================================================");
        System.out.println("PLAINTEXT TO BE ENCRYPTED = " + plainText);
        System.out.println("CIPHERTEXT SENT TO SERVER = " + cipherText);
        System.out.println("================================================");

        clientSocket.close();
        serverInput.close();
        clientInput.close();
    }
}


// recv e
// input message and enrypt
// send M to server