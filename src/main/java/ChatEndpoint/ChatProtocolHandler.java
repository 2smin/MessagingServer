package ChatEndpoint;

import Boostraps.ChatRoomServerContainer;
import Protocols.ChatProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatProtocolHandler extends SimpleChannelInboundHandler<ChatProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatProtocol msg) throws Exception {

        String chatRoom = msg.getChattingRoomName();
        String userName = msg.getUserName();
        String message = msg.getMessage();

        System.out.println("message: " + message);

        //FIXME : send message to all channelGroup users
        ChatRoomServerContainer.getInstance().get(chatRoom).channelGroup.writeAndFlush(
                "[" + userName + "] : " + message + "\n"
        );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception caught");
        cause.printStackTrace();
    }
}
