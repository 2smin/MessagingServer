package ChatEndpoint;

import Protocols.ChatProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatProtocolHandler extends SimpleChannelInboundHandler<ChatProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatProtocol msg) throws Exception {

        String chatRoom = msg.getChattingRoomName();
        String userName = msg.getUserName();
        String message = msg.getMessage();
        String action = msg.getAction().toString();

        //TODO: paramter로 받은 정보를 가지고, 채팅방에 메세지를 전송한다.
    }
}
