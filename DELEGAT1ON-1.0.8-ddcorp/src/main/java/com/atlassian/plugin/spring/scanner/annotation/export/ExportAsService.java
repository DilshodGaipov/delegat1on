package com.atlassian.plugin.spring.scanner.annotation.export;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/** Stub for @ExportAsService({MyInterface.class}). */
@Retention(CLASS)
@Target({ TYPE })
public @interface ExportAsService {
    Class<?>[] value() default {};
}
