package Common;

import Enums.MessagingServerConst;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalChannel;

public class Global {

    private Global(){}

    private static class Holder{
        private static final Global INSTANCE = new Global();
    }

    public static Global getInstance(){
        return Holder.INSTANCE;
    }


    public static void init(){
    }
}
