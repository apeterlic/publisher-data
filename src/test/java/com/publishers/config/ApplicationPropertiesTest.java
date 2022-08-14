package com.publishers.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApplicationPropertiesTest {

    ApplicationProperties applicationProperties;

    @BeforeEach
    void initMock() {
        applicationProperties = mock(ApplicationProperties.class);
    }

    @Test
    void givenAwsPath_whenGetAwsStoragePath_thenReturnValue() {
        when(applicationProperties.getAwsStoragePath()).thenReturn("s3://some-special-bucket/production/publisher-data/");
        assertNotNull(applicationProperties.getAwsStoragePath());
    }
}
