package com.wangzhf.rpc.netty.client;

import com.wangzhf.rpc.netty.api.Constant;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 服务发现
 */
public class ServiceDiscovery {

	private static Logger logger = LoggerFactory.getLogger(ServiceDiscovery.class);

	private CountDownLatch latch = new CountDownLatch(1);

	private volatile List<String> dataList = new ArrayList<>();

	private String registryAddress;

	public ServiceDiscovery(String registryAddress) {
		this.registryAddress = registryAddress;
		ZooKeeper zk = connectServer();
		if(zk != null) {
			watchNode(zk);
		}
	}

	public String discover() {
		String data = null;
		int size = dataList.size();
		if(size > 0) {
			if(size == 1) {
				data = dataList.get(0);
				logger.debug("using only data: {} ", data);
			}else{
				data = dataList.get(ThreadLocalRandom.current().nextInt(size));
				logger.debug("using random data: {} ", data);
			}
		}
		return data;
	}

	private ZooKeeper connectServer() {
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
				@Override
				public void process(WatchedEvent watchedEvent) {
					if(watchedEvent.getState() == Event.KeeperState.SyncConnected) {
						latch.countDown();
					}
				}
			});
			latch.await();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			logger.error("connect server error: ", e);
		}
		return zk;
	}

	private void watchNode(final ZooKeeper zk) {
		try {
			List<String> nodeList = zk.getChildren(Constant.ZK_REGISTRY_PATH, new Watcher() {
				@Override
				public void process(WatchedEvent watchedEvent) {
					if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
						watchNode(zk);
					}
				}
			});

			List<String> dataList = new ArrayList<>();
			for(String node : nodeList) {
				byte[] bytes = zk.getData(Constant.ZK_REGISTRY_PATH + "/" + node, false, null);
				dataList.add(new String(bytes));
			}
			logger.debug("node data: {} ", dataList);
			this.dataList = dataList;
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
			logger.error("watchNode error: ", e);
		}
	}
}
