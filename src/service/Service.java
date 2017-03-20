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

    /**
     * Evaluates a ClientRequest and generates the ServerResponse for it
     */
    public void evalRequest() {
        switch (request.getMethod()) {
            case GET:
                String uri = request.getUri();
                if (uri.equals("/")) {
                    uri = "/index.html";
                }
                File file = new File(serverDir + uri);
                if (file.exists()) {
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
            case POST:
                break;
            case PUT:
                break;
            case DELETE:
                break;
            case CONNECT:
                break;
            case OPTIONS:
                break;
            case TRACE:
                break;
            case PATCH:
                break;
        }
    }

    /**
     * Creates the initial status line for the HTTP response.
     * For example: "HTTP/1.1 200 OK"
     *
     * @param sc The HTTP status code that will be in the status line.
     */
    public void generateStatusLine(HttpStatusCode sc) {
        response.setStatusLine(request.getVersion() + " " + sc);
    }

    /**
     * Creates the header line for the content type based on HTTP standards.
     * For example: "Content-Type: text/html
     *
     * @param uri The requested uri that will correspond to a file
     */
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
