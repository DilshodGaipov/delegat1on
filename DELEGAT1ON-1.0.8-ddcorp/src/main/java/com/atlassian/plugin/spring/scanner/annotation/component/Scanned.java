package com.atlassian.plugin.spring.scanner.annotation.component;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/** Stub annotation for compile-time only (scanner not used at build). */
@Retention(CLASS)
@Target({ TYPE })
public @interface Scanned {}
