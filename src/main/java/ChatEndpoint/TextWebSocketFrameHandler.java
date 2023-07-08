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
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //protocol 변환

        System.out.println("receive message");
        String message = msg.content().toString(CharsetUtil.UTF_8);
        JsonObject jsonObject;
        Gson gson = new Gson();

        jsonObject = gson.fromJson(message, JsonObject.class);

        ChatProtocol chatProtocol = new ChatProtocol(
                jsonObject.get("chattingRoomName").getAsString(),
                jsonObject.get("userName").getAsString(),
                jsonObject.get("message").getAsString(),
                jsonObject.get("action").getAsString()
        );


        ctx.fireChannelRead(chatProtocol);

    }
}
