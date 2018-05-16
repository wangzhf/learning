package com.wangzhf.util;

/**
 * 辅助工具类
 */
public class CommonUtil {

    /**
     * 获取端口号
     * @param args
     * @param defaultPort
     * @return
     */
    public static int getPort(String[] args, int defaultPort){
        if(args != null && args.length > 0){
            try {
                defaultPort = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){

            }
        }
        return defaultPort;
    }

}
