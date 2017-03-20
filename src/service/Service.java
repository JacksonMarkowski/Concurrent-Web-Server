package service;

import http.HttpStatusCode;
import http.ContentType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        generateDateHeader();
        switch (request.getMethod()) {
            case GET:
                String uri = updateUri(request.getUri());
                File file = new File(serverDir + uri);
                if (file.exists()) {
                    try {
                        byte[] data = Files.readAllBytes(file.toPath());
                        response.setMsgBody(data);
                        generateStatusHeader(HttpStatusCode.SC_200);
                        generateContentHeader(uri);
                    } catch (IOException e) {
                        generateStatusHeader(HttpStatusCode.SC_500);
                    }
                } else {
                    generateStatusHeader(HttpStatusCode.SC_404);
                }
                break;

            case HEAD:
                uri = updateUri(request.getUri());
                file = new File(serverDir + uri);
                if (file.exists()) {
                    generateStatusHeader(HttpStatusCode.SC_200);
                    generateContentHeader(uri);
                } else {
                    generateStatusHeader(HttpStatusCode.SC_404);
                }
                break;

            case POST:
                generateStatusHeader(HttpStatusCode.SC_501);
                break;

            case PUT:
                generateStatusHeader(HttpStatusCode.SC_501);
                break;

            case DELETE:
                generateStatusHeader(HttpStatusCode.SC_501);
                break;

            case CONNECT:
                generateStatusHeader(HttpStatusCode.SC_501);
                break;

            case OPTIONS:
                generateStatusHeader(HttpStatusCode.SC_501);
                break;

            case TRACE:
                generateStatusHeader(HttpStatusCode.SC_501);
                break;

            case PATCH:
                generateStatusHeader(HttpStatusCode.SC_501);
                break;
        }
    }

    public String updateUri(String uri) {

        //Add any other uri requests that need to be changed to correspond to their correct server file
        if (uri.equals("/")) {
            return "/index.html";
        } else {
            return uri;
        }
    }

    /**
     * Creates the initial status line for the HTTP response.
     * For example: "HTTP/1.1 200 OK"
     *
     * @param sc The HTTP status code that will be in the status line.
     */
    public void generateStatusHeader(HttpStatusCode sc) {
        //ToDo: version number may not be what the request was
        response.setStatusLine(request.getVersion() + " " + sc);
    }

    /**
     * Creates the header line for the date based on HTTP standards.
     * For example: "Date: Mon, 20 Mar 2017 14:41:08 MDT
     */
    public void generateDateHeader() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        String dateLine = "Date: " + dateFormat.format(new Date());
        response.addHeaderLine(dateLine);
    }

    /**
     * Creates the header line for the content type based on HTTP standards.
     * For example: "Content-Type: text/html
     *
     * @param uri The requested uri that will correspond to a file
     */
    public void generateContentHeader(String uri) {
        //ToDO: add other files
        String fileType = uri.substring(uri.indexOf(".") + 1);
        ContentType type = ContentType.valueOf(fileType.toLowerCase());
        try {
            String line = "Content-Type: " + type.getMIMEType();
            response.addHeaderLine(line);
        } catch (Exception e) {
            //ToDo: handle error, cant find content type for uri
        }
    }
}
