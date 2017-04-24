package server;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    private static class Node {
        private AtomicReference<Node> next = new AtomicReference<>();
        private String str;
    }

    private Node initNode = new Node();
    private AtomicReference<Node> head = new AtomicReference<Node>(initNode);
    private AtomicReference<Node> tail = new AtomicReference<Node>(initNode);

    private Logger logger = Logger.getLogger("Log");
    private FileHandler fileHandler;

    public Log() {
        createNewLog();
    }

    private void createNewLog() {
        try {
            fileHandler = new FileHandler("./ServerLog.log");
            logger.addHandler(fileHandler);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void logString(String str) {
        //ToDO: implement logging queue
    }
}
