package serverMultiClients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    private static final int PORT = 8189;
    private static final String HOST = "127.0.0.1";
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private Thread thread;

    public static void main(String[] args) {
        Client2 client = new Client2();
        client.startClient();
    }

    private void startClient() {
        try {
            openConnect();

            outMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openConnect() {
        try {
            socket = new Socket(HOST, PORT);
            System.out.println("Client2 started");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            inMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inMessage() {
        thread = new Thread(() -> {
            try {
                while (true) {
                    String inMessFromServer = in.readUTF();
                    System.out.println("Message from server: " + inMessFromServer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void outMessage() throws IOException {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                if (sc.hasNext()) {
                    String outMessToServer = sc.nextLine();
                    if ("/end".equalsIgnoreCase(outMessToServer)) {
                        System.out.println("Client2 stopped");
                        break;
                    }
                    out.writeUTF("Client1 = " + "\"" + outMessToServer + "\"");
                }
            }
        } finally {
            closeClient();
        }
    }

    private void closeClient() throws IOException {
        if (thread.isInterrupted() && thread.isAlive()) {
            thread.interrupt();
        }

        if (out != null) {
            out.close();
        }

        if (in != null) {
            in.close();
        }

        if (socket != null) {
            socket.close();
        }
    }
}
