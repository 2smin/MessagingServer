package Protocols;

public class ChatProtocol {

    private String chattingRoomName;
    private String userName;
    private String message;
    private Action action;


    public ChatProtocol(String chattingRoomName, String userName, String message, String action) {
        this.chattingRoomName = chattingRoomName;
        this.userName = userName;
        this.message = message;
        this.action = Action.valueOf(action);
    }

    public ChatProtocol() {
    }

    @Override
    public String toString() {
        return "[ChatRoom] : " + chattingRoomName + " \n" +
                "[User] : " + userName + " \n" +
                "[Message] : " + message;
    }

    public String getChattingRoomName() {
        return chattingRoomName;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action){
        this.action = action;
    }

    public enum Action {
        	JOIN ("join"), EXIT("exit"), CREATE("create"), UPDATE("update"), DELETE("delete"),
        MESSAGE("message");

        String action;

        Action(String enter) {
        }
    }
}
