package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DiscordClientThread implements Runnable{
    private Socket socket;
    private BufferedReader input;

    public DiscordClientThread(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    public void run() {
        try {
            while (true) {
                String message = input.readLine();
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
