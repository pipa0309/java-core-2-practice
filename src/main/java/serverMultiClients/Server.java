package serverMultiClients;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private static final int PORT = 8189;
    private final Set<ClientHandler> clients;
    private AuthenticateService authService;

    public Server() {
        this.clients = new HashSet<>();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started");
            authService = new MyAuthService();
            System.out.println("Waiting connected...");

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                authService.start();
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

//    private boolean checkSubscribe(Set<ClientHandler> clients) {
//        if (clients.)
//    }
}