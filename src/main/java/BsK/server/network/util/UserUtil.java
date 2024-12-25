package BsK.server.network.util;

import BsK.common.packet.Packet;
import BsK.common.util.network.NetworkUtil;
import BsK.server.network.manager.UserManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtil {
  public static void sendPacket(int userId, Packet packet) {
    var user = UserManager.getUserById(userId);
    if (user == null) return;

    NetworkUtil.sendPacket(user.getChannel(), packet);
  }
}
