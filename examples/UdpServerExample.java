import java.net.SocketException;
import udp.UdpServer;

public class UdpServerExample {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.printf("Usage: java %s <port>%n",
                UdpServerExample.class.getName());
            return;
        }

        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.err.printf("Invalid port number: %s%n", args[0]);
            return;
        }

        UdpServer server;
        try {
            server = UdpServer.at(port);
        } catch (SocketException se) {
            System.err.printf("Error creating server: %s%n", se);
            return;
        }

        System.out.printf("Server listening at port %s...%n", port);
        server.serve(request -> request);

        server.close();
    }
}
