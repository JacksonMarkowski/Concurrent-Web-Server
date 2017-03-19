package request;

import http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClientRequest {

    private HttpMethod method;
    private String uri;
    private String version;

    public ClientRequest(InputStream input) {
        parseInputStream(input);
    }

    /**
     * Parses the input from a request.  The input should have an initial
     * request line, and can have optional header lines and content body
     *
     * @param input The stream of data from the request
     */
    private void parseInputStream(InputStream input) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
            parseInitRequest(buffer.readLine());

            String line;
            while (!(line = buffer.readLine()).equals("")) {
                System.out.println(line);
            }
        } catch (IOException e) {

        }
    }

    /**
     * Parses the initial line for the request.  As per HTTP standards,
     * the initial line should include a method name, local path of requested
     * item, and HTTP version.
     *
     * @param line The initial(first) line of the HTTP request
     */
    private void parseInitRequest(String line) {
        //ToDo: test if string is null
        String[] lineSplit = line.split("\\s+");
        //ToDo: check if method,uri,version are all there
        try {
            method = HttpMethod.valueOf(lineSplit[0]);
        } catch (Exception e) {
            //ToDo: throw exception or something
        }
        uri = lineSplit[1];
        version = lineSplit[2];

    }

    private void parseHeaderRequest(String line) {
        //ToDo: handle headers
    }
}
