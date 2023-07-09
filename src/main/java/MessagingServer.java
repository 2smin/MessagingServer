import ChatEndpoint.ChattingEndpoint;
import ChatManager.RoomManager;
import Common.ChatLogger;
import Common.Global;
import ServiceApi.ServiceApiEndpoint;
import org.slf4j.Logger;

import static ServiceApi.ServiceApiEndpoint.runServerEndpoint;

public class MessagingServer {

    private static Logger logger = ChatLogger.holder.INSTANCE.getLogger(MessagingServer.class.getSimpleName());

    public static void main(String[] args) {
        //chat endpoint에게 roomManager localchannel binding 하기
        ChattingEndpoint chattingEndpoint = new ChattingEndpoint();
        chattingEndpoint.runChattingServer(33336);

        logger.info("booting......");
        try{
            //start room Manager server ep
            RoomManager.start();
            //initialize common resource such as chatManagerChannel
            //connect to chatManager
            ServiceApiEndpoint.connectToChatManager();
            ServiceApiEndpoint.runServerEndpoint();
        }catch (InterruptedException e){
            System.out.println("booting failed.");
        }

        logger.info("booting success.");
    }
}
