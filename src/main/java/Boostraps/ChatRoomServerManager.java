package Boostraps;

import io.netty.bootstrap.ServerBootstrap;

import java.util.HashMap;
import java.util.Map;

public class ChatRoomServerManager {
    private ChatRoomServerManager(){}

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
        ChatRoomServerContainer.getInstance().add(name, chatRoom);
    }




}
