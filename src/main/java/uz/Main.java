package uz;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final int PORT = 4550;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server running on PORT: " + PORT);

            while (true) {
                Socket socket = server.accept();

                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    System.out.println(">> " + line);
                }

                String response = """
                        HTTP/1.1 200 OK
                        Content-Type: text/plain
                        Content-Length: 13
                        
                        Hello, world!
                        """;
                out.write(response.getBytes());
                out.flush();

                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}