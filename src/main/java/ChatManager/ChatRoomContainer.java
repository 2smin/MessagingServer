package ChatManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatRoomContainer {

    private ChatRoomContainer(){}

    private static class ChatRoomServerContainerHolder{
        private static final ChatRoomContainer INSTANCE = new ChatRoomContainer();
    }

    public static ChatRoomContainer getInstance(){
        return ChatRoomServerContainerHolder.INSTANCE;
    }

    private Map<String, ChatRoom> chatRoomServerMap = new HashMap<>();

    public void add(String chatRoomName, ChatRoom chatRoom){
        chatRoomServerMap.put(chatRoomName, chatRoom);
    }

    public void remove(String chatRoomName) throws NullPointerException{
        get(chatRoomName);
        chatRoomServerMap.remove(chatRoomName);
    }

    public ChatRoom get(String chatRoomName){
        return Objects.requireNonNull(chatRoomServerMap.get(chatRoomName));
    }
}
