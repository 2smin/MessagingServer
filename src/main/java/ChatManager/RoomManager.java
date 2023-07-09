package ChatManager;

import Common.ChatLogger;
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
import org.slf4j.Logger;

import java.net.InetSocketAddress;

public class RoomManager {

    private static Logger logger = ChatLogger.holder.INSTANCE.getLogger(RoomManager.class.getName());
    public static void start() throws InterruptedException{
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup(1), new NioEventLoopGroup(10));
        serverBootstrap.channel(LocalServerChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                logger.info("initialize RoomManager Endpoint");
                //통신 방식은 chatProtocol
                ChannelPipeline pipeLine = ch.pipeline();
                pipeLine.addLast(new ChatRoomManagingHandler());

            }
        });

        serverBootstrap.bind(new LocalAddress(MessagingServerConst.CHAT_MANAGER_PORT)).sync();
    }
}
