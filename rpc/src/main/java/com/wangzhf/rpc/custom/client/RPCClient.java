package com.wangzhf.rpc.custom.client;

import com.wangzhf.rpc.custom.server.HelloService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RPCClient<T> {

	public <T> T getRemoteProxyObj(final Class<?> serviceInterface, final InetSocketAddress addr){
		return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(),
				new Class<?>[]{serviceInterface},
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						Socket socket = null;
						ObjectOutputStream out = null;
						ObjectInputStream in = null;
						try {
							// 创建socket客户端，根据指定地址连接远程服务提供者
							socket = new Socket();
							socket.connect(addr);

							// 将远程服务调用所需的接口类、方法名、参数列表等编码后发送给服务提供者
							out = new ObjectOutputStream(socket.getOutputStream());
							out.writeUTF(serviceInterface.getName());
							out.writeUTF(method.getName());
							out.writeObject(method.getParameterTypes());
							out.writeObject(args);

							// 同步阻塞等待服务器返回响应，获取应答后返回
							in = new ObjectInputStream(socket.getInputStream());
							return in.readObject();
						} finally {
							if(in != null) {
								in.close();
							}
							if(out != null ){
								out.close();
							}
							if(socket != null){
								socket.close();
							}
						}
					}
				});
	}


	public static void main(String[] args) {
		HelloService service = new RPCClient<>().getRemoteProxyObj(HelloService.class, new InetSocketAddress("localhost", 8080));
		String out = service.sayHi("zhangsan");
		System.out.println("==> " + out);
	}
}
