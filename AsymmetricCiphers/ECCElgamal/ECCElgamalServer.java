package AsymmetricCiphers.ECCElgamal;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import AsymmetricCiphers.HelperTools.ECCPoint;

public class ECCElgamalServer {
    public static void main(String[] args) throws Exception {
        ServerSocket elgamalECCServer = new ServerSocket(5000);
        Socket serverSocket = elgamalECCServer.accept(); 

        Scanner serverInput = new Scanner(System.in);
        System.out.println("Enter the prime value for the Elliptic Curve : ");
        int primeValue = serverInput.nextInt();
        System.out.println("Enter the value of A for the Elliptic Curve : ");
        int ellipticAValue = serverInput.nextInt();
        System.out.println("Enter the value of B for the Elliptic Curve : ");
        int ellipticBValue = serverInput.nextInt();
        System.out.println("Enter the value of the Shared Private Key : ");
        int sharedPrivateKey = serverInput.nextInt();

        ECCPoint cipher1Point = new ECCPoint(0, 0);
        ECCPoint cipher2Point = new ECCPoint(0, 0);
        
        Scanner clientInput = new Scanner(serverSocket.getInputStream());
        cipher1Point.xCoord = clientInput.nextInt();
        cipher1Point.yCoord = clientInput.nextInt();
        cipher2Point.xCoord = clientInput.nextInt();
        cipher2Point.yCoord = clientInput.nextInt();

        ECCPoint tempPoint = new ECCPoint(0, 0);
        for(int i=0; i<sharedPrivateKey; i++)
        {
            tempPoint = ECCPoint.addPoints(cipher1Point, cipher1Point, ellipticAValue, primeValue);
        }

        ECCPoint plaintextPoint = new ECCPoint(0, 0);
        ECCPoint negativeTempPoint = new ECCPoint(tempPoint.xCoord, -1 * tempPoint.yCoord);
        plaintextPoint = ECCPoint.addPoints(cipher2Point, negativeTempPoint, ellipticAValue, primeValue);

        System.out.println("================================================");
        System.out.println("ELLIPTICAL PRIME VALUE = " + primeValue);
        System.out.println("ELLIPTICAL A VALUE =  " + ellipticAValue);
        System.out.println("ELLIPTICAL B VALUE =  " + ellipticBValue);
        System.out.println("================================================");
        System.out.println("SHARED PRIVATE KEY VALUE = " + sharedPrivateKey);
        System.out.println("================================================");
        System.out.println("CIPHER 1, 2 RECIEVED FROM CLIENT = { (" +  cipher1Point.xCoord + ", " + cipher1Point.yCoord + "), (" + cipher2Point.xCoord + ", " + cipher2Point.yCoord + ") }");
        System.out.println("================================================");
        System.out.println("PLAINTEXT MESSAGE X COORDINATE = " + plaintextPoint.xCoord);
        System.out.println("PLAINTEXT MESSAGE Y COORDINATE = " + plaintextPoint.yCoord);
        System.out.println("================================================");

        elgamalECCServer.close();
        serverSocket.close();
        serverInput.close();
        clientInput.close();
    }
}
