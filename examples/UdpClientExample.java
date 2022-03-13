package examples;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import udp.UdpClient;

public class UdpClientExample {
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

        UdpClient client;
        try {
            client = new UdpClient();
        } catch (SocketException se) {
            System.err.printf("Error creating client: %s%n", se);
            return;
        }

        try {
            String response = client.send(remoteAddress, remotePort, message);
            System.out.printf("Received response from %s:%s:%n", remoteAddress,
                remotePort);
            System.out.println(response);
        } catch (IOException ioe) {
            System.err.printf("Error sending message: %s%n", ioe);
        }

        client.close();
    }
}
