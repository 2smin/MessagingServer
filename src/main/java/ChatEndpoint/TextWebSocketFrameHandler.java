package ChatEndpoint;

import Protocols.ChatProtocol;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.CharsetUtil;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

//    private final ChannelGroup channelGroup;

//    public TextWebSocketFrameHandler(ChannelGroup channelGroup) {
//        this.channelGroup = channelGroup;
//    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            //handshake가 완료되면, httpRequestHandler를 파이프라인에 삭제하고 (http 더이상 안옴) , channelGroup에 join message를 전송한다.
            ctx.pipeline().remove(HttpRequestHandler.class);

            //메세지 정보를 보고 어느 채팅룸으로 가야할 메세지인지 확인하고 해당 채팅룸에 메세지를 전송한다.
            //채팅룸에 메세지를 전송하는 부분은 채팅룸에서 처리한다.
            //websocket 내부의 프로토콜을 구현해야함



        }else{
            super.userEventTriggered(ctx,evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //protocol 변환
        String message = msg.content().toString(CharsetUtil.UTF_8);
        JsonObject jsonObject;
        Gson gson = new Gson();

        jsonObject = gson.fromJson(message, JsonObject.class);

        ChatProtocol chatProtocol = new ChatProtocol(
                jsonObject.get("chattingRoomName").getAsString(),
                jsonObject.get("userName").getAsString(),
                jsonObject.get("message").getAsString()
        );

        System.out.println("chatProtocol: " + chatProtocol.toString());

    }
}
