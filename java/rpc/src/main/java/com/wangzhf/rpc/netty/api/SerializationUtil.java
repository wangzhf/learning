package com.wangzhf.rpc.netty.api;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用Protostuff序列化
 */
public class SerializationUtil {

	private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

	private static Objenesis objenesis = new ObjenesisStd(true);

	private SerializationUtil() {
	}

	private static <T> Schema<T> getSchema(Class<T> cls) {
		Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
		if(schema == null) {
			schema = RuntimeSchema.createFrom(cls);
			if(schema != null) {
				cachedSchema.put(cls, schema);
			}
		}
		return schema;
	}

	public static <T> byte[] serialize(T obj) {
		Class<T> cls = (Class<T>) obj.getClass();
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

		try {
			Schema<T> schema = getSchema(cls);
			return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
		} finally {
			buffer.clear();
		}
	}

	public static <T> T deserialize(byte[] data, Class<T> cls) {
		T msg = objenesis.newInstance(cls);
		Schema<T> schema = getSchema(cls);
		ProtostuffIOUtil.mergeFrom(data, msg, schema);
		return msg;
	}
}
