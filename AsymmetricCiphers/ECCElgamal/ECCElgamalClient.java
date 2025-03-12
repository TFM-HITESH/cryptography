package AsymmetricCiphers.ECCElgamal;

import java.net.Socket;
import java.util.Scanner;

import AsymmetricCiphers.HelperTools.ECCPoint;

public class ECCElgamalClient {
    public static void main(String[] args) throws Exception {
        Socket clientSocket = new Socket("localhost", 5000);

        Scanner clientInput = new Scanner(System.in);
        System.out.println("Enter the prime value for the Elliptic Curve : ");
        int primeValue = clientInput.nextInt();
        System.out.println("Enter the value of A for the Elliptic Curve : ");
        int ellipticAValue = clientInput.nextInt();
        System.out.println("Enter the value of B for the Elliptic Curve : ");
        int ellipticBValue = clientInput.nextInt();
        System.out.println("Enter the X coordinate for the Generator : ");
        int generatorXCoord = clientInput.nextInt();
        System.out.println("Enter the Y coordinate for the Generator : ");
        int generatorYCoord = clientInput.nextInt();
        System.out.println("Enter the value of the Shared Private Key : ");
        int sharedPrivateKey = clientInput.nextInt();
        System.out.println("Enter the value of the random integer : ");
        int randomIntegerValue = clientInput.nextInt();
        System.out.println("Enter the X Coordinate of the plaintext message : ");
        int plaintextXCoord = clientInput.nextInt();
        System.out.println("Enter the Y Coordinate of the plaintext message : ");
        int plaintextYCoord = clientInput.nextInt();

        
        ECCPoint generatorPoint = new ECCPoint(generatorXCoord, generatorYCoord);
        ECCPoint clientPublicKey = generatorPoint;
        // Computing public key
        for(int i=1; i<sharedPrivateKey; i++)
        {
            clientPublicKey = ECCPoint.addPoints(clientPublicKey, generatorPoint, ellipticAValue, primeValue);
        }

        ECCPoint cipher1Point = generatorPoint;
        for(int i=1; i<randomIntegerValue; i++)
        {
            cipher1Point = ECCPoint.addPoints(cipher1Point, generatorPoint, ellipticAValue, primeValue);
        }

        ECCPoint plaintextPoint = new ECCPoint(plaintextXCoord, plaintextYCoord);
        ECCPoint tempPoint = clientPublicKey;
        for(int i=1; i<randomIntegerValue; i++)
        {
            tempPoint = ECCPoint.addPoints(tempPoint, clientPublicKey, ellipticAValue, primeValue);
        }

        ECCPoint cipher2Point = ECCPoint.addPoints(plaintextPoint, tempPoint, ellipticAValue, primeValue);

        clientSocket.getOutputStream().write((cipher1Point.xCoord + "\n").getBytes());
        clientSocket.getOutputStream().write((cipher1Point.yCoord + "\n").getBytes());
        clientSocket.getOutputStream().write((cipher2Point.xCoord + "\n").getBytes());
        clientSocket.getOutputStream().write((cipher2Point.yCoord + "\n").getBytes());

        System.out.println("================================================");
        System.out.println("ELLIPTICAL PRIME VALUE = " + primeValue);
        System.out.println("ELLIPTICAL A VALUE =  " + ellipticAValue);
        System.out.println("ELLIPTICAL B VALUE =  " + ellipticBValue);
        System.out.println("================================================");
        System.out.println("GENERATOR X COORDINATE = " + generatorXCoord);
        System.out.println("GENERATOR Y COORDINATE = " + generatorYCoord);
        System.out.println("================================================");
        System.out.println("SHARED PRIVATE KEY VALUE = " + sharedPrivateKey);
        System.out.println("RANDOM INTEGER VALUE = " + randomIntegerValue);
        System.out.println("================================================");
        System.out.println("PLAINTEXT MESSAGE X COORDINATE = " + plaintextXCoord);
        System.out.println("PLAINTEXT MESSAGE Y COORDINATE = " + plaintextYCoord);
        System.out.println("================================================");
        System.out.println("CIPHER 1, 2 BEING SENT = { (" +  cipher1Point.xCoord + ", " + cipher1Point.yCoord + "), (" + cipher2Point.xCoord + ", " + cipher2Point.yCoord + ") }");

        clientSocket.close();
        clientInput.close();
    }
}
