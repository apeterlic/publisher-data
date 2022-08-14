package com.publishers.helper;

import com.publishers.entity.Publisher;
import com.publishers.dto.PublisherDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link PublisherMapper} class.
 */
class PublisherMapperTest {

    @Test
    void givenNoPublisherDto_whenToEntity_thenReturnNull() {
        assertNull(PublisherMapper.toEntity(null));
    }

    @Test
    void givenPublisherDto_whenToEntity_thenReturnNotNull() {
        PublisherDto publisherDto = new PublisherDto(LocalDateTime.now(), "ABC", "testFileName");
        Publisher publisher = PublisherMapper.toEntity(publisherDto);

        assertNotNull(publisher);
        assertEquals(publisherDto.getPublisherCode(), publisher.getCode());
        assertEquals(publisherDto.getFileName(), publisher.getFile());
        assertTrue(publisher.isActive());
        assertNull(publisher.getName());
    }

}