package server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadServer extends Server {

    List handlerThreads = new ArrayList();

    public void run() {
        createServerSocket();

        while(isServerRunning()){
            try {
                Socket clientSocket = acceptClientSocket();
                ClientHandler newHandler = new ClientHandler(this, clientSocket);
                //ToDo: add thread to list for easier management
                Thread thread = new Thread(newHandler);
                thread.start();
            } catch (IOException e) {
                //ToDo: Handle exception from streams
            }

        }
    }
}
