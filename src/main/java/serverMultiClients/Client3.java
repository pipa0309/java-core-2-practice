package serverMultiClients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client3 {
    private static final int PORT = 8189;
    private static final String HOST = "127.0.0.1";
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private Thread thread;
    private static boolean FLAG_AUTH = false;
    private AuthService authService;

    public static void main(String[] args) {
        Client3 client = new Client3();
        client.startClient();
    }

    private void startClient() {
        try {
            socket = new Socket(HOST, PORT);
            System.out.println("Client3 started");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            thread = new Thread(() -> {
                try {
                    while (true) {
                        String inMessFromServer = in.readUTF();
                        System.out.println(inMessFromServer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();

            outMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void outMessage() throws IOException {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                if (sc.hasNext()) {
                    String outMessToServer = sc.nextLine();
                    out.writeUTF(outMessToServer);
                }
            }
        }
    }
}
