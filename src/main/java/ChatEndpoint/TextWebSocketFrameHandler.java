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
            //handshake가 완료되면, httpRequestHandler를 파이프라인에 삭제하고 (http 더이상 안옴) , channelGroup에 join message를 전송한다

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

        String action = jsonObject.get("action").getAsString();
        chatProtocol.setAction(action);

        ctx.writeAndFlush(chatProtocol);

    }
}
