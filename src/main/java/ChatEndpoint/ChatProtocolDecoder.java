package ChatEndpoint;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ChatProtocolDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //byteBuf를 읽어서 ChatProtocol 객체로 변환한다.
        int length = in.readableBytes();
        System.out.println("length = " + length);
    }
}
