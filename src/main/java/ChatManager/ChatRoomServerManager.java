package ChatManager;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class ChatRoomServerManager {
    private ChatRoomServerManager(){
        this.eventExecutors = new NioEventLoopGroup(10);
    }
    private EventLoopGroup eventExecutors;

    //모든 Thread에서 공통으로 사용할 chatRoomServerManager
    private static class ChatRoomServerHolder{
        public static final ChatRoomServerManager INSTANCE = new ChatRoomServerManager();
    }
    public static ChatRoomServerManager getInstance(){
        return ChatRoomServerHolder.INSTANCE;
    }


    //chatRoom 마다 포트를 가질수 없다. 포트는 한정, chatRoom을 라우팅하자.
    //우선 ServerBootStrap을 뚫어서 node를 구분해야한다
    public void addChatRoom(String name){
        ChatRoom chatRoom = new ChatRoom(name);
        ChatRoomContainer.getInstance().add(name, chatRoom);

    }

    public void removeChatRoom(String name) throws NullPointerException{
        ChatRoomContainer.getInstance().remove(name);
    }

    public void joinChatRoom(String chatRoomName, String userName, Channel clientChannel){
        ChatRoom chatRoom;
        try{
            chatRoom = ChatRoomContainer.getInstance().get(chatRoomName);
            chatRoom.joinChatRoom(userName, clientChannel);
        }catch (IllegalArgumentException e){
            //생성 여부 질문하기
            System.out.println(e.getMessage());
            return;
        }

    }

    public ChatRoom getChatRoom(String test){
        ChatRoom chatRoom = ChatRoomContainer.getInstance().get("test");
        return chatRoom;
    }




}
