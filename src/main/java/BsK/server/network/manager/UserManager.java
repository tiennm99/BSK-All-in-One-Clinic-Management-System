package BsK.server.network.manager;

import BsK.server.network.entity.User;
import io.netty.channel.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserManager {
  private static final Map<Integer, User> USER_MAP = new ConcurrentSkipListMap<>();

  private static final Map<String, Integer> CHANNEL_ID_MAP = new ConcurrentHashMap<>();

  private static final AtomicInteger USER_ID = new AtomicInteger(0);

  public static User getUserById(int userId) {
    return USER_MAP.get(userId);
  }

  public static User getUserByChannelId(String channelId) {
    return USER_MAP.get(CHANNEL_ID_MAP.get(channelId));
  }

  public static User getUserByChannel(Channel channel) {
    return getUserByChannelId(channel.id().asLongText());
  }

  public static int onUserLogin(Channel channel) {
    int userId = USER_ID.incrementAndGet();
    var user = new User(userId, channel);
    USER_MAP.put(userId, user);
    CHANNEL_ID_MAP.put(channel.id().asLongText(), userId);
    return userId;
  }

  public static void onUserDisconnect(Channel channel) {
    USER_MAP.remove(CHANNEL_ID_MAP.remove(channel.id().asLongText()));
  }
}
