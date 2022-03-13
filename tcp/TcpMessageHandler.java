package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TcpMessageHandler {
    private static final int INITIAL_BUFFER_SIZE = 1024;
    private static final int START_OF_TEXT = 2;
    private static final int END_OF_TEXT = 3;

    private final InputStream inputStream;
    private final OutputStream outputStream;

    private TcpMessageHandler(InputStream inputStream,
        OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public static TcpMessageHandler of(Socket socket) throws IOException {
        return new TcpMessageHandler(socket.getInputStream(),
            socket.getOutputStream());
    }

    public void send(String message) throws IOException {
        byte[] bufSend = message.getBytes(StandardCharsets.UTF_8);
        this.outputStream.write(START_OF_TEXT);
        this.outputStream.write(bufSend);
        this.outputStream.write(END_OF_TEXT);
    }

    public String receive() throws IOException {
        byte[] bufReceive = new byte[INITIAL_BUFFER_SIZE];
        int bytesRead = 0;
        while (this.inputStream.read() != START_OF_TEXT) {
            continue;
        }
        while (true) {
            int nextByte = this.inputStream.read();
            if (nextByte == -1 || nextByte == END_OF_TEXT) {
                break;
            }
            bufReceive[bytesRead++] = (byte) nextByte;
            if (bytesRead == bufReceive.length) {
                bufReceive = Arrays.copyOf(bufReceive, bufReceive.length * 2);
            }
        }
        String message =
            new String(bufReceive, 0, bytesRead, StandardCharsets.UTF_8);
        return message;
    }
}
