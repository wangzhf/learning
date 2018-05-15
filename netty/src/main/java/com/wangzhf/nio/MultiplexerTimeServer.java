package com.wangzhf.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel servChannel;

    private volatile boolean stop;

    /**
     * 初始化多路复用器、绑定监听端口
     * @param port
     */
    public MultiplexerTimeServer(int port){
        try {
            selector = Selector.open();
            servChannel = ServerSocketChannel.open();
            // 配置为非阻塞
            servChannel.configureBlocking(false);
            servChannel.socket().bind(new InetSocketAddress(port), 1024);
            servChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
            // 资源初始化失败，退出
            System.exit(1);
        }
    }

    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        while(!stop){
            try {
                // 无论是否有读写事件发生，selector每个1s都被唤醒一次
                selector.select(1000);
                // selector将返回就绪状态的Channel的SelectionKey集合
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                SelectionKey key = null;
                while(it.hasNext()){
                    key = it.next();
                    it.remove();
                    // handle
                    try {
                        handleInput(key);
                    }catch (Exception e){
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动注册并关闭，所以不需要重复释放资源
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            // 处理新接入的请求消息
            if(key.isAcceptable()){
                // Accept the new connection
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                // Add the new connection to the selector
                sc.register(selector, SelectionKey.OP_READ);
            }
            if(key.isReadable()){
                // Read the data
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                // 前面已经将SocketChannel设置为非阻塞，所以此时的read方法为非阻塞
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0){
                    // 读到字节，对字节进行编解码
                    // flip操作：将缓冲区当前的limit设置为position，position设置为0，用于后续对缓冲区的操作
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order: " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                            new Date(System.currentTimeMillis()).toString() :
                            "BAD ORDER";
                    doWrite(sc, currentTime);
                }else if(readBytes < 0){
                    // 返回值为-1，链路已经关闭，需要关闭SocketChannel，释放资源
                    key.cancel();
                    sc.close();
                }else{
                    // 读到0字节，忽略
                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        if(response != null && response.trim().length() > 0){
            byte[] bytes = response.getBytes();
            ByteBuffer writeByffer = ByteBuffer.allocate(bytes.length);
            writeByffer.put(bytes);
            writeByffer.flip();
            channel.write(writeByffer);
        }
    }
}
