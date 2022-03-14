package tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {
    private final Socket socket;

    private TcpClient(Socket socket) {
        this.socket = socket;
    }

    public static TcpClient of(InetAddress remoteAddress, int remotePort)
        throws IOException {
        Socket socket = new Socket(remoteAddress, remotePort);
        return new TcpClient(socket);
    }

    public String send(String message) throws IOException {
        TcpMessageHandler messageHandler = TcpMessageHandler.of(this.socket);
        messageHandler.send(message);
        String responseMessage = messageHandler.receive();
        return responseMessage;
    }

    public void close() throws IOException {
        this.socket.close();
    }

    @Override
    public String toString() {
        InetAddress address = this.socket.getLocalAddress();
        int port = this.socket.getLocalPort();
        return String.format("TCP Client at %s:%s", address, port);
    }
}
