package ChatEndpoint;

import ChatManager.ChatRoomServerManager;
import Common.ChatLogger;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.slf4j.Logger;

public class HttpRequestHandler extends ChannelInboundHandlerAdapter {

    private Logger chatLogger = ChatLogger.holder.INSTANCE.getLogger(this.getClass().getSimpleName());

    public HttpRequestHandler(){
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;

            System.out.println("[" + Thread.currentThread().getName() + "] : incoming http request");
            HttpHeaders headers = request.headers();

            //TODO : remove all http params... use chatProtocol in this endpoint
            //chatroom에 대한 create join, delete를 진행. 이건 service api로 뺴기

            if (headers.contains(HttpHeaderNames.CONNECTION) && headers.contains(HttpHeaderNames.UPGRADE)) {
                if (headers.get(HttpHeaderNames.CONNECTION).equalsIgnoreCase(HttpHeaderValues.UPGRADE.toString()) &&
                        headers.get(HttpHeaderNames.UPGRADE).equalsIgnoreCase(HttpHeaderValues.WEBSOCKET.toString())) {
                    ctx.pipeline().replace(this, "TextWebScoketFrameHandler", new TextWebSocketFrameHandler());
                    ctx.pipeline().addLast(new ChatProtocolHandler());

                }
            }

            String websocketUri = "ws://" + request.headers().get(HttpHeaderNames.HOST) + request.uri();
            chatLogger.info("websocket connect request from : " + websocketUri);
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                    websocketUri, null, false);

            WebSocketServerHandshaker shaker = wsFactory.newHandshaker(request);
            ChannelFuture channelFuture = shaker.handshake(ctx.channel(), request);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
