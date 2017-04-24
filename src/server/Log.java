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
        Node node = new Node();
        node.str = str;
        enq(node);
    }

    private void enq(Node node) {
        while (true) {
            Node last = tail.get();
            Node next = last.next.get();
            if (last == tail.get()) {
                if (next == null) {
                    if (last.next.compareAndSet(null, node)) {
                        tail.compareAndSet(last, node);

                        return;
                    }
                } else {
                    tail.compareAndSet(last, next);
                }
            }
        }
    }

    public void deq() throws Exception {
        while (true) {
            Node first = head.get();
            Node last = tail.get();
            Node next = first.next.get();
            if (first == head.get()) {
                if (first == last) {
                    if (next == null) {
                        throw new Exception();
                    }
                    tail.compareAndSet(last, next);
                } else {
                    String str = next.str;
                    if (head.compareAndSet(first, next)) {
                        logger.info(str);
                    }
                }
            }
        }
    }
}
