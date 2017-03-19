package service;

import java.util.ArrayList;
import java.util.List;

public class ServerResponse {

    private List<String> header = new ArrayList<String>();

    public ServerResponse() {

    }

    public void addHeaderLine(String line) {
        header.add(line);
    }

}
