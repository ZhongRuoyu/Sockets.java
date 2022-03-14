package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UdpClient {
    private static final int BUFFER_SIZE = 65535;

    private final DatagramSocket socket;

    public UdpClient() throws SocketException {
        this.socket = new DatagramSocket();
    }

    public String send(InetAddress remoteAddress, int remotePort,
        String message) throws IOException {
        byte[] bufSend = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket request = new DatagramPacket(bufSend, bufSend.length,
            remoteAddress, remotePort);
        this.socket.send(request);

        byte[] bufReceive = new byte[BUFFER_SIZE];
        DatagramPacket response =
            new DatagramPacket(bufReceive, bufReceive.length);
        this.socket.receive(response);

        String responseMessage = new String(response.getData(),
            response.getOffset(), response.getLength(), StandardCharsets.UTF_8);
        return responseMessage;
    }

    public void close() {
        this.socket.close();
    }

    @Override
    public String toString() {
        InetAddress address = this.socket.getLocalAddress();
        int port = this.socket.getLocalPort();
        return String.format("UDP Client at %s:%s", address, port);
    }
}
