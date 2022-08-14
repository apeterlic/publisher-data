package com.publishers.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a helper class that holds error messages during file processing.
 *
 * @author Ana Peterlic
 */
public class ErrorHelper {

    private static final List<String> errors = new ArrayList<>();

    /**
     * Private constructor since this class contains only static methods and should not be instantiated.
     */
    private ErrorHelper() {
    }

    public static void add(String s) {
        errors.add(s);
    }

    public static List<String> get() {
        return errors;
    }

    public static void clear() {
        errors.clear();
    }

}
