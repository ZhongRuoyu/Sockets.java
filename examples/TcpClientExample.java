import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import tcp.TcpClient;

public class TcpClientExample {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.printf("Usage: java %s <hostname> <port> <message>%n",
                TcpClientExample.class.getName());
            return;
        }

        String remoteHostname = args[0];
        int remotePort;
        try {
            remotePort = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.err.printf("Invalid port number: %s%n", args[1]);
            return;
        }
        String message = args[2];

        InetAddress remoteAddress;
        try {
            remoteAddress = InetAddress.getByName(remoteHostname);
        } catch (UnknownHostException uhe) {
            System.err.printf("Unknown host: %s%n", remoteHostname);
            return;
        }

        TcpClient client;
        try {
            client = TcpClient.of(remoteAddress, remotePort);
        } catch (IOException ioe) {
            System.err.printf("Error creating client: %s%n", ioe);
            return;
        }

        try {
            String response = client.send(message);
            System.out.printf("Received response from %s:%s:%n", remoteAddress,
                remotePort);
            System.out.println(response);
        } catch (IOException ioe) {
            System.err.printf("Error sending message: %s%n", ioe);
        }

        try {
            client.close();
        } catch (IOException e) {
            System.err.printf("Error closing client: %s%n", e);
        }
    }
}
