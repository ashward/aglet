package com.github.ashward.aglet.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface AuthenticationNotRequired { }