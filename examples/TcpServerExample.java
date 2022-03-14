import java.io.IOException;
import tcp.TcpServer;

public class TcpServerExample {
    private static final int PORT = 8000;
    private static final String RESPONSE = "response";

    public static void main(String[] args) {
        int port = PORT;
        String response = RESPONSE;

        TcpServer server;
        try {
            server = TcpServer.at(port);
        } catch (IOException ioe) {
            System.err.printf("Error creating server: %s%n", ioe);
            return;
        }

        System.out.printf("Server listening at port %s...%n", port);
        server.serve(str -> response);

        try {
            server.close();
        } catch (IOException ioe) {
            System.err.printf("Error closing server: %s%n", ioe);
        }
    }
}
