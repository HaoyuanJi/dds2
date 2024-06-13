package Client;

import java.io.PrintWriter;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 12345)) {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            String userInput;

            DiscordClientThread dCThread = new DiscordClientThread(socket);
            new Thread(dCThread).start();
            do {
                userInput = scanner.nextLine();
                output.println(userInput);
                if (userInput.equals("exit")) {
                    //reading the input from server
                    break;
                }
            } while (true);
        } catch (Exception e) {
            System.out.println("Error occured in client Main: " + e.getStackTrace());
        }
    }
}
