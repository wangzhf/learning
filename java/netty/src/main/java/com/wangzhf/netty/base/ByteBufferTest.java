package com.wangzhf.netty.base;

import io.netty.buffer.ByteBufUtil;

import javax.sound.midi.Soundbank;
import java.nio.ByteBuffer;

/**
 * JDK ByteBuffer测试
 */
public class ByteBufferTest {

    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(22);

        String value = "今天天气好晴朗";

        buf.put(value.getBytes());
        System.out.println(buf.position());
        buf.flip();
        System.out.println(buf.position());
        byte[] vArray = new byte[buf.remaining()];
        buf.get(vArray);
        String decodeValue = new String(vArray);
        System.out.println(decodeValue);

        // 需要手动扩容
        // buf.put("我".getBytes()); // 空间不够，抛异常
        String newInfo = "，处处好风光";
        int byteLen = newInfo.getBytes().length;
        System.out.println("byteLen: " + byteLen);
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        if(newInfo.getBytes().length > (buf.capacity() - buf.limit())){
            ByteBuffer newBuf = ByteBuffer.allocate(buf.capacity() + newInfo.getBytes().length);
            buf.flip();
            newBuf.put(buf);
            newBuf.put(newInfo.getBytes());
            newBuf.flip();
            byte[] newArr = new byte[newBuf.remaining()];
            newBuf.get(newArr);
            String newValue = new String(newArr);
            System.out.println(newValue);
        }
    }
}
