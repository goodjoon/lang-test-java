package netty.example.time;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class TimeServerHandler extends SimpleChannelHandler {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		Channel ch = e.getChannel();
		
		ChannelBuffer time = ChannelBuffers.buffer(4);
		time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
		
		ChannelFuture f = ch.write(time); // XXXFuture 를 리턴하여서, 다음 동작을 지정하게 함. 비동기 이므로!!
		
		f.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) {
				Channel ch = future.getChannel();
				ch.close(); // 이 Close 조차도 비동기 이므로, 다음 동작할 수 있는 핸들을 Future 로 리턴함. 
			}
		});
	}
	
}
