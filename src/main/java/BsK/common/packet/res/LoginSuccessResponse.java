package BsK.common.packet.res;

import BsK.server.network.entity.Role;
import BsK.common.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginSuccessResponse implements Packet {
    int userId;
    Role role;
}
