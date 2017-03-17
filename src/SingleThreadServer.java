

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
                InputStream  input  = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();
                output.write(("HTTP/1.1 200 OK\n\n<html><h1>Return</h1></html>").getBytes());
                output.close();
                input.close();

            } catch (IOException e) {

            }
        }
        //ToDo: shut down server, close sockets
    }

    private void createServerSocket() {
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Socket acceptClientSocket() {
        Socket clientSocket;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clientSocket;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning.set(isRunning);
    }
}
