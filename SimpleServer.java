import java.net.*;
import java.util.Scanner;

public class SimpleServer {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(5000);
        Socket socket = server.accept();

        Scanner input = new Scanner(socket.getInputStream());
        int number = input.nextInt();  // Receive number from client
        System.out.println("Processed number: " + (number * 5)); // Multiply by 5 and print

        socket.close();
        server.close();
    }
}
