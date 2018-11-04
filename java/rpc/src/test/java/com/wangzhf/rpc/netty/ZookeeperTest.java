package com.wangzhf.rpc.netty;

import com.wangzhf.rpc.netty.api.Constant;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperTest {

	CountDownLatch latch = new CountDownLatch(1);

	@Test
	public void testNode() throws IOException, InterruptedException, KeeperException {
		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", Constant.ZK_SESSION_TIMEOUT, new Watcher() {
			@Override
			public void process(WatchedEvent watchedEvent) {
				if(watchedEvent.getState() == Event.KeeperState.SyncConnected) {
					latch.countDown();
				}
			}
		});
		latch.await();
		List<String> dataList = zk.getChildren(Constant.ZK_REGISTRY_PATH, true);
		assert dataList.size() > 0;
	}

}
