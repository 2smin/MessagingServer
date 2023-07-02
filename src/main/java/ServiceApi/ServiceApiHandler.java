package ServiceApi;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.local.LocalChannel;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;

public class ServiceApiHandler extends SimpleChannelInboundHandler<HttpObject> {

    private LocalChannel chatManagerChannel;

    public ServiceApiHandler(LocalChannel chatManagerChannel){
        this.chatManagerChannel = chatManagerChannel;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        HttpRequest request = (HttpRequest) msg;
    }
}
