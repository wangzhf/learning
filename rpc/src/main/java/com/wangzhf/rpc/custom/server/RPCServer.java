package com.wangzhf.rpc.custom.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RPCServer implements Server {

	private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private static final HashMap<String, Class> serviceRegistry = new HashMap<>();

	private static boolean isRunning = false;

	private int port;

	public RPCServer(int port){
		this.port = port;
	}

	@Override
	public void stop() {
		isRunning = false;
		executor.shutdown();
	}

	@Override
	public void start() throws IOException {
		ServerSocket server = new ServerSocket();
		server.bind(new InetSocketAddress(port));
		System.out.println("Start server...");
		try {
			while (true) {
				// 监听客户端TCP链接，接收到后封装成task，有线程池执行
				executor.execute(new ServiceTask(server.accept()));
			}
		} finally {
			server.close();
		}
	}

	@Override
	public void register(Class serviceInterface, Class impl) {
		serviceRegistry.put(serviceInterface.getName(), impl);
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public int getPort() {
		return this.port;
	}

	private static class ServiceTask implements Runnable {
		Socket client = null;

		public ServiceTask(Socket client){
			this.client = client;
		}

		@Override
		public void run() {
			ObjectInputStream in = null;
			ObjectOutputStream out = null;
			try {
				// 将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果
				in = new ObjectInputStream(client.getInputStream());
				String serviceName = in.readUTF();
				String methodName = in.readUTF();
				Class<?>[] parameterTypes = (Class<?>[]) in.readObject();
				Object[] args = (Object[]) in.readObject();
				Class serviceClass = serviceRegistry.get(serviceName);
				if(serviceClass == null ){
					throw new ClassNotFoundException(serviceName + " is not found!");
				}
				Method method = serviceClass.getMethod(methodName, parameterTypes);
				Object result = method.invoke(serviceClass.newInstance(), args);

				// 将执行结果反序列化，通过socket发送给客户端
				out = new ObjectOutputStream(client.getOutputStream());
				out.writeObject(result);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} finally {
				if(out != null){
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(client != null){
					try {
						client.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Server server = new RPCServer(8080);
		server.register(HelloService.class, HelloServiceImpl.class);
		server.start();
	}
}
