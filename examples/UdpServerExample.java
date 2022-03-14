import java.net.SocketException;
import udp.UdpServer;

public class UdpServerExample {
    private static final int PORT = 8000;
    private static final String RESPONSE = "response";

    public static void main(String[] args) {
        int port = PORT;
        String response = RESPONSE;

        UdpServer server;
        try {
            server = UdpServer.at(port);
        } catch (SocketException se) {
            System.err.printf("Error creating server: %s%n", se);
            return;
        }

        System.out.printf("Server listening at port %s...%n", port);
        server.serve(str -> response);

        server.close();
    }
}
