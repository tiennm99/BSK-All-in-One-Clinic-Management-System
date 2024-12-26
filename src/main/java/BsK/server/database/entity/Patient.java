package BsK.server.database.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Patient {
  String patientId;
  Date birthDate;
  String name;
  Sex sex;

  public enum Sex {
    MALE,
    FEMALE,
  }
}
