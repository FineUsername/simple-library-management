package com.mygroup.coursework.gui;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.mygroup.coursework.gui.net.Request;

@Retention(RUNTIME)
@Target(FIELD)
public @interface InputField {
  public String name() default "";

  public Request[] usedIn();
}
