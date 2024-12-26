package BsK.common.packet.res;

import BsK.common.packet.Packet;
import BsK.server.database.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GetPatientInfoRes implements Packet {

  Patient patient;
}
