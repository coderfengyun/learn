package org.tcse.algorithms.learn;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

import org.junit.Test;

public class TryNIO {
	
	@Test
	public void test_UseNIO(){
		try {
			Selector selector = Selector.open();
			ServerSocketChannel ssChannel = createANonBlockAcceptSSChanel();
	 		ssChannel.register(selector, SelectionKey.OP_ACCEPT);
	 		pollEachSelectedKey(selector);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ServerSocketChannel createANonBlockAcceptSSChanel(){
		ServerSocketChannel ssChannel = null;
		try {
			ssChannel = ServerSocketChannel.open();
			ssChannel.configureBlocking(false);
	 		ssChannel.socket().bind(new InetSocketAddress(8998));
		} catch (IOException e) {
			e.printStackTrace();
		}
 		return ssChannel;
	}
	
	private void pollEachSelectedKey(Selector selector) {
		while (true) {
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			for (SelectionKey sKey : selectedKeys) {
				if ((sKey.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
					dealWithAccept(sKey);
				}else if ((sKey.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
					dealWithRead(sKey);
				} 
				selectedKeys.remove(sKey);
			}
		}
	}

	

	private void dealWithAccept(SelectionKey sKey) {
		
	}
	
	private void dealWithRead(SelectionKey sKey) {
		
	}
	
}
