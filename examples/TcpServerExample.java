import java.io.IOException;
import tcp.TcpServer;

public class TcpServerExample {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.printf("Usage: java %s <port>%n",
                TcpServerExample.class.getName());
            return;
        }

        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.err.printf("Invalid port number: %s%n", args[0]);
            return;
        }

        TcpServer server;
        try {
            server = TcpServer.at(port);
        } catch (IOException ioe) {
            System.err.printf("Error creating server: %s%n", ioe);
            return;
        }

        System.out.printf("Server listening at port %s...%n", port);
        server.serve(request -> request);

        try {
            server.close();
        } catch (IOException ioe) {
            System.err.printf("Error closing server: %s%n", ioe);
        }
    }
}
