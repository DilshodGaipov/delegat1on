package com.atlassian.plugin.spring.scanner.annotation.imports;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/** Stub for @ComponentImport on fields/ctor params. */
@Retention(CLASS)
@Target({ FIELD, PARAMETER })
public @interface ComponentImport {}
