package com.github.liyp.mina.demo.imageserver;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ImageRequestDecoder extends CumulativeProtocolDecoder {
    private static final String DECODER_STATE_KEY = ImageRequestDecoder.class
            .getName() + ".STATE";
    private final Charset DEFAULT_CHARSET = Charset.forName("utf8");

    private static class DecoderState {
        private int id = 0;
        private int typeLen = 0;
        private int imageLen = 0;
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in,
            ProtocolDecoderOutput out) throws Exception {
        DecoderState decoderState = (DecoderState) session
                .getAttribute(DECODER_STATE_KEY);
        if (decoderState == null) {
            decoderState = new DecoderState();
            session.setAttribute(DECODER_STATE_KEY, decoderState);
        }
        if (decoderState.id == 0) {
            if (in.remaining() >= 12) {
                decoderState.id = in.getInt();
                decoderState.typeLen = in.getInt();
                decoderState.imageLen = in.getInt();
            } else {
                return false;
            }
        }

        if (in.remaining() >= (decoderState.typeLen + decoderState.imageLen)) {
            byte[] typeBytes = new byte[decoderState.typeLen];
            in.get(typeBytes);
            String type = new String(typeBytes, DEFAULT_CHARSET);

            byte[] bytes = new byte[decoderState.imageLen];
            in.get(bytes);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(bais);

            ImageRequest request = new ImageRequest(decoderState.id, type,
                    image);
            out.write(request);
            decoderState.id = 0;
            return true;
        } else {
            return false;
        }
    }

}
