package Boostraps;

import io.netty.channel.Channel;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.HashSet;
import java.util.Set;

public class ChatRoom {

    public String name;
    public Set<String> users = new HashSet<>();
    public ChannelGroup channelGroup;


    protected ChatRoom(String name){
        this.name = name;
        channelGroup = new DefaultChannelGroup(name, new DefaultEventLoop());
    }

    public void joinChatRoom(String userName, Channel clientChannel){
        users.add(userName);
        channelGroup.add(clientChannel);
    }
}
