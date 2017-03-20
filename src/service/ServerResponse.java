package service;

import http.HttpStatusCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ServerResponse {

    private String statusLine = "HTTP/1.1 400 Server Error";
    private List<String> header = new ArrayList<String>();
    private byte[] msgBody;

    public ServerResponse() {

    }

    public void addHeaderLine(String line) {
        header.add(line);
    }

    public void setStatusLine(String initStatusLine) {
        this.statusLine = initStatusLine;
    }

    public void setMsgBody(byte[] msgBody) {
        this.msgBody = msgBody;
    }

    public void write(OutputStream output) {
        try {
            DataOutputStream stream = new DataOutputStream(output);
            stream.writeBytes(statusLine  + "\r\n");

            for (String line: header) {
                stream.writeBytes(line + "\r\n");
            }
            stream.writeBytes("\r\n");

            if (msgBody != null) {
                stream.write(msgBody);
            }
            stream.writeBytes("\r\n");
            stream.flush();
            //stream.close();
        } catch (IOException e) {
            //ToDO: handle exception
        }

    }

}
