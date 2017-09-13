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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ImageRequestEncoder implements ProtocolEncoder {

    private final Charset DEFAULT_CHARSET = Charset.forName("utf8");

    @Override
    public void encode(IoSession session, Object message,
            ProtocolEncoderOutput out) throws Exception {
        ImageRequest request = (ImageRequest) message;
        byte[] imageTypeBytes = request.getType().getBytes(DEFAULT_CHARSET);
        byte[] imageBytes = getBytes(request.getImage(), request.getType());
        IoBuffer buffer = IoBuffer.allocate(imageTypeBytes.length
                + imageBytes.length + 12);
        buffer.putInt(request.getId());
        buffer.putInt(imageTypeBytes.length);
        buffer.putInt(imageBytes.length);
        buffer.put(imageTypeBytes);
        buffer.put(imageBytes);
        buffer.flip();
        out.write(buffer);
    }

    private byte[] getBytes(BufferedImage image, String type)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, type, baos);
        return baos.toByteArray();
    }

    @Override
    public void dispose(IoSession session) throws Exception {
        // TODO Auto-generated method stub
    }
}
