package server;

import request.ClientRequest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class SingleThreadServer extends Server {

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
}
