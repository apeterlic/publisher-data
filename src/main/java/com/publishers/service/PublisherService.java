package com.publishers.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Represents a publisher service interface.
 *
 * @author Ana Peterlic
 */
public interface PublisherService {

    /**
     * Reads lines from the file, finds the last record for each publisher and updates
     * the database accordingly.
     *
     * @param file :: the file
     */
    void processFile(MultipartFile file);
}
