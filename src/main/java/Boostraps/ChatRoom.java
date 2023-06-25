package Boostraps;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.HashSet;
import java.util.Set;

public class ChatRoom {

    public String name;
    public Set<String> users = new HashSet<>();
    public ChannelGroup channelGroup = new DefaultChannelGroup(name, null);


    protected ChatRoom(String name){
        this.name = name;
    }

    public void joinChatRoom(String userName, Channel clientChannel){
        users.add(userName);
        channelGroup.add(clientChannel);
    }
}
