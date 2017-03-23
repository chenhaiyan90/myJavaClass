package homework11.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class IOHandler implements Runnable {
	private final SelectionKey selectionKey;
	private final SocketChannel socketChannel;
	private ByteBuffer writeBuffer;
	private ByteBuffer readBuffer;
	private int lastMessagePos;
	public IOHandler(final  Selector selector,SocketChannel socketChannel) throws IOException {
		socketChannel.configureBlocking(false);
		this.socketChannel=socketChannel;
		selectionKey = socketChannel.register(selector, 0);
		selectionKey.interestOps(SelectionKey.OP_READ);
		writeBuffer=ByteBuffer.allocateDirect(1024*2);
		readBuffer=ByteBuffer.allocateDirect(100);
		//绑定会话
		selectionKey.attach(this);
		writeBuffer.put("Welcome Leader.us Power Man Java Course ...\r\nTelnet>".getBytes());
		writeBuffer.flip();
		doWriteData();
	}

	
	public void run() {
		try
		{
            if(selectionKey.isReadable())
            {
            	doReadData();
            }else  if(selectionKey.isWritable())
            {
            	doWriteData();
            }
            
		}catch(Exception e)
		{
			e.printStackTrace();
			selectionKey.cancel();
			try {
				socketChannel.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
            	
	}

	private void doWriteData() throws IOException {
		
		writeToChannel();
	}
	
	private void writeToChannel() throws IOException
	{
		int writed = socketChannel.write(writeBuffer);
		System.out.println("writed "+writed );
		if (writeBuffer.hasRemaining()) {
			System.out.println("writed "+writed+" not write finished  so bind to session ,remains " + writeBuffer.remaining());
			selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
		} else {
			System.out.println(" block write finished ");
			writeBuffer.clear();
			selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE| SelectionKey.OP_READ);
		}
	}

	private void doReadData() throws IOException {
		//System.out.println("readed ");
		socketChannel.read(readBuffer);
		int readEndPos=readBuffer.position();
		String readedLine=null;
		for(int i=lastMessagePos;i<readEndPos;i++)
		{
			//System.out.println(readBuffer.get(i));
			if(readBuffer.get(i)==13)
			{// a line finished
			   byte[] lineBytes=new byte[i-lastMessagePos];
			   readBuffer.position(lastMessagePos);
			   readBuffer.get(lineBytes);
			   lastMessagePos=i;
			   readedLine=new String(lineBytes);
		       System.out.println("received line ,lenth:"+readedLine.length()+" value "+readedLine);
		       break;
			}
		}
		
		if(readedLine!=null)
		{
			//取消读事件关注，因为要应答数据
			selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_READ);
			//处理指令
			processCommand(readedLine);
			
		}
		if(readBuffer.position()>readBuffer.capacity()/2)
		{//清理前面读过的废弃空间
			System.out.println(" rewind read byte buffer ,get more space  "+readBuffer.position());
			readBuffer.limit(readBuffer.position());
			readBuffer.position(lastMessagePos);
			readBuffer.compact();
			lastMessagePos=0;
		}
	
	}


	private void processCommand(String readedLine) throws IOException {
		
		if(readedLine.startsWith("dir"))
		{
			readedLine="cmd  /c "+readedLine;
			String result=LocalCmandUtil.callCmdAndgetResult(readedLine);
			writeBuffer.put(result.getBytes("GBK"));
			writeBuffer.put("\r\nTelnet>".getBytes());
		}else
		{
			for (int i = 0; i < writeBuffer.capacity()-10 ; i++) {
				writeBuffer.put((byte) ('a' + i % 25));
			}
			writeBuffer.put("\r\nTelnet>".getBytes());
		}
		writeBuffer.flip();
		writeToChannel();
		
		
	}
}