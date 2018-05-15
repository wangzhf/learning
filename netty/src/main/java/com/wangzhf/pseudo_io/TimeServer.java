package com.wangzhf.pseudo_io;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪异步IO测试
 */
public class TimeServer {

    public static void main(String[] args) {

        int port = 8080;

        if(args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e){
                // nothing to do
            }
        }

        ServerSocket server = null;
        try {
            server = new ServerSocket(port);

            System.out.println("The time server is start in port: " + port);
            Socket socket = null;
            // 创建线程池
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50, 10000);
            while(true){
                socket = server.accept();
                singleExecutor.execute(new TimeServerHandler(socket));
            }
        } catch (IOException e){

        } finally {
            if(server != null){
                System.out.println("The time server will close.");
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                server = null;
            }
        }


    }

}
