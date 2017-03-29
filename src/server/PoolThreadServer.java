package server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolThreadServer extends Server {

    private ExecutorService threadPool = Executors.newFixedThreadPool(5);

    public void run() {
        createServerSocket();

        while(isServerRunning()){
            try {
                Socket clientSocket = acceptClientSocket();
                ClientHandler newHandler = new ClientHandler(this, clientSocket);
                threadPool.execute(newHandler);
            } catch (IOException e) {
                //ToDo: Handle exception from streams
            }
        }
        threadPool.shutdown();
    }
}
