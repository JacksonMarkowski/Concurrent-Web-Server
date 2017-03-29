package server;

import service.ClientRequest;
import service.ServerResponse;
import service.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Server server;
    private Socket socket;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            ClientRequest request = new ClientRequest(socket.getRemoteSocketAddress());
            request.read(input);
            server.logRequest(request);

            ServerResponse response = new ServerResponse();
            Service service = new Service(server.getServerDir(), request, response);
            service.evalRequest();

            OutputStream output = socket.getOutputStream();
            response.write(output);

            //ToDo: potentially move outside, that way if the server is shut down you can still close the streams and socket
            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {

        }

    }
}
