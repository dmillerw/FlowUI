package me.dmillerw.flow.util;

public class FunctionalUtils {

    @SuppressWarnings("Guava")
    public static <T> com.google.common.base.Predicate<T> javaToGoogle(java.util.function.Predicate<T> java) {
        return java::test;
    }
}
