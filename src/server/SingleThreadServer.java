package server;

import service.ClientRequest;
import service.ServerResponse;
import service.Service;

import java.io.*;
import java.net.Socket;

public class SingleThreadServer extends Server {

    public SingleThreadServer() {}

    public void run() {
        createServerSocket();

        while(isServerRunning()){
            try {
                Socket clientSocket = acceptClientSocket();
                InputStream input = clientSocket.getInputStream();
                ClientRequest request = new ClientRequest(clientSocket.getRemoteSocketAddress());
                request.read(input);
                logRequest(request);

                ServerResponse response = new ServerResponse();
                Service service = new Service(getServerDir(), request, response);
                service.evalRequest();

                OutputStream output = clientSocket.getOutputStream();
                response.write(output);

                //ToDo: potentially move outside, that way if the server is shut down you can still close the streams and socket
                output.close();
                input.close();
                clientSocket.close();
            } catch (IOException e) {
                //ToDo: Handle exception from streams
            }

        }
    }
}
