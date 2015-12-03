package com.github.liyp.mina.demo.imageserver;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 *
 */
public class ImageServer {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ImageServer.class);

    private IoAcceptor acceptor;

    public ImageServer(int port, String path) throws IOException,
            InterruptedException {
        acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new ImageServerCodecFactory()));
        acceptor.setHandler(new ImageServerHandler(path));
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        acceptor.bind(new InetSocketAddress(port));
    }

    class ImageServerHandler extends IoHandlerAdapter {

        private String path;

        public ImageServerHandler(String path) {
            this.path = path;
        }

        @Override
        public void messageReceived(IoSession session, Object message)
                throws Exception {
            LOGGER.debug("message = {}", message);
            ImageRequest request = (ImageRequest) message;

            OutputStream output = new BufferedOutputStream(
                    new FileOutputStream(path + request.getId() + "."
                            + request.getType()));
            ImageIO.write(request.getImage(), request.getType(), output);

            System.out.println("### request: id=" + request.getId());
            ImageResponse response = new ImageResponse();
            response.setId(request.getId());
            response.setResult("success");
            session.write(response);
        }

        @Override
        public void messageSent(IoSession session, Object message)
                throws Exception {
            LOGGER.debug("message = {}", message);
            // TODO Auto-generated method stub
        }
    }

    public static void main(String[] args) throws IOException,
            InterruptedException {
        new ImageServer(9222, "_image/image-");
    }
}
