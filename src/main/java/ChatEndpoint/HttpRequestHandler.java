package ChatEndpoint;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;
    private static final File INDEX;

    static{
        //handler가 위치한 곳에서 index.html을 찾기 위해 경로를 설정
        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            String path = location.toURI() + "Websocket/index.html";
            path = !path.contains("file:") ? path : path.substring(5);
            INDEX = new File(path);
        }catch (Exception e){
            throw new IllegalStateException("unable to locate index.html",e);
        }
    }

    public HttpRequestHandler(String wsUri){
        this.wsUri = wsUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        System.out.println("[" + Thread.currentThread().getName() + "] : incoming http request");
        HttpHeaders headers = request.headers();

        if(headers.contains(HttpHeaderNames.CONNECTION) && headers.contains(HttpHeaderNames.UPGRADE)){
            if (headers.get(HttpHeaderNames.CONNECTION).equalsIgnoreCase(HttpHeaderValues.UPGRADE.toString()) &&
                    headers.get(HttpHeaderNames.UPGRADE).equalsIgnoreCase(HttpHeaderValues.WEBSOCKET.toString())) {
                ctx.pipeline().replace(this, "TextWebScoketFrameHandler", new TextWebSocketFrameHandler());
            }
        }

        String websocketUri = "ws://" + request.headers().get(HttpHeaderNames.HOST) + request.uri();
        System.out.println("websocket connect request from : " + websocketUri);
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                websocketUri, null, false);

        WebSocketServerHandshaker shaker = wsFactory.newHandshaker(request);
        ChannelFuture channelFuture = shaker.handshake(ctx.channel(), request);


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
