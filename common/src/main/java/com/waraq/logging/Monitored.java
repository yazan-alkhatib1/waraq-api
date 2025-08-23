package com.waraq.logging;

import java.lang.annotation.*;

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Monitored {

    String name() default "";
}
