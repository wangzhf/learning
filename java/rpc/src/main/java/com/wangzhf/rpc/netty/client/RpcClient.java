package com.wangzhf.rpc.netty.client;

import com.wangzhf.rpc.netty.api.codec.RpcDecoder;
import com.wangzhf.rpc.netty.api.codec.RpcEncoder;
import com.wangzhf.rpc.netty.api.codec.RpcRequest;
import com.wangzhf.rpc.netty.api.codec.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {

	private static Logger logger = LoggerFactory.getLogger(RpcClient.class);

	private String host;
	private int port;

	private RpcResponse response;

	private final Object obj = new Object();

	public RpcClient(String host, int port){
		this.host = host;
		this.port = port;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
		this.response = response;

		synchronized (obj) {
			obj.notifyAll(); // 收到响应，唤醒线程
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("client caught exception: ", cause);
		ctx.close();
	}

	public RpcResponse send(RpcRequest request) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {

			Bootstrap b = new Bootstrap();
			b.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
									.addLast(new RpcEncoder(RpcRequest.class))
									.addLast(new RpcDecoder(RpcResponse.class))
									.addLast(RpcClient.this);  // 使用RPCClient发送rpc请求
						}
					})
					.option(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture f = b.connect(host, port).sync();
			f.channel().writeAndFlush(request).sync();

			synchronized (obj) {
				obj.wait();		// 未收到响应，使线程等待
			}

			if(response != null ){
				f.channel().closeFuture().sync();
			}
			return response;
		}finally {
			group.shutdownGracefully();
		}
	}
}
