package tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

public class TcpServer {
    private static class TcpServerThread extends Thread {
        private final Socket socket;
        private final Function<String, String> responder;

        public TcpServerThread(Socket socket,
            Function<String, String> responder) {
            this.socket = socket;
            this.responder = responder;
        }

        @Override
        public void run() {
            try {
                TcpMessageHandler messageHandler =
                    TcpMessageHandler.of(this.socket);
                String requestMessage = messageHandler.receive();
                String responseMessage = this.responder.apply(requestMessage);
                messageHandler.send(responseMessage);
                this.socket.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private final ServerSocket serverSocket;

    private TcpServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static TcpServer at(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        return new TcpServer(serverSocket);
    }

    public void serve(Function<String, String> responder) {
        while (true) {
            try {
                Socket socket = this.serverSocket.accept();
                new TcpServerThread(socket, responder).start();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public void close() throws IOException {
        this.serverSocket.close();
    }

    @Override
    public String toString() {
        InetAddress address = this.serverSocket.getInetAddress();
        int port = this.serverSocket.getLocalPort();
        return String.format("TCP Server at %s:%s", address, port);
    }
}
