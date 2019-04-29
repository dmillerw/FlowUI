package me.dmillerw.flow.util;

import com.google.common.base.Predicate;

import javax.annotation.Nullable;

public class FunctionalUtils {

    public static <T> com.google.common.base.Predicate<T> javaToGoogle(java.util.function.Predicate<T> java) {
        return java::test;
    }
}
