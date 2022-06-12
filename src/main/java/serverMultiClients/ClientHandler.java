package serverMultiClients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private final Socket socket;
    private final Server server;
    private final DataInputStream in;
    private final DataOutputStream out;


    public ClientHandler(Socket socket, Server server) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    readMessage();
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Error create connect to client", e);
        }
    }

    public void readMessage() {
        try {
            while (true) {
                String inMsg = in.readUTF();
                System.out.println("Received message: " + inMsg);
                server.broadcast(inMsg);
                if ("/end".equalsIgnoreCase(inMsg)) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error read message by client", e);
        }
    }

    public void writeMessage(String outMsg) {
        try {
            out.writeUTF(outMsg);
        } catch (IOException e) {
            throw new RuntimeException("Error write message by client", e);
        }
    }

    private void closeConnection() {
        writeMessage("/end");

        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error disconnect", e);
        }

        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error disconnect", e);
        }

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error disconnect", e);
        }
    }
}