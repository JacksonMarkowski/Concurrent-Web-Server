package server;

import service.ClientRequest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public abstract class Server implements Runnable {

    private String serverDir = "./server_dir";
    private int portNumber = 4000;
    private AtomicBoolean isRunning = new AtomicBoolean(true);

    private ServerSocket serverSocket;

    private Logger logger = Logger.getLogger("Log");
    FileHandler fileHandler;

    public Server() {
        createNewLog();
    }

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
        try {
            serverSocket.close();
        } catch (IOException e) {

        }
        System.out.println("Closed server socket");
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

    private void createNewLog() {
        try {
            fileHandler = new FileHandler("./ServerLog.log");
            logger.addHandler(fileHandler);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logRequest(ClientRequest request) {
        logger.info(request.toString());
    }

    //ToDo: force down methods that ctl can call to force server to close ports right away

}
