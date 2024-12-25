package BsK.common.util.network;

import BsK.common.packet.Packet;
import BsK.common.packet.PacketSerializer;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NetworkUtil {
  public static void sendPacket(Channel channel, Packet packet) {
    var text = PacketSerializer.GSON.toJson(packet);
    channel.writeAndFlush(new TextWebSocketFrame(text));
  }
}
