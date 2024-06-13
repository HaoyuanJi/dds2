package Server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DiscordServerThread extends Thread {
    private Socket socket;
    private ArrayList<DiscordServerThread> threadLists = new ArrayList<>();
    private int index;
    private PrintWriter output;

    public DiscordServerThread(Socket socket, ArrayList<DiscordServerThread> threadLists, int index) {
        this.socket = socket;
        this.threadLists = threadLists;
        this.index = index;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            while(true) {
                String inputLine = in.readLine();
                if (inputLine == null || inputLine == "exit") {
                    socket.close();
                    return;
                }

                String message = String.format("Client #%d: %s",index, inputLine);
                broadcastToAllOthers(message);
                System.out.println(message);
            }
        } catch (Exception e) {
            System.out.println("Error occurred in server thread: " + e.getStackTrace());
        }
    }

    private void broadcastToAllOthers(String message) {
        for (DiscordServerThread thread : threadLists) {
            if (thread.index != this.index) {
                System.out.println(thread.index);
                thread.output.println(message);
            }
        }
    }
}
