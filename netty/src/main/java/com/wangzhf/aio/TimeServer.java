package com.wangzhf.aio;

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

        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();
    }

}
