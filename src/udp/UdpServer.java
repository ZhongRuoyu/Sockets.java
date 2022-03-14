package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public class UdpServer {
    private static class UdpServerThread extends Thread {
        private final DatagramSocket socket;
        private final DatagramPacket request;
        private final Function<String, String> responder;

        public UdpServerThread(DatagramSocket socket, DatagramPacket request,
            Function<String, String> responder) {
            this.socket = socket;
            this.request = request;
            this.responder = responder;
        }

        @Override
        public void run() {
            try {
                String requestMessage =
                    new String(this.request.getData(), this.request.getOffset(),
                        this.request.getLength(), StandardCharsets.UTF_8);
                String responseMessage = this.responder.apply(requestMessage);

                byte[] bufSend =
                    responseMessage.getBytes(StandardCharsets.UTF_8);
                InetAddress requestAddress = this.request.getAddress();
                int requestPort = this.request.getPort();
                DatagramPacket response = new DatagramPacket(bufSend,
                    bufSend.length, requestAddress, requestPort);
                this.socket.send(response);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private static final int BUFFER_SIZE = 65535;

    private final DatagramSocket socket;

    private UdpServer(DatagramSocket socket) {
        this.socket = socket;
    }

    public static UdpServer at(int port) throws SocketException {
        DatagramSocket socket = new DatagramSocket(port);
        return new UdpServer(socket);
    }

    public void serve(Function<String, String> responder) {
        while (true) {
            try {
                byte[] bufReceive = new byte[BUFFER_SIZE];
                DatagramPacket request =
                    new DatagramPacket(bufReceive, bufReceive.length);
                this.socket.receive(request);
                new UdpServerThread(this.socket, request, responder).start();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public void close() {
        this.socket.close();
    }

    @Override
    public String toString() {
        InetAddress address = this.socket.getLocalAddress();
        int port = this.socket.getLocalPort();
        return String.format("UDP Server at %s:%s", address, port);
    }
}
