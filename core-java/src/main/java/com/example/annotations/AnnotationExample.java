package com.example.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

public class AnnotationExample {
  public static void main(String[] args) {
    Car car = new Car("Ford", "F150", "2018");
    JsonSerializer serializer = new JsonSerializer();
    try {
      serializer.serialize(car);
    } catch (JsonSerializeException e) {
      e.printStackTrace();
    }
  }

  // todo custom annotation
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface JsonField {
    public String value() default "";
  }

  // todo Car class to use custom annotation
  static class Car {
    @JsonField("manufacturer")
    private final String make;

    @JsonField private final String model;
    private final String year;

    Car(String make, String model, String year) {
      this.make = make;
      this.model = model;
      this.year = year;
    }

    public String getMake() {
      return make;
    }

    public String getModel() {
      return model;
    }

    public String getYear() {
      return year;
    }

    @Override
    public String toString() {
      return year + " " + make + " " + model;
    }
  }

  // todo JsonSerializer to process custom annotation
  public static class JsonSerializer {
    private static String getSerializedKey(Field field) {
      String annotationValue = field.getAnnotation(JsonField.class).value();
      if (annotationValue.isEmpty()) {
        return field.getName();
      } else {
        return annotationValue;
      }
    }

    public String serialize(Object object) throws JsonSerializeException {
      try {
        Class<?> objectClass = requireNonNull(object).getClass();
        Map<String, String> jsonElements = new HashMap<>();
        for (Field field : objectClass.getDeclaredFields()) {
          field.setAccessible(true);
          if (field.isAnnotationPresent(JsonField.class)) {
            jsonElements.put(getSerializedKey(field), (String) field.get(object));
          }
        }
        System.out.println(toJsonString(jsonElements));
        return toJsonString(jsonElements);
      } catch (IllegalAccessException e) {
        throw new JsonSerializeException(e.getMessage());
      }
    }

    private String toJsonString(Map<String, String> jsonMap) {
      String elementsString =
          jsonMap.entrySet().stream()
              .map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"")
              .collect(joining(","));
      return "{" + elementsString + "}";
    }
  }

  // todo custom exception for JsonParser
  static class JsonSerializeException extends Exception {
    private static final long serialVersionUID = -8845242379503538623L;

    JsonSerializeException(String message) {
      super(message);
    }
  }
}
