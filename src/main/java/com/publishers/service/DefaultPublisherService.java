package com.publishers.service;

import com.publishers.config.ApplicationProperties;
import com.publishers.dto.PublisherDto;
import com.publishers.entity.Publisher;
import com.publishers.helper.FileParserHelper;
import com.publishers.helper.PublisherMapper;
import com.publishers.repository.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents the service class for publishers.
 *
 * @author Ana Peterlic
 */
@Service
public class DefaultPublisherService implements PublisherService {

    private final ApplicationProperties properties;

    private final PublisherRepository publisherRepository;

    /**
     * Creates an instance of {@link DefaultPublisherService} class.
     *
     * @param properties          :: the application properties instance
     * @param publisherRepository :: the publisher repository instance
     */
    public DefaultPublisherService(ApplicationProperties properties, PublisherRepository publisherRepository) {
        this.properties = properties;
        this.publisherRepository = publisherRepository;
    }

    /**
     * Reads lines from the file, finds the last record for each publisher and updates
     * the database accordingly.
     *
     * @param file :: the file
     */
    @Override
    public void processFile(MultipartFile file) {
        Map<String, PublisherDto> publisherDataByCode = FileParserHelper.extract(file);
        process(publisherDataByCode);
    }

    void process(Map<String, PublisherDto> publisherDataByCode) {
        publisherDataByCode.forEach((code, publisherDto) -> {
            publisherDto.setFileName(properties.getAwsStoragePath() + publisherDto.getFileName());
            createOrUpdate(code, publisherDto);
        });
    }

    void createOrUpdate(String code, PublisherDto publisherDto) {
        Optional<Publisher> optionalPublisher = publisherRepository.findByCode(code);
        if (optionalPublisher.isPresent()) {
            publisherRepository.updateFile(code, publisherDto.getFileName());
        } else {
            publisherRepository.save(Objects.requireNonNull(PublisherMapper.toEntity(publisherDto)));
        }
    }
}
