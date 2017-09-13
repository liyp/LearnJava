/*
 * Copyright © 2017 liyp (liyp.yunpeng@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.liyp.mina.demo.imageserver;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageResponseEncoder implements ProtocolEncoder {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ImageResponseEncoder.class);
    private final Charset DEFAULT_CHARSET = Charset.forName("utf8");

    @Override
    public void encode(IoSession session, Object message,
            ProtocolEncoderOutput out) throws Exception {
        LOGGER.debug("messgae = {}", message);

        ImageResponse response = (ImageResponse) message;
        byte[] resultBytes = response.getResult().getBytes(DEFAULT_CHARSET);
        IoBuffer buffer = IoBuffer.allocate(resultBytes.length + 8);
        buffer.putInt(response.getId());
        buffer.putInt(resultBytes.length);
        buffer.put(resultBytes);
        buffer.flip();
        out.write(buffer);
    }

    @Override
    public void dispose(IoSession session) throws Exception {
        // TODO Auto-generated method stub
    }
}
