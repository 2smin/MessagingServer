package ChatManager;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashSet;
import java.util.Set;

public class ChatRoom {

    public String name;
    public Set<String> users = new HashSet<>();
    public ChannelGroup channelGroup;


    protected ChatRoom(String name){
        this.name = name;
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    public void joinChatRoom(String userName, Channel clientChannel){
        users.add(userName);
        channelGroup.add(clientChannel);
    }

    public void listUsers(){
        System.out.println("ChatRoom Name : " + name);
        System.out.println("Users : ");
        for(String user : users){
            System.out.println(user);
        }
    }

    public void listChannelGroup(){
        System.out.println("ChatRoom Name : " + name);
        System.out.println("ChannelGroup : ");
        for(Channel channel : channelGroup){
            System.out.println(channel);
        }
    }
}
