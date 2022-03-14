package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpMessageHandler {
    private final InputStream inputStream;
    private final OutputStream outputStream;

    private TcpMessageHandler(InputStream inputStream,
        OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public static TcpMessageHandler of(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        TcpMessageHandler messageHandler =
            new TcpMessageHandler(inputStream, outputStream);
        return messageHandler;
    }

    public void send(String message) throws IOException {
        byte[] bufSend = message.getBytes(StandardCharsets.UTF_8);
        this.outputStream.write(bufSend);
    }

    public String receive() throws IOException {
        int bytesAvailable = this.inputStream.available();
        byte[] bufReceive = this.inputStream.readNBytes(bytesAvailable);
        String message = new String(bufReceive, StandardCharsets.UTF_8);
        return message;
    }
}
