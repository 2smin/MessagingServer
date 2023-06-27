package Protocols;

public class ChatProtocol {

    private String chattingRoomName;
    private String userName;
    private String message;
    private Action action;


    public ChatProtocol(String chattingRoomName, String userName, String message) {
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

    public void setAction(String action){
        switch (action) {
            case "ENTER":
                this.action = Action.ENTER;
                break;
            case "EXIT":
                this.action = Action.EXIT;
                break;
            default:
                throw new IllegalArgumentException("Action must be ENTER or EXIT");
        }
    }

    public enum Action {
        	ENTER, EXIT
    }
}
