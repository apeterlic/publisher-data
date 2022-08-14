package com.publishers.helper;

import com.publishers.dto.PublisherDto;
import com.publishers.entity.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublisherMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherMapper.class);

    /**
     * Private constructor since this class contains only static methods and should not be instantiated.
     */
    private PublisherMapper() {
    }

    /**
     * Converts DTO object to entity instance.
     *
     * @param publisherDto :: the publisher DTO instance
     * @return result :: new publisher object
     */
    public static Publisher toEntity(PublisherDto publisherDto) {
        if (publisherDto == null) {
            LOGGER.warn("toEntity() - publisherDto is null");
            return null;
        }

        Publisher publisher = new Publisher();
        publisher.setCode(publisherDto.getPublisherCode());
        publisher.setFile(publisherDto.getFileName());
        publisher.setActive(true);
        return publisher;
    }
}
