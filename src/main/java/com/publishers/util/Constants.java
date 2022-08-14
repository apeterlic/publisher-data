package com.publishers.util;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    /**
     * Private constructor since this class contains only static methods and should not be instantiated.
     */
    private Constants() {
    }

    public static class ApiMessages {

        public static final String ERROR_FILE_NOT_SELECTED = "Please select a file to upload.";
        public static final String OK_UPLOAD = "You successfully uploaded %s!";

        /**
         * Private constructor since this class contains only static methods and should not be instantiated.
         */
        private ApiMessages() {
        }
    }

    public static class ApiAttribute {

        public static final String MESSAGE = "message";

        /**
         * Private constructor since this class contains only static methods and should not be instantiated.
         */
        private ApiAttribute() {
        }
    }

}
