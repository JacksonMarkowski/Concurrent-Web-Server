import server.*;

import java.util.Scanner;

public class ServerCtl {

    private boolean stopCtl = false;

    private Thread serverThread;
    private Server server;
    private boolean serverRunning = false;

    public ServerCtl() {}

    private void readUserInput() {
        Scanner scanner = new Scanner(System.in);
        while (!stopCtl) {
            System.out.print("ctl: ");
            try {
                String input = scanner.nextLine();
                evalUserInput(input);
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
    }

    private void evalUserInput(String input) {
        if (input.equals("quit") || input.equals("q")) {
            stopCtl = true;
            attemptServerStop();
        } else if (input.equals("single start")) {
            attemptServerStart(new SingleThreadServer());
        } else if (input.equals("multi start")) {
            attemptServerStart(new MultiThreadServer());
        } else if (input.equals("pool start")) {
            attemptServerStart(new PoolThreadServer());
        } else if (input.equals("h")) {
            System.out.println("Usage: (single/multi/pool) (start/quit)");
        } else if (input.equals("stop")) {
            attemptServerStop();
        } else {
            System.out.println("ctl: type h for help");
        }
    }

    private void attemptServerStart(Server server) {
        if (!serverRunning) {
            serverRunning = true;
            System.out.println("Starting " + server.getClass().getSimpleName());
            this.server = server;
            serverThread = new Thread(this.server);
            serverThread.start();
        } else {
            System.out.println(server.getClass().getSimpleName() + " is currently running. You must stop it before starting a new server");
        }
    }

    private void attemptServerStop() {
        if (serverRunning) {
            server.setIsRunning(false);
            serverRunning = false;
        } else if (!stopCtl){
            System.out.println("No server currently running");
        }
    }

    public static void main(String args[]) {
        ServerCtl ctl = new ServerCtl();
        ctl.readUserInput();
    }
}
