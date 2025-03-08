package SimpleSocket;
import java.net.*;
import java.util.Scanner;

public class SimpleServer {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(5000);
        Socket serverSocket = server.accept();


        // RECIEVING DATA FROM CLIENT
        Scanner clientInput = new Scanner(serverSocket.getInputStream());
        int recvNum = clientInput.nextInt();
        System.out.println("Number recieved from Client = " + recvNum);


        // SEND DATA TO CLIENT
        Scanner serverInput = new Scanner(System.in);
        System.out.println("Enter the number to send :");
        int sendNum = serverInput.nextInt();

        serverSocket.getOutputStream().write((sendNum + "\n").getBytes());


        server.close();
        serverSocket.close();
        serverInput.close();
        clientInput.close();
    }
}
