package Boostraps;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatRoomServerContainer {

    private ChatRoomServerContainer(){}

    private static class ChatRoomServerContainerHolder{
        private static final ChatRoomServerContainer INSTANCE = new ChatRoomServerContainer();
    }

    public static ChatRoomServerContainer getInstance(){
        return ChatRoomServerContainerHolder.INSTANCE;
    }

    private Map<String, ChatRoom> chatRoomServerMap = new HashMap<>();

    public void add(String chatRoomName, ChatRoom chatRoom){
        chatRoomServerMap.put(chatRoomName, chatRoom);
    }

    public void remove(String chatRoomName){
        chatRoomServerMap.remove(chatRoomName);
    }

    public ChatRoom get(String chatRoomName){
        if(chatRoomServerMap.get(chatRoomName) == null)
            throw new IllegalArgumentException("There is no chatRoom named " + chatRoomName);
        return Objects.requireNonNull(chatRoomServerMap.get(chatRoomName));
    }
}
