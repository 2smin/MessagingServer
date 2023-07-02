package ChatManager;

import Enums.MessagingServerConst;
import ServiceApi.ServiceApiHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.net.InetSocketAddress;

public class RoomManager {

    public static void start() throws InterruptedException{
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup(1), new NioEventLoopGroup(10));
        serverBootstrap.channel(LocalServerChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                System.out.println("initialize RoomManager Endpoint");
                //통신 방식은 chatProtocol
                ChannelPipeline pipeLine = ch.pipeline();
                pipeLine.addLast(new ChatRoomManagingHandler());

            }
        });

        serverBootstrap.bind(new LocalAddress(MessagingServerConst.CHAT_MANAGER_PORT)).sync();
    }
}
