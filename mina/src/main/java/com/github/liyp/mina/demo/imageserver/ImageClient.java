package com.github.liyp.mina.demo.imageserver;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 */
public class ImageClient {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ImageClient.class);

    public static final int CONNECT_TIMEOUT = 3000;
    private String ip;
    private int port;
    private SocketConnector connector;
    private IoSession session;

    public ImageClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new ImageClientCodecFactory()));
        connector.setHandler(new ImageClientHandler());
        connector.getSessionConfig().setTcpNoDelay(false);
    }

    public void connect() {
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress(
                ip, port));
        connectFuture.awaitUninterruptibly(CONNECT_TIMEOUT);
        session = connectFuture.getSession();
    }

    public void disconnect() {
        if (session != null) {
            session.close(true).awaitUninterruptibly(CONNECT_TIMEOUT);
            session = null;
        }
    }

    public void shutdown() {
        connector.dispose();
    }

    public void sendImage(String path) throws IOException {
        ImageRequest request = new ImageRequest();
        request.setId(Long.valueOf(System.currentTimeMillis() / 1000 % 10000)
                .intValue());
        request.setType(path.substring(path.lastIndexOf('.') + 1, path.length()));
        BufferedImage image = ImageIO.read(new FileInputStream(path));
        request.setImage(image);
        session.write(request);
    }

    class ImageClientHandler extends IoHandlerAdapter {

        @Override
        public void messageReceived(IoSession session, Object message)
                throws Exception {
            LOGGER.debug("message = {}", message);
            ImageResponse response = (ImageResponse) message;
            System.out.println("### respone: id=" + response.getId()
                    + " ,result=" + response.getResult());
        }

        @Override
        public void messageSent(IoSession session, Object message)
                throws Exception {
            LOGGER.debug("message = {}", message);
        }
    }

    public static void main(String[] args) throws IOException {
        ImageClient client = new ImageClient("127.0.0.1", 9222);
        client.connect();
        client.sendImage("test.png");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.disconnect();
        client.shutdown();
    }
}
