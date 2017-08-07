package homework10;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by chenhaiyan on 2017/3/22.
 */
public class TelnetEchoServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.bind(new InetSocketAddress(9001));
        Selector selector = Selector.open();
        ssChannel.configureBlocking(false);
        ssChannel.register(selector, SelectionKey.OP_ACCEPT); //注册监听连接请求
        while (true) {
            int selectedNum=selector.select();//阻塞 直到某个channel注册的事件被触发
//            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
//            System.out.println("selectedKeys size = "+selector.selectedKeys().size());
            while(iter.hasNext()) {
                SelectionKey key=iter.next();
                if (key.isAcceptable()) { //客户端连接请求
                    ServerSocketChannel ssc = (ServerSocketChannel) key
                            .channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ); //注册监听客户端输入
                }
                else if((key.readyOps()&SelectionKey.OP_READ)==SelectionKey.OP_READ){ //客户端输入
                    System.out.println("received read event");
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(100);
                    sc.read(buffer);
                    buffer=(ByteBuffer) key.attachment();
                    if(buffer==null||!buffer.hasRemaining()){
                        int dataLength=sc.socket().getSendBufferSize()*50;
                        buffer=ByteBuffer.allocate(dataLength);
                        for (int i = 0; i <buffer.capacity()-2 ; i++) {
                            buffer.put((byte)('a'+i%25));
                        }
                        buffer.flip();
                        System.out.println("send another huge block data "+dataLength);
                    }
                    /*int writeBufferSize=sc.socket().getSendBufferSize();
                    System.out.println("send buffer size:"+writeBufferSize);
                    buffer=ByteBuffer.allocate(writeBufferSize*50+2);
                    for(int i=0;i<buffer.capacity()-2;i++){
                        buffer.put((byte)('a'+i%25));
                    }
                    buffer.put("\r\n".getBytes());
                    buffer.flip();*/
                    int writed=sc.write(buffer);
                    System.out.println("writed "+writed);
                    if(buffer.hasRemaining()){
                        System.out.println("not write finished,remains"+buffer.remaining());
                        buffer=buffer.compact();
                        key.attach(buffer);
                        key.interestOps(key.interestOps()|SelectionKey.OP_WRITE);
                    }else{
                        System.out.println("block write finished");
                        key.attach(null);
                        key.interestOps(key.interestOps()&~SelectionKey.OP_WRITE);
                    }
                }else if((key.readyOps()&SelectionKey.OP_WRITE)==SelectionKey.OP_WRITE){
                    System.out.println("received write event");
                    ByteBuffer buffer=(ByteBuffer)key.attachment();
                    SocketChannel sc= (SocketChannel) key.channel();
                    if(buffer!=null){
                        int writed=sc.write(buffer);
                        System.out.println("writed"+writed);
                        if(buffer.hasRemaining()){
                            System.out.println("not write finished,bind to session,remains"+buffer.remaining());
                            buffer=buffer.compact();
                            key.attach(buffer);
                            key.interestOps(key.interestOps()| SelectionKey.OP_WRITE);
                        }else{
                            System.out.println("block write finished");
                            key.attach(null);
                            key.interestOps(key.interestOps()&~SelectionKey.OP_WRITE);
                        }
                    }

                }
                iter.remove();
            }
        }
    }
}
