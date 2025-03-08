import java.net.*;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();  // Take user input

        socket.getOutputStream().write((number + "\n").getBytes()); // Send number to server

        socket.close();
    }
}
