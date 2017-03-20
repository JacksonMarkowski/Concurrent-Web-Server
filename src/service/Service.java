package service;

import http.HttpStatusCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
                    System.out.println(uri);
                    generateStatusLine(HttpStatusCode.SC_200);
                    try {
                        byte[] data = Files.readAllBytes(file.toPath());
                        response.setMsgBody(data);
                        generateContentType(uri);
                    } catch (IOException e) {
                        //ToDO: handle exception from reading file
                    }
                } else {
                    generateStatusLine(HttpStatusCode.SC_404);
                }
                break;
            case HEAD:
                //ToDo: return headers
                break;
        }
    }

    public void generateStatusLine(HttpStatusCode sc) {
        response.setStatusLine(request.getVersion() + " " + sc);
    }

    public void generateContentType(String uri) {
        //ToDO: add other files
        String fileType = uri.substring(uri.indexOf(".") + 1);
        if (fileType.equals("html")) {
            response.addHeaderLine("Content-Type: text/html");
        } else if (fileType.equals("ico")) {
            response.addHeaderLine("Content-Type: image/gif");
        }
    }
}
