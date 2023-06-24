package Protocols;

public class ChatProtocol {

    private String chattingRoomName;
    private String userName;
    private String message;

    public ChatProtocol(String chattingRoomName, String userName, String message){
        this.chattingRoomName = chattingRoomName;
        this.userName = userName;
        this.message = message;
    }

    @Override
    public String toString() {
        return "[ChatRoom] : " + chattingRoomName + " \n" +
                "[User] : " + userName + " \n" +
                "[Message] : " + message;
    }
}
