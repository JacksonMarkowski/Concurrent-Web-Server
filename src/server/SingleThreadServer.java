package server;

import service.ClientRequest;
import service.ServerResponse;
import service.Service;

import java.io.*;
import java.net.Socket;

public class SingleThreadServer extends Server {

    public SingleThreadServer() {

    }

    public void run() {
        createServerSocket();

        while(isServerRunning()){
            Socket clientSocket = acceptClientSocket();
            try {
                InputStream input = clientSocket.getInputStream();
                ClientRequest request = new ClientRequest(input);
                //clientSocket.getRemoteSocketAddress().toString();
                //ToDo: log service
                Service service = new Service(getServerDir(), request, new ServerResponse());
                service.evalRequest();

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
