package com.publishers.validation;

import com.publishers.helper.ErrorHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PublisherValidatorTest {

    @Test
    void givenValidData_whenIsValid_thenReturnTrue() {
        String line = "2021-07-22 07:27:47 AICQUN-c130028c-8ba5-4a74-b0bc-fab353609ana";
        assertTrue(PublisherValidator.isValid(line));
    }

    @Test
    void givenInvalidData_whenIsValid_thenReturnTrue() {
        String line = "2021-07-22 07:27:47";
        assertFalse(PublisherValidator.isValid(line));
        assertFalse(ErrorHelper.get().isEmpty());
    }

    @Test
    void givenDate_whenIsDateValid_thenReturnTrue() {
        String line = "2021-07-22 07:27:47 AICQUN-c130028c-8ba5-4a74-b0bc-fab353609ana";
        assertTrue(PublisherValidator.isDateValid(line));
    }

    @Test
    void givenInvalidDate_whenIsDateValid_thenReturnFalse() {
        String line = "2021.07.22 07:27:47 AICQUN-c130028c-8ba5-4a74-b0bc-fab353609ana";
        assertFalse(PublisherValidator.isDateValid(line));
    }

    @Test
    void givenTime_whenIsTimeValid_thenReturnTrue() {
        String line = "2021-07-22 07:27:47 AICQUN-c130028c-8ba5-4a74-b0bc-fab353609ana";
        assertTrue(PublisherValidator.isTimeValid(line));
    }

    @Test
    void givenInvalidTime_whenIsTimeValid_thenReturnFalse() {
        String line = "2021-07-22 52:27:47 AICQUN-c130028c-8ba5-4a74-b0bc-fab353609ana";
        assertFalse(PublisherValidator.isTimeValid(line));
    }

    @Test
    void givenNoCode_whenIsCodeValid_thenReturnFalse() {
        String line = "2021-07-22 07:27:47";
        assertFalse(PublisherValidator.isCodeValid(line));
    }

    @Test
    void givenCode_whenIsCodeValid_thenReturnTrue() {
        String line = "2021-07-22 07:27:47 AICQUN-c130028c-8ba5-4a74-b0bc-fab353609ana";
        assertTrue(PublisherValidator.isCodeValid(line));
    }
}