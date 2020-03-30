package me.geek.tom.testgame.server.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServerInboundThread extends Thread {

    private ServerSocket serverSocket;
    private static Logger LOGGER = LogManager.getLogManager().getLogger("Inbound Thread");

    public ServerInboundThread() {
        super();
        this.setName("Server incoming thread");
        this.setDaemon(true);
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(32323);
            try {
                while (true) {
                    Socket s = serverSocket.accept();
                    s.getOutputStream().write("Hello!".getBytes());
                    new ServerClientConnectionThread(s).start();
                }
            } finally {
                serverSocket.close();
            }
        } catch (IOException e) {
            LOGGER.severe("Exception on server thread:");
            e.printStackTrace();
        }
    }

    public static class ServerClientConnectionThread extends Thread {

        private Socket sock;
        private InputStream in;
        private OutputStream out;

        public ServerClientConnectionThread(Socket s) {
            this.sock = s;
            setName("Client incoming thread");
            setDaemon(true);
        }

        @Override
        public void run() {
            try {
                this.in = sock.getInputStream();
                this.out = sock.getOutputStream();

                byte[] data = new byte[in.available()];
                int read = this.in.read(data);

                ByteBuffer buf = ByteBuffer.wrap(data);
                char id = buf.getChar();
                if (id == 'l') {
                    out.write("ok".getBytes());
                }
                in.read();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    sock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
