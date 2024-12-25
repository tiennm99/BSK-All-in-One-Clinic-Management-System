package BsK.common.packet.req;

import BsK.common.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterRequest implements Packet {

  String username;
  String password;
  String name;
}
