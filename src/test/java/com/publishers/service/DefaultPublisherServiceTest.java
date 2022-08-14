package com.publishers.service;

import com.publishers.config.ApplicationProperties;
import com.publishers.entity.Publisher;
import com.publishers.helper.ErrorHelper;
import com.publishers.dto.PublisherDto;
import com.publishers.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultPublisherServiceTest {

    private DefaultPublisherService defaultPublisherService;

    private PublisherRepository publisherRepository;
    private ApplicationProperties applicationProperties;

    @BeforeEach
    void initMock() {
        applicationProperties = mock(ApplicationProperties.class);
        publisherRepository = mock(PublisherRepository.class);
        defaultPublisherService = new DefaultPublisherService(applicationProperties, publisherRepository);
    }

    @Test
    void givenPublisherNotExist_whenCreateOrUpdate_thenSave() {
        PublisherDto publisherDto = new PublisherDto(LocalDateTime.now(), "ABC", "testFileName");
        when(publisherRepository.findByCode(anyString())).thenReturn(Optional.empty());
        when(publisherRepository.save(any())).thenReturn(null);

        defaultPublisherService.createOrUpdate("ABC", publisherDto);
        verify(publisherRepository, times(1)).save(any());

    }

    @Test
    void givenPublisherExist_whenCreateOrUpdate_thenUpdate() {
        PublisherDto publisherDto = new PublisherDto(LocalDateTime.now(), "ABC", "testFileName");

        when(publisherRepository.findByCode(anyString())).thenReturn(Optional.of(new Publisher()));
        doNothing().when(publisherRepository).updateFile(anyString(), anyString());

        defaultPublisherService.createOrUpdate("ABC", publisherDto);
        verify(publisherRepository, times(1)).updateFile(anyString(), anyString());
    }

    @Test
    void givenMultipartFile_whenProcess_thenAddAwsStoragePath() {
        when(applicationProperties.getAwsStoragePath()).thenReturn("s3://some-special-bucket/production/publisher-data/");
        doNothing().when(publisherRepository).updateFile(anyString(), anyString());
        when(publisherRepository.save(any())).thenReturn(null);
        when(publisherRepository.findByCode(anyString())).thenReturn(Optional.of(new Publisher()));

        Map<String, PublisherDto> publisherDataByCode = new HashMap<>();
        publisherDataByCode.put("Publisher1", new PublisherDto(LocalDateTime.now(), "Publisher1", "AICQUN-c130028c-8ba5-4a74-b0bc-fab353609ana"));
        publisherDataByCode.put("Publisher2", new PublisherDto(LocalDateTime.now(), "Publisher1", "AICQUN-c130028c-8ba5-4a74-b0bc-fab353609ana"));
        publisherDataByCode.put("Publisher3", new PublisherDto(LocalDateTime.now(), "Publisher1", "AICQUN-c130028c-8ba5-4a74-b0bc-fab353609ana"));

        defaultPublisherService.process(publisherDataByCode);

        assertNotNull(publisherDataByCode.get("Publisher1"));
        assertTrue(publisherDataByCode.get("Publisher1").getFileName().startsWith("s3://some-special-bucket/production/publisher-data/"));
    }

    @Test
    void givenMultipartFile_whenProcessFile_thenExecuteMethods() {
        when(applicationProperties.getAwsStoragePath()).thenReturn("s3://some-special-bucket/production/publisher-data/");
        doNothing().when(publisherRepository).updateFile(anyString(), anyString());
        when(publisherRepository.save(any())).thenReturn(null);
        when(publisherRepository.findByCode(anyString())).thenReturn(Optional.of(new Publisher()));

        defaultPublisherService.processFile(new MockMultipartFile("Test file", "ABS".getBytes(StandardCharsets.UTF_8)));

        assertFalse(ErrorHelper.get().isEmpty());
    }
}