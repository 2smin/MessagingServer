package ChatEndpoint;

import ChatManager.ChatRoomContainer;
import Common.ChatLogger;
import Protocols.ChatProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;

public class ChatProtocolHandler extends SimpleChannelInboundHandler<ChatProtocol> {

    private Logger chatLogger = ChatLogger.holder.INSTANCE.getLogger(this.getClass().getSimpleName());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatProtocol msg) throws Exception {

        ChatProtocol.Action action = msg.getAction();
        String chatRoom = msg.getChattingRoomName();
        String userName = msg.getUserName();
        String message = msg.getMessage();
        System.out.println("[" + Thread.currentThread().getName() + "]" + "action : " + action);
        //url 기반으로 해볼까??????
        switch (action) {
            case JOIN:
                try{
                    ChannelGroup group = ChatRoomContainer.getInstance().get(chatRoom).channelGroup;
                    group.add(ctx.channel());
                    group.writeAndFlush(new TextWebSocketFrame(userName + " join to " + chatRoom));
                    chatLogger.info("[" + Thread.currentThread().getName() + "]" + "user name : " + userName + " join to " + chatRoom);
                }catch (Exception e){
                    e.getMessage();
                    ctx.channel().writeAndFlush(new TextWebSocketFrame("chat room is not exist"));
                }
                break;
            case MESSAGE:
                try{
                    ChatRoomContainer.getInstance().get(chatRoom).channelGroup.writeAndFlush(
                            new TextWebSocketFrame(userName + " : " + message)
                    );

                    chatLogger.info("[" + Thread.currentThread().getName() + "]" + userName + " : " + message);
                }catch (Exception e){
                    e.getMessage();
                    ctx.channel().writeAndFlush(new TextWebSocketFrame("chat room is not exist"));
                }
                //Read만 chatEndlpoint에서 하고 crd는 serviceAPi의 싱글 스레드가 하기 때문에 동시성 보장 가능
                break;
            case EXIT:
                try{
                    chatLogger.info("[" + Thread.currentThread().getName() + "]" + "user name : " + userName + " exit from " + chatRoom);
                    ChatRoomContainer.getInstance().get(chatRoom).channelGroup.writeAndFlush(
                            new TextWebSocketFrame(userName + " exit from " + chatRoom)
                    );
                    ChatRoomContainer.getInstance().get(chatRoom).channelGroup.remove(ctx.channel());
                    break;

                }catch (Exception e){
                    e.getMessage();
                    ctx.channel().writeAndFlush(new TextWebSocketFrame("chat room is not exist"));
                }
            //TODO : 백그라운드 채팅방에 들어왔을 때의 action도 설정해야할듯.
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception caught");
        cause.printStackTrace();
    }
}
