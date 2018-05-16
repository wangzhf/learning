package com.wangzhf.netty.http;

import com.wangzhf.util.CommonUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 文件服务器，提供HTTP协议对外服务，当客户端通过浏览器访问文件服务器时，对访问路径进行检查，
 * 检查失败时返回HTTP403错误，该页无法访问；如果通过，以链接的方式开发当前文件目录，每个目录
 * 或者文件都是个超链接，可以递归访问。如果是目录，可以继续递归访问他下面的子目录或者文件，如果
 * 是文件且可读，则可以在浏览器端直接打开，或者通过【目标另存为】下载该文件。
 */
public class HttpFileServer {

    private static final String DEFAULT_URL = "/netty/src/main/java/com/wangzhf/";

    public void run(final int port, final String url) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            // 将多个消息转换为单一的FullHttpRequest或者FullHttpResponse，
                            // 原因是HTTP解码器在每个HTTP消息中会生成多个消息对象：
                            //  1. HttpRequest/HttpResponse
                            //  2. HttpContent
                            //  3. LastHttpContent
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            // 支持异步发送大的码流(例如大的文件传输)，但不占用过多的内存，防止发生Java内存溢出错误
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            System.out.println("HTTP file server start, the site is : http://127.0.0.1:" + port + url);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = CommonUtil.getPort(args, 8080);
        String url = DEFAULT_URL;
        new HttpFileServer().run(port, url);
    }
}
