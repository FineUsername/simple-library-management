package com.mygroup.coursework.gui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mygroup.coursework.gui.net.Request;

public class InputFieldAssembler {

  private Class<?> type;

  public InputFieldAssembler(Class<?> type) {
    this.type = type;
  }

  public String getAnnotatedName(Field field) {
    if (!field.isAnnotationPresent(InputField.class)) {
      throw new IllegalArgumentException("Field " + field.getName() + " wasn't annotated");
    }
    return field.getAnnotation(InputField.class).name();
  }

  public String getJsonName(Field field) {
    if (!field.isAnnotationPresent(JsonProperty.class)) {
      throw new IllegalArgumentException("Field " + field.getName() + " wasn't annotated");
    }
    return field.getAnnotation(JsonProperty.class).value();
  }

  public Field getInputFieldByFieldName(String fieldName) {
    Field[] fields = type.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      if (fields[i].isAnnotationPresent(InputField.class)
          && (fields[i].getName().equals(fieldName))) {
        return fields[i];
      }
    }
    throw new IllegalArgumentException(
        "Type didn't have any fields annotated with InputField annotation with field name "
            + fieldName);
  }

  public Field getInputFieldByAnnotatedName(String annotatedName) {
    Field[] fields = type.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      if (fields[i].isAnnotationPresent(InputField.class)
          && (fields[i].getAnnotation(InputField.class).name().equals(annotatedName))) {
        return fields[i];
      }
    }
    throw new IllegalArgumentException(
        "Type didn't have any fields annotated with InputField annotation with annotated name "
            + annotatedName);
  }

  public List<Field> getAllAnnotatedInputFields() {
    List<Field> result = new ArrayList<>();
    Field[] fields = type.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      if (fields[i].isAnnotationPresent(InputField.class)) {
        result.add(fields[i]);
      }
    }
    return result;
  }

  public List<Field> getAllFieldsForRequest(Request request) {
    List<Field> result = new ArrayList<>();
    Field[] fields = type.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      if (fields[i].isAnnotationPresent(InputField.class)
          && Arrays.asList(fields[i].getAnnotation(InputField.class).usedIn()).contains(request)) {
        result.add(fields[i]);
      }
    }
    return result;
  }
}
