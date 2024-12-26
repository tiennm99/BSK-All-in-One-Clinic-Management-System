package BsK.common.packet.req;

import BsK.common.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class LoginRequest implements Packet {

  String username;
  String password;
  LocalDateTime timestamp;

  public LoginRequest(String username, String password) {
    this.username = username;
    this.password = password;
    this.timestamp = LocalDateTime.now(); // Automatically sets the current time
  }
}