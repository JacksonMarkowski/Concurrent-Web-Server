package server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Server implements Runnable {

    private String serverDir = "./def_dir";
    private int portNumber = 4000;
    private AtomicBoolean isRunning = new AtomicBoolean(true);

    private ServerSocket serverSocket;

    public Server() {
    }

    public abstract void run();

    public void setIsRunning(boolean isRunning) {
        this.isRunning.set(isRunning);
    }

    public boolean isServerRunning() {
        return isRunning.get();
    }

    public String getServerDir() {
        return serverDir;
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
     * Accepts a client socket/service
     */
    protected Socket acceptClientSocket() {
        Socket clientSocket;
        try {
            //ToDo: blocks on .accept() until a client makes a service, needs to unblock if the server is stopped
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clientSocket;
    }

    //ToDo: force down methods that ctl can call to force server to close ports right away

}
