package server;

import service.ClientRequest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Server implements Runnable {

    private String serverDir = "./server_dir";
    private int portNumber = 4000;
    private AtomicBoolean isRunning = new AtomicBoolean(true);

    private ServerSocket serverSocket;

    private Log log = new Log();

    public Server() {}

    public abstract void run();

    public void setIsRunning(boolean isRunning) {
        if (this.isRunning.get() && !isRunning) {
            closeServerSocket();
        }
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

    protected  void closeServerSocket() {
        System.out.println("Closing server socket");
        try {
            serverSocket.close();
        } catch (IOException e) {

        }
    }

    /**
     * Accepts a client socket/service
     */
    protected Socket acceptClientSocket() throws IOException{
        Socket clientSocket;
        try {
            //ToDo: blocks on .accept() until a client makes a service, needs to unblock if the server is stopped
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw e;
        }
        return clientSocket;
    }

    public void logRequest(ClientRequest request) {
        log.logString(request.toString());
    }

    //ToDo: force down methods that ctl can call to force server to close ports right away

}
