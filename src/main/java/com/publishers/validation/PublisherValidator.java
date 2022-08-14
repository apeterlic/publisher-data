package com.publishers.validation;

import com.publishers.helper.ErrorHelper;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.publishers.util.Constants.DATE_FORMATTER;
import static com.publishers.util.Constants.TIME_FORMATTER;

/**
 * Represents a publisher validator class.
 *
 * @author Ana Peterlic
 */
public class PublisherValidator {

    /**
     * Private constructor since this class contains only static methods and should not be instantiated.
     */
    private PublisherValidator() {
    }

    /**
     * Checks if data from file are valid.
     *
     * @param line :: the line from file
     * @return result :: true if data is valid, false otherwise
     */
    public static boolean isValid(String line) {
        return isDateValid(line) && isTimeValid(line) && isCodeValid(line);
    }

    /**
     * Checks if date from the line is valid.
     *
     * @param line :: the line from file
     * @return result :: true if date is valid, false otherwise
     */
    static boolean isDateValid(String line) {
        try {
            String date = line.substring(0, 10);
            LocalDate.from(DATE_FORMATTER.parse(date));
            return true;
        } catch (Exception e) {
            ErrorHelper.add(String.format("Error processing line %s. Date is not valid.", line));
            return false;
        }
    }

    /**
     * Checks if time from the line is valid.
     *
     * @param line :: the line from file
     * @return result :: true if time is valid, false otherwise
     */
    static boolean isTimeValid(String line) {
        try {
            String time = line.substring(11, 19);
            LocalTime.from(TIME_FORMATTER.parse(time));
            return true;
        } catch (Exception e) {
            ErrorHelper.add(String.format("Error processing line %s. Time is not valid.", line));
            return false;
        }
    }

    /**
     * Checks if code from the line is valid.
     *
     * @param line :: the line from file
     * @return result :: true if code is valid, false otherwise
     */
    static boolean isCodeValid(String line) {
        try {
            String publisherFileName = line.substring(20);
            String code = publisherFileName.split("-")[0];
            return true;
        } catch (Exception e) {
            ErrorHelper.add(String.format("Error processing line %s. Code is not valid.", line));
            return false;
        }
    }

}
