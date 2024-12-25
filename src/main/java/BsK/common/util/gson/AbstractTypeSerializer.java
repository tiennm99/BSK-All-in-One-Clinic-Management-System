package BsK.common.util.gson;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;

/**
 * Serialize/deserialize object thành json có chứa thông tin class. Dùng trong trường hợp muốn
 * serialize một interface/abstract.
 */
@Slf4j
public abstract class AbstractTypeSerializer<T> implements JsonDeserializer<T>, JsonSerializer<T> {

  protected static final String CLASS_KEY = "class";
  /**
   * Trong một số trường hợp class truyền vào không phải object thông thường (primitive, enum,
   * array,...) thì cần cho vào 1 object to hơn để chứa cả class lẫn value.
   */
  protected static final String VALUE_KEY = "value";
  private static final Gson NORMAL_GSON = new Gson();

  protected Class<?> getClassForName(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      log.error("classNotFound: {}", className, e);
      return Object.class;
    }
  }

  protected String getClassName(T object) {
    return object.getClass().getName();
  }

  protected Gson getGson() {
    return NORMAL_GSON;
  }

  @Override
  public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
    var jsonTree = getGson().toJsonTree(src);
    if (jsonTree.isJsonObject()) {
      var json = jsonTree.getAsJsonObject();
      var className = getClassName(src);
      json.addProperty(CLASS_KEY, className);
      return json;
    } else {
      var json = new JsonObject();
      var className = getClassName(src);
      json.addProperty(CLASS_KEY, className);
      json.add(VALUE_KEY, jsonTree);
      return json;
    }
  }

  @Override
  public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    var jsonObject = json.getAsJsonObject();
    var jsonPrimitive = (JsonPrimitive) jsonObject.get(CLASS_KEY);
    if (jsonPrimitive == null) {
      log.error("json not include type: {}", jsonObject);
      return null;
    }
    var classname = jsonPrimitive.getAsString();
    Class<?> clazz = getClassForName(classname);
    if (jsonObject.has(VALUE_KEY)) {
      return (T) getGson().fromJson(jsonObject.get(VALUE_KEY), clazz);
    } else {
      return (T) getGson().fromJson(json, clazz);
    }
  }
}
