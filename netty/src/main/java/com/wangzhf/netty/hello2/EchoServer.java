package com.wangzhf.netty.hello2;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	
	private final int port;
	
	public EchoServer(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) throws InterruptedException {
		int port = 8008;
		new EchoServer(port).start();
	}
	
	public void start() throws InterruptedException{
		NioEventLoopGroup group = new NioEventLoopGroup();
		try {
		
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(group)
				.channel(NioServerSocketChannel.class)
				.localAddress(new InetSocketAddress(port))
				.childHandler(new ChannelInitializer<SocketChannel>() {
	
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new EchoServerHandler());
					}
					
				});
			
			ChannelFuture f = bootstrap.bind().sync();
			System.out.println("Server started and are listening on : " + f.channel().localAddress());
			f.channel().closeFuture().sync();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			group.shutdownGracefully().sync();
		}
	}

}
