package by.kolgotik.filter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation specifies by.kolgotik.filter name for client side.
 * For example: You have field 'name' in your by.kolgotik.filter object, but you want to use it as 'username' in your client side.
 * You can specify it like this: @ClientFilterName(name="username")
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ClientFilterName {
    String name();
}
