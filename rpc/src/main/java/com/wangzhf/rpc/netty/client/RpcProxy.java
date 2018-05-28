package com.wangzhf.rpc.netty.client;

import com.wangzhf.rpc.netty.api.codec.RpcRequest;
import com.wangzhf.rpc.netty.api.codec.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class RpcProxy {

	private String serverAddress;

	private ServiceDiscovery serviceDiscovery;

	public RpcProxy(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public RpcProxy(ServiceDiscovery serviceDiscovery) {
		this.serviceDiscovery = serviceDiscovery;
	}

	public <T> T create(Class<T> interfaceClass) {
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
				new Class<?>[]{interfaceClass},
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						RpcRequest request = new RpcRequest();
						request.setRequestId(UUID.randomUUID().toString());
						request.setClassName(method.getDeclaringClass().getName());
						request.setMethodName(method.getName());
						request.setParameterTypes(method.getParameterTypes());
						request.setParameters(args);

						if(serviceDiscovery != null ){
							serverAddress = serviceDiscovery.discover();
						}

						String[] array = serverAddress.split(":");
						String host = array[0];
						int port = Integer.parseInt(array[1]);

						// 初始化RPC客户端
						RpcClient client = new RpcClient(host, port);
						// 通过RPC客户端发送RPC请求并获取RPC响应
						RpcResponse response = client.send(request);
						Throwable error = response.getError();
						if(error != null ){
							throw error;
						}else{
							return response.getResult();
						}
					}
				});
	}
}
