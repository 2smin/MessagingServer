package ChatEndpoint;

import ChatManager.ChatRoomContainer;
import Protocols.ChatProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

public class ChatProtocolHandler extends SimpleChannelInboundHandler<ChatProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatProtocol msg) throws Exception {

        String chatRoom = msg.getChattingRoomName();
        String userName = msg.getUserName();
        String message = msg.getMessage();

        System.out.println("message: " + message);

        //FIXME : send message to all channelGroup users
        //TextWebSocketFrame 에 넣어서 보내야한다.
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame();
        textWebSocketFrame.content().writeBytes(message.getBytes(CharsetUtil.UTF_8));
        ChatRoomContainer.getInstance().get("test").channelGroup.writeAndFlush(
                textWebSocketFrame
        );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception caught");
        cause.printStackTrace();
    }
}
