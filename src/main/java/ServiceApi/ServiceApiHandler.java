package ServiceApi;

import Protocols.ChatProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.local.LocalChannel;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;

import java.nio.channels.Pipe;

public class ServiceApiHandler extends SimpleChannelInboundHandler<HttpObject> {

    private LocalChannel chatManagerChannel;

    public ServiceApiHandler(LocalChannel chatManagerChannel){
        this.chatManagerChannel = chatManagerChannel;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        HttpRequest request = (HttpRequest) msg;

        System.out.println("ServiceApiHandler : " + request.uri());

        String[] uris = request.uri().split("/");
        //TODO : specify http request handling
        if(uris[1].equalsIgnoreCase("room")){
            ChatProtocol chatProtocol = new ChatProtocol();
            if(uris[2].equalsIgnoreCase("create")){
                chatProtocol.setAction(ChatProtocol.Action.CREATE);
                //TODO : set chatProtocol paramters to handle chatRoom creation
            }else if (uris[2].equalsIgnoreCase("update")) {
                chatProtocol.setAction(ChatProtocol.Action.UPDATE);
            }else if (uris[2].equalsIgnoreCase("delete")) {
                chatProtocol.setAction(ChatProtocol.Action.DELETE);
            }else{
                throw new IllegalStateException("Action must be CREATE, UPDATE, DELETE");
            }
            System.out.println("send to roomManager : " + chatProtocol.getAction().toString());
            chatManagerChannel.writeAndFlush(chatProtocol);
        }

        //chatcontainer에서 특정 이름의 방 찾아서 참여하고 있는 모든 channel 출력


    }
}
