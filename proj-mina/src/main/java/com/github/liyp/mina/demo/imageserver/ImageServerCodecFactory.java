package com.github.liyp.mina.demo.imageserver;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageServerCodecFactory implements ProtocolCodecFactory {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ImageServerCodecFactory.class);

    private ImageResponseEncoder encoder = new ImageResponseEncoder();
    private ImageRequestDecoder decoder = new ImageRequestDecoder();

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }
}
