package Common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.LogManager;

public class ChatLogger {

    private ChatLogger() {
        System.out.println("ChatLogger Initialized");


    }

    public static class  holder {
        public static final ChatLogger INSTANCE = new ChatLogger();
    }

    public Logger getLogger(String className) {

        return LoggerFactory.getLogger(className);
    }
}
