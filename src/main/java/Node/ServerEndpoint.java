package Node;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServerEndpoint {


    //외부 사용자들을 위한 서버 엔드포인트
    public static void runServerEndpoint(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup(1), new NioEventLoopGroup(10));
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                //통신 방식은 일단 http로
                ChannelPipeline pipeLine = ch.pipeline();
                pipeLine.addLast(new HttpServerCodec());
                pipeLine.addLast(new HttpObjectAggregator(512*1024));
                pipeLine.addLast(new ChatRoomHandler());
            }
        });
        serverBootstrap.bind(33335);

    }
}
