package serverMultiClients;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 8189;
    private final List<ClientHandler> clientHandlers;

    public Server() {
        this.clientHandlers = new ArrayList<>();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started");
            System.out.println("Waiting connected...");

            waitConnectNewClient(serverSocket);

        } catch (IOException e) {
            throw new RuntimeException("Error start server", e);
        }
    }

    private void waitConnectNewClient(ServerSocket serverSocket) {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                ClientHandler clientHandler = new ClientHandler(socket, this);
                clientHandlers.add(clientHandler);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error connect new client", e);
        }
    }

    public void broadcast(String inMsg) {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.writeMessage(inMsg);
        }
    }
}
