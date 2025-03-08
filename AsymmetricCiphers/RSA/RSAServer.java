package AsymmetricCiphers.RSA;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import AsymmetricCiphers.HelperTools.HelperTools;

public class RSAServer {

    public static int rsaDecrypt(int cipherText, int privateKey, int commonNValue)
    {
        return HelperTools.modPowerCalculator(cipherText, privateKey, commonNValue);
    }

    public static void main(String[] args) throws Exception
    {
        ServerSocket rsaServer = new ServerSocket(5000);   
        Socket serverSocket = rsaServer.accept();

        Scanner serverInput = new Scanner(System.in);
        System.out.println("Enter p value : ");
        int pValue = serverInput.nextInt();
        System.out.println("Enter q value : ");
        int qValue = serverInput.nextInt();

        int commonNValue = pValue * qValue;

        System.out.println("Enter the PUBLIC KEY : ");
        int publicKey = serverInput.nextInt();

        int phiTotient = HelperTools.phiTotientCalculator(commonNValue);
        int privateKey = HelperTools.inverseCalculator(publicKey, phiTotient) % phiTotient;


        serverSocket.getOutputStream().write((publicKey + "\n").getBytes());
        serverSocket.getOutputStream().write((commonNValue + "\n").getBytes());

        Scanner clientInput = new Scanner(serverSocket.getInputStream());
        int cipherText = clientInput.nextInt();

        int plainText = rsaDecrypt(cipherText, privateKey, commonNValue);

        System.out.println("================================================");
        System.out.println("P AND Q VALUES CHOSEN =" + pValue + ", " + qValue);
        System.out.println("COMMON N VALUE GENERATED AND SENT TO CLIENT = " + commonNValue);
        System.out.println("================================================");
        System.out.println("PUBLIC KEY VALUE GENERATED = " + publicKey);
        System.out.println("PRIVATE KEY VALUE GENERATED = " + privateKey);
        System.out.println("================================================");
        System.out.println("CIPHERTEXT RECIEVED FROM CLIENT = " + cipherText);
        System.out.println("PLAINTEXT DECRYPTED ANSWER = " + plainText);
        System.out.println("================================================");

        serverSocket.close();
        rsaServer.close();
        serverInput.close();
        clientInput.close();
    }
}

// Choose p and q
// generate e, d
// send e, n to client


// recv M and decrypt using d
