package SimpleSocket;
import java.net.*;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) throws Exception {
        Socket clientSocket = new Socket("localhost", 5000);
        

        // SENDING DATA FROM CLIENT TO SERVER

        // Input from terminal of client
        Scanner clientInput = new Scanner(System.in);
        System.out.println("Enter a number to send :");
        int sendNum = clientInput.nextInt();

        // sending it to the server
        clientSocket.getOutputStream().write((sendNum + "\n").getBytes());


        // RECIEVING DATA FROM THE SERVER 
        Scanner serverInput = new Scanner(clientSocket.getInputStream());
        int recvNum = serverInput.nextInt();
        System.out.println("Number recieved = " + recvNum);


        clientSocket.close();
        serverInput.close();
        clientInput.close();
    }
}
