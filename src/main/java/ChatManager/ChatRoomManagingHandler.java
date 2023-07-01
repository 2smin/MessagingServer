package ChatManager;

import Protocols.ChatProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ChatRoomManagingHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof ChatProtocol){
            ChatProtocol chatProtocol = (ChatProtocol) msg;

            String action = chatProtocol.getAction().toString();

            switch (action) {
                case "CREATE":
                    ChatRoomServerManager.getInstance().addChatRoom(chatProtocol.getChattingRoomName());
                    //TODO : send join signal with assigned websocket port
                    break;
                case "DELETE":
                    ChatRoomServerManager.getInstance().removeChatRoom(chatProtocol.getChattingRoomName());
                    // TODO : send remove signal and close chatRoom
                    break;
                case "UPDATE":

                    break;
                default:
                    throw new IllegalArgumentException("Action must be ENTER or EXIT");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //TODO : handle npe
    }
}
