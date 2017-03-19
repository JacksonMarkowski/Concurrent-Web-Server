import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClientRequest {

    private HttpMethod method;

    public ClientRequest(InputStream input) {
        readInputStream(input);
    }

    private void readInputStream(InputStream input) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
            String line;
            while (!(line = buffer.readLine()).equals("")) {
                System.out.println(line);
            }
        } catch (IOException e) {

        }
    }
}
