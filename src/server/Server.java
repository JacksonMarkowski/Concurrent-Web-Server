package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Server implements Runnable {

    private int portNumber = 4000;
    private AtomicBoolean isRunning;

    private ServerSocket serverSocket;

    public Server() {
        isRunning = new AtomicBoolean(true);
    }

    public abstract void run();

    public void setIsRunning(boolean isRunning) {
        this.isRunning.set(isRunning);
    }

    /**
     * Creates/opens the server socket on the specified port number
     */
    protected void createServerSocket() {
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Accepts a client socket/request
     */
    protected Socket acceptClientSocket() {
        Socket clientSocket;
        try {
            //ToDo: blocks on .accept() until a client makes a request, needs to unblock if the server is stopped
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clientSocket;
    }

}
