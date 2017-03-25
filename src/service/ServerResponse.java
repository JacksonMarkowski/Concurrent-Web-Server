package service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ServerResponse {

    private String statusLine = "HTTP/1.1 400 Bad Request";
    private List<String> header = new ArrayList<String>();
    private byte[] msgBody;

    public ServerResponse() {}

    public void setStatusLine(String initStatusLine) {
        this.statusLine = initStatusLine;
    }

    public void addHeaderLine(String line) {
        header.add(line);
    }

    public void setMsgBody(byte[] msgBody) {
        this.msgBody = msgBody;
    }

    public void write(OutputStream output) {
        try {
            DataOutputStream stream = new DataOutputStream(output);
            stream.writeBytes(statusLine  + "\r\n");

            //Writes all the lines in the header
            for (String line: header) {
                stream.writeBytes(line + "\r\n");
            }

            //HTTP requires a blank line after the headers
            stream.writeBytes("\r\n");
            //Writes the message body
            if (msgBody != null) {
                //ToDo: thread blocks and waits if client buffer is full, need to cancel at some point
                stream.write(msgBody);
            }

            //One final new line at the end of the output
            stream.writeBytes("\r\n");
            stream.flush();
            //stream.close();
        } catch (IOException e) {
            //ToDO: handle exception
        }

    }

}
