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
                request.read(input);
                //clientSocket.getRemoteSocketAddress().toString();
                //ToDo: log service

                ServerResponse response = new ServerResponse();
                Service service = new Service(getServerDir(), request, response);
                service.evalRequest();

                OutputStream output = clientSocket.getOutputStream();
                response.write(output);

                output.close();
                input.close();
                clientSocket.close();
            } catch (IOException e) {
                //ToDo: Handle exception from streams
            }

        }
        System.out.println("Server Stopping");
        closeServerSocket();
        //ToDo: shut down server, close sockets, etc.
    }
}
