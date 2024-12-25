package BsK.server.network.entity;

import io.netty.channel.Channel;
import lombok.Data;

@Data
public class User {

  int id;
  Channel channel;
  Role role = Role.GUEST;

  public User(int id, Channel channel) {
    this.id = id;
    this.channel = channel;
  }

  public void authenticate(String username, String password) {
    if (username.equals("admin") && password.equals("admin")) {
      role = Role.ADMIN;
    } else if (username.equals("user") && password.equals("user")) {
      role = Role.USER;
    }
  }

  public boolean isAuthenticated() {
    return role != Role.GUEST;
  }
}
