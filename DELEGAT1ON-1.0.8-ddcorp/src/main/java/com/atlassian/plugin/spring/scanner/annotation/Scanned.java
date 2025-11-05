package com.atlassian.plugin.spring.scanner.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/** Stub annotation just for compile-time. */
@Retention(CLASS)
@Target({ TYPE })
public @interface Scanned {}
