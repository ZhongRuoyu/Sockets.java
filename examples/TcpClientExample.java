import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import tcp.TcpClient;

public class TcpClientExample {
    private static final String REMOTE_HOSTNAME = "localhost";
    private static final int REMOTE_PORT = 8000;
    private static final String MESSAGE = "request";

    public static void main(String[] args) {
        String remoteHostname = REMOTE_HOSTNAME;
        int remotePort = REMOTE_PORT;
        String message = MESSAGE;

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
