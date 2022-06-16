package serverMultiClients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClientHandler {
    private final Socket socket;
    private final Server server;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String name;
    private final AuthService authService;
    private List<String> messByName;


    public String getName() {
        return name;
    }

    public ClientHandler(Socket socket, Server server, AuthService authService) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.authService = authService;

            new Thread(() -> {
                try {
                    authorize();
                    readMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start();


        } catch (IOException e) {
            throw new RuntimeException("Error create connect to client", e);
        }
    }

    private void authorize() {
        try {
            while (true) {
                String inputLogPass = in.readUTF(); // 2. хендлер принял сообщение от клиента
                if (inputLogPass.startsWith("/auth")) {
                    String[] tokens = inputLogPass.split("\\s+");
                    String nick = authService.getNickByLoginAndPass(tokens[1], tokens[2]);
                    if (nick != null) {
                        server.subscribe(this);
                        sendMessage("/authok " + nick);
                        this.name = nick;
                        server.broadcast(name + " connected to chat");
                        break;
                    } else {
                        sendMessage("Incorrect login/pass");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage() throws IOException {
        try {
            while (true) {
                String inputStr = in.readUTF();

                if (inputStr.startsWith("/w")) {
                    String[] split = inputStr.split("\\s+");
                    messByName = Arrays.stream(split)
                            .filter((s) -> (!s.equals("/w") && !s.equals(split[1])))
                            .collect(Collectors.toList());

                    String messToClient = messByName.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(" ", "", ""));

                    server.broadcastByName(messToClient, split[1]);
                } else {
                    System.out.println("Received message: " + inputStr);
                    server.broadcast(inputStr + " #");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message); // 3. хендлер отправил сообщение клиенту
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        server.unsubscribe(this);
        server.broadcast(this.name + "left chat");

        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
