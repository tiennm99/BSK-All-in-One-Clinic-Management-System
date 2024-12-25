package BsK.common.packet;

import BsK.common.util.gson.AbstractTypeSerializer;
import BsK.common.util.reflection.ReflectionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PacketSerializer extends AbstractTypeSerializer<Packet> {

  @Getter(lazy = true)
  private static final PacketSerializer instance = new PacketSerializer();
  public static final Gson GSON =
      new GsonBuilder()
          .registerTypeHierarchyAdapter(Packet.class, getInstance())
          .create();
  private static final Map<String, Class<? extends Packet>> classNameToClass =
      ReflectionUtil.getSubTypesOf(Packet.class).stream()
          .collect(Collectors.toMap(Class::getSimpleName, clazz -> clazz));


  @Override
  protected Class<?> getClassForName(String className) {
    return classNameToClass.get(className);
  }

  @Override
  protected String getClassName(Packet object) {
    return object.getClass().getSimpleName();
  }

}
