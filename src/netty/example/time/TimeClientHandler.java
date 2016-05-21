package netty.example.time;

import java.util.Date;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class TimeClientHandler extends SimpleChannelHandler {
	private final ChannelBuffer internalBuffer = ChannelBuffers.dynamicBuffer();

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		ChannelBuffer buf = (ChannelBuffer) e.getMessage();
		internalBuffer.writeBytes(buf); // 받은 데이터를 계속 Buffer 에 쌓는다
			
		// 4 바이트 씩 Frame 을 끊기.
		if (internalBuffer.readableBytes() >= 4) {
			long currentTimeMillis = internalBuffer.readInt() * 1000L;
			System.out.println(new Date(currentTimeMillis));
			e.getChannel().close();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}
	
}
