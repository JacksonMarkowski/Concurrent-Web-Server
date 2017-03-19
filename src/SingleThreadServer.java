

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class SingleThreadServer implements Runnable {

    private int portNumber = 4000;
    private AtomicBoolean isRunning;

    private ServerSocket serverSocket;

    public SingleThreadServer() {
        isRunning = new AtomicBoolean(true);
    }

    public void run() {
        createServerSocket();

        while(isRunning.get()){
            Socket clientSocket = acceptClientSocket();
            try {
                InputStream input = clientSocket.getInputStream();
                ClientRequest request = new ClientRequest(input);

                OutputStream output = clientSocket.getOutputStream();
                output.write(("HTTP/1.1 200 OK\n\n<html>\n\t<h1>Return</h1></html>").getBytes());
                output.close();
                input.close();

            } catch (IOException e) {

            }
        }
        System.out.println("stop");
        //ToDo: shut down server, close sockets
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning.set(isRunning);
    }

    /**
     * Creates/opens the server socket on the specified port number
     */
    private void createServerSocket() {
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Accepts a client socket/request
     */
    private Socket acceptClientSocket() {
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
