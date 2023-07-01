public class Global {

    private Global(){}

    private static class Holder{
        private static final Global INSTANCE = new Global();
    }

    public static Global getInstance(){
        return Holder.INSTANCE;
    }


}
