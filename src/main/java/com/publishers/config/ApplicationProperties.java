package com.publishers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Represents a property class that contains values defined inside application.properties file.
 *
 * @author Ana Peterlic
 */
@Component
public class ApplicationProperties {

    @Value("${aws.storage.path}")
    private String awsStoragePath;

    public String getAwsStoragePath() {
        return awsStoragePath;
    }

}
