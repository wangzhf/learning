package com.wangzhf.rpc.netty.server;

import com.wangzhf.rpc.netty.api.codec.RpcRequest;
import com.wangzhf.rpc.netty.api.codec.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {

	private static Logger logger = LoggerFactory.getLogger(RpcHandler.class);

	private final Map<String, Object> handlerMap;

	public RpcHandler(Map<String, Object> handlerMap) {
		this.handlerMap = handlerMap;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
		RpcResponse resp = new RpcResponse();
		resp.setRequestId(request.getRequestId());

		try {
			Object result = handle(request);
			resp.setResult(result);
		} catch (Throwable e){
			resp.setError(e);
		}
		ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
	}

	private Object handle(RpcRequest request) throws InvocationTargetException {
		String className = request.getClassName();
		Object serviceBean = handlerMap.get(className);

		Class<?> serviceClass = serviceBean.getClass();
		String methodName = request.getMethodName();
		Class<?>[] parameterTypes = request.getParameterTypes();
		Object[] parameters = request.getParameters();

		/*Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);*/

		FastClass serviceFastClass = FastClass.create(serviceClass);
		FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
		return serviceFastMethod.invoke(serviceBean, parameters);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("server caught exception: ", cause);
		ctx.close();
	}
}
