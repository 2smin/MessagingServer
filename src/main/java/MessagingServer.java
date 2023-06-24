import ChatEndpoint.ChattingEndpoint;

import static ServiceApi.ServiceApiEndpoint.runServerEndpoint;

public class MessagingServer {

    public static void main(String[] args) {
        runServerEndpoint();
        ChattingEndpoint chattingEndpoint = new ChattingEndpoint();
        chattingEndpoint.runChattingServer(33336);
    }
}
