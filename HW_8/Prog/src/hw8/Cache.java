package hw8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

enum _cacheType {IN_MEMORY, IN_FILE};

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Cache {
    _cacheType cacheType() default _cacheType.IN_MEMORY;

    String fileNamePrefix() default "";

    boolean zip() default false;

    Class[] identityBy() default {};

    int listMaxLength() default Integer.MAX_VALUE;
}
