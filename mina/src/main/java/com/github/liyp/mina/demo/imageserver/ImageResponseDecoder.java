package com.github.liyp.mina.demo.imageserver;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ImageResponseDecoder extends CumulativeProtocolDecoder {
    private static final String DECODER_STATE_KEY = ImageResponseDecoder.class
            .getName() + ".STATE";
    private final Charset DEFAULT_CHARSET = Charset.forName("utf8");

    private static class DecoderState {
        private int id = 0;
        private int resultLen = 0;
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
            if (in.remaining() >= 8) {
                decoderState.id = in.getInt();
                decoderState.resultLen = in.getInt();
            } else {
                return false;
            }
        }

        if (in.remaining() >= decoderState.resultLen) {
            byte[] resultBytes = new byte[decoderState.resultLen];
            in.get(resultBytes);
            String result = new String(resultBytes, DEFAULT_CHARSET);
            ImageResponse response = new ImageResponse(decoderState.id, result);
            out.write(response);
            decoderState.id = 0;
            return true;
        } else {
            return false;
        }
    }

}
