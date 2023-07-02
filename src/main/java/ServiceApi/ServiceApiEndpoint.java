package ServiceApi;

import Common.Global;
import Enums.MessagingServerConst;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServiceApiEndpoint {

    private static LocalChannel chatManagerChannel;
    private static EventLoopGroup eventGroup;

    public static void connectToChatManager(){
        //순서 중요
        eventGroup = new NioEventLoopGroup(1);
        chatManagerChannel = new LocalChannel();
        eventGroup.register(chatManagerChannel);
        chatManagerChannel.connect(new LocalAddress(MessagingServerConst.CHAT_MANAGER_PORT));
    }


    //외부 사용자들을 위한 서버 엔드포인트
    public static void runServerEndpoint(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventGroup, new NioEventLoopGroup(10));
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                System.out.println("initialize ServceiAPi Endpoint");
                //통신 방식은 일단 http로
                ChannelPipeline pipeLine = ch.pipeline();
                pipeLine.addLast(new HttpServerCodec());
                pipeLine.addLast(new HttpObjectAggregator(512*1024));
                pipeLine.addLast(new ServiceApiHandler(chatManagerChannel));
            }
        });
        serverBootstrap.bind(33335);

    }
}
