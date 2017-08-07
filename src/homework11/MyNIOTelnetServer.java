package homework11;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class MyNIOTelnetServer {
	public static void main(String args[]) throws Exception {
		Selector selector = Selector.open();
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		InetSocketAddress address = new InetSocketAddress(9000);
		serverSocketChannel.socket().bind(address);
		System.out.println("started at " + address);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		while (true) {
			selector.select();
			Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
			while (iter.hasNext()) {
				SelectionKey selectedKey = iter.next();
				if ((selectedKey.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
					ServerSocketChannel serverChannel = (ServerSocketChannel) selectedKey.channel();
					SocketChannel socketChannel = serverChannel.accept();
					socketChannel.configureBlocking(false);
					socketChannel.register(selector, SelectionKey.OP_READ);
					socketChannel.write(ByteBuffer.wrap("Welcome Leader.us Power Man Java Course ...\r\n".getBytes()));
				} else if ((selectedKey.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
					
				} else if ((selectedKey.readyOps() & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) {
					System.out.println("received write event");
					ByteBuffer buffer = (ByteBuffer) selectedKey.attachment();
					SocketChannel socketChannel = (SocketChannel) selectedKey.channel();
					if (buffer != null) {
						int writed = socketChannel.write(buffer);
						System.out.println("writed " + writed);
						if (buffer.hasRemaining()) {
							System.out.println(" not write finished ,bind to session ,remains " + buffer.remaining());
							buffer = buffer.compact();
							buffer.flip();
							selectedKey.attach(buffer);
							selectedKey.interestOps(selectedKey.interestOps() | SelectionKey.OP_WRITE);
						} else {
							System.out.println(" block write finished ");
							selectedKey.attach(null);
							selectedKey.interestOps(selectedKey.interestOps() & ~SelectionKey.OP_WRITE);
						}
					}

				}
				iter.remove();
			}

		}

	}


	
}