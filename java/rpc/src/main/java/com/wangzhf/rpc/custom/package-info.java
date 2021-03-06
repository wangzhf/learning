/**
 * 自定义实现RPC
 *	使用比较原始的方案实现RPC框架，采用Socket通信、动态代理与反射与Java原生的序列化。
 *	RPC架构分为三部分：
 *		1）服务提供者，运行在服务器端，提供服务接口定义与服务实现类。
 *		2）服务中心，运行在服务器端，负责将本地服务发布成远程服务，管理远程服务，提供给服务消费者使用。
 *		3）服务消费者，运行在客户端，通过远程代理对象调用远程服务。
 *
 */
package com.wangzhf.rpc.custom;