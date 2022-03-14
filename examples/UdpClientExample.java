import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import udp.UdpClient;

public class UdpClientExample {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.printf("Usage: java %s <hostname> <port> <message>%n",
                UdpClientExample.class.getName());
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
