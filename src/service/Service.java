package service;

import http.HttpMethod;

import java.io.File;

public class Service {

    private String serverDir;

    private ClientRequest request;
    private ServerResponse response;

    public Service(String serverDir, ClientRequest request, ServerResponse response) {
        this.serverDir = serverDir;
        this.request = request;
        this.response = response;
    }

    public void evalRequest() {
        switch (request.getMethod()) {
            case GET:
                String uri = request.getUri();
                if (uri.equals("/")) {
                    uri = "/index.html";
                }
                File file = new File(serverDir + uri);
                if (file.exists()) {
                    //ToDo: read file
                }
                break;
            case HEAD:
                //ToDo: return headers
                break;
        }
    }
}
