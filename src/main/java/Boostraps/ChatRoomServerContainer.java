package Boostraps;

import io.netty.bootstrap.ServerBootstrap;

import java.util.HashMap;
import java.util.Map;

public class ChatRoomServerContainer {

    private ChatRoomServerContainer(){}

    private static class ChatRoomServerContainerHolder{
        private static final ChatRoomServerContainer INSTANCE = new ChatRoomServerContainer();
    }

    public static ChatRoomServerContainer getInstance(){
        return ChatRoomServerContainerHolder.INSTANCE;
    }

    private Map<String, ServerBootstrap> chatRoomServerMap = new HashMap<>();
}
