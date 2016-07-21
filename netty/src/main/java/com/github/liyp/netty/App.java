/*
 * Copyright © 2016 liyp (liyp.yunpeng@gmail.com)
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
package com.github.liyp.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    static final int PORT = 51234;

    static final short HEADER = (short)0xA1B2;

    public static void main(String[] args) throws Exception {

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ReplayingDecoder() {
                                @Override
                                protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                                    short magicHeader = in.readShort();
                                    logger.debug("Receive magic header: {}.", magicHeader);
                                    if (magicHeader != HEADER) {
                                        logger.error("Receive illegal magic header: {}, close channel: {}.", magicHeader,
                                                ctx.channel().remoteAddress());
                                        ctx.close();
                                    }

                                    short dataLen = in.readShort();
                                    logger.debug("Receive message data length: {}.", dataLen);
                                    if (dataLen < 0) {
                                        logger.error("Data length is negative, close channel: {}.", ctx.channel().remoteAddress());
                                        ctx.close();
                                    }

                                    ByteBuf payload = in.readBytes(dataLen);
                                    String cloudMsg = payload.toString(CharsetUtil.UTF_8);
                                    logger.debug("Receive data: {}.", cloudMsg);
                                    out.add(cloudMsg);
                                }
                            }).addLast(new MessageToByteEncoder<String>() {
                                @Override
                                protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
                                    out.writeBytes(msg.getBytes());
                                }
                            }).addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    logger.info("start receive msg...");
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    logger.info("receive msg: {}", msg);
                                    logger.info("echo msg");
                                    ctx.writeAndFlush(msg);
                                }
                            });
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(PORT).sync();
            logger.info("9999");
            f.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
