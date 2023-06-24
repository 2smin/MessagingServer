package ChatEndpoint;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChattingEndpoint {

    public void runChattingServer(int port){

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup(1), new NioEventLoopGroup(10));
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChattingEndpointInitializer());
        serverBootstrap.bind(port);
    }
}
