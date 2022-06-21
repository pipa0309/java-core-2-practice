package serverMultiClients;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 8189;
    private final List<ClientHandler> clients;
    private AuthService authService;

    public Server() {
        this.clients = new ArrayList<>();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started");
            authService = new MyAuthService();
            authService.start();
            System.out.println("Waiting connected...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ClientHandler(socket, this, authService);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error start server", e);
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public synchronized void broadcast(String mess) {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(mess);
        }
    }

    public synchronized void broadcastByName(String mess, String name) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getName().equals(name)) {
                clientHandler.sendMessage(mess);
            }
        }
    }

    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}
