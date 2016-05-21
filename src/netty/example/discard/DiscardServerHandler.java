package netty.example.discard;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;


public class DiscardServerHandler extends SimpleChannelHandler {

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		
		// 화면에 받은거 그대로 출력
		ChannelBuffer buf = ((ChannelBuffer) e.getMessage()).copy(); // 아래에서 readByte() 하면, 내용이 없어지므로, 복사본 가져오게 했음.
		while(buf.readable()) {
			System.out.print((char)buf.readByte());
		}
		
		// Echo
		Channel ch = e.getChannel();
		ch.write(e.getMessage());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getCause().printStackTrace();
		
		Channel ch = e.getChannel();
		ch.close();
	}
	
}
