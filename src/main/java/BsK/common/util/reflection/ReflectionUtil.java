package BsK.common.util.reflection;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ReflectionUtil {

  private static final String PACKAGE = "BsK";

  public static final ScanResult SCAN_RESULT =
      new ClassGraph().acceptPackages(PACKAGE).enableAllInfo().scan();

  public static <T> Set<Class<? extends T>> getSubTypesOf(Class<T> clazz) {
    if (clazz.isInterface()) {
      return SCAN_RESULT.getClassesImplementing(clazz).stream()
          .map((ClassInfo classInfo) -> (Class<? extends T>) classInfo.loadClass())
          .collect(Collectors.toSet());
    } else {
      return SCAN_RESULT.getSubclasses(clazz.getName()).stream()
          .map((ClassInfo classInfo) -> (Class<? extends T>) classInfo.loadClass())
          .collect(Collectors.toSet());
    }
  }
}
