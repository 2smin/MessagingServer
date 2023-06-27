package ChatEndpoint;

import Boostraps.ChatRoomServerManager;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

public class HttpRequestHandler extends ChannelInboundHandlerAdapter {


    public HttpRequestHandler(){
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;

            System.out.println("[" + Thread.currentThread().getName() + "] : incoming http request");
            HttpHeaders headers = request.headers();

            //chatroom에 대한 create join, delete를 진행
            if (request.uri().equalsIgnoreCase("/createRoom")) {
                ChatRoomServerManager chatManager = ChatRoomServerManager.getInstance();
                chatManager.addChatRoom("test");
                System.out.println("user :" + ctx.channel().id() + " create chatroom");
            } else if (request.uri().equalsIgnoreCase("/joinRoom")) {
                //join한 후 바로 chat을 시작할수 있도록 해야한다. 그럼 websocket upgrade request가 joinRoom에 대한 정보를 담고 있어야함.
                ChatRoomServerManager chatManager = ChatRoomServerManager.getInstance();
                chatManager.joinChatRoom("test", ctx.channel());
                System.out.println("user :" + ctx.channel().id() + " join chatroom");
            }

            if (headers.contains(HttpHeaderNames.CONNECTION) && headers.contains(HttpHeaderNames.UPGRADE)) {
                if (headers.get(HttpHeaderNames.CONNECTION).equalsIgnoreCase(HttpHeaderValues.UPGRADE.toString()) &&
                        headers.get(HttpHeaderNames.UPGRADE).equalsIgnoreCase(HttpHeaderValues.WEBSOCKET.toString())) {
                    ctx.pipeline().replace(this, "TextWebScoketFrameHandler", new TextWebSocketFrameHandler());
                    ctx.pipeline().addLast(new ChatProtocolHandler());
                }
            }

            String websocketUri = "ws://" + request.headers().get(HttpHeaderNames.HOST) + request.uri();
            System.out.println("websocket connect request from : " + websocketUri);
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
