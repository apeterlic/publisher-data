package com.publishers.helper;

import com.publishers.dto.PublisherDto;
import com.publishers.exception.FileProcessingException;
import com.publishers.validation.PublisherValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static com.publishers.util.Constants.DATE_FORMATTER;
import static com.publishers.util.Constants.TIME_FORMATTER;

/**
 * Represents a helper for parsing txt files.
 *
 * @author Ana Peterlic
 */
public class FileParserHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileParserHelper.class);

    /**
     * Private constructor since this class contains only static methods and should not be instantiated.
     */
    private FileParserHelper() {
    }

    public static Map<String, PublisherDto> extract(MultipartFile file) {
        Map<String, PublisherDto> publishersByCode = new HashMap<>();

        try {
            InputStream inputStream = file.getInputStream();
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines().forEach(line -> parseLine(publishersByCode, line));
        } catch (Exception e) {
            LOGGER.error("extract() - ", e);
            throw new FileProcessingException("Problem occurred during file processing");
        }

        return publishersByCode;
    }


    static void parseLine(Map<String, PublisherDto> publishersByCode, String line) {
        try {

            if (!PublisherValidator.isValid(line)) {
                return;
            }

            String date = line.substring(0, 10);
            String time = line.substring(11, 19);
            String publisherFileName = line.substring(20);
            String code = publisherFileName.split("-")[0];
            LocalDateTime localDateTime = LocalDateTime.of(
                    LocalDate.from(DATE_FORMATTER.parse(date)),
                    LocalTime.from(TIME_FORMATTER.parse(time)));

            addPublisher(publishersByCode, publisherFileName, code, localDateTime);
        } catch (Exception e) {
            LOGGER.error("extract() - ", e);
            ErrorHelper.add(String.format("Error processing line %s. %s", line, e.getMessage()));
        }
    }

    static void addPublisher(Map<String, PublisherDto> publishersByCode,
                             String publisherFileName,
                             String code,
                             LocalDateTime created) {
        if (publishersByCode.get(code) == null ||
                created.isAfter(publishersByCode.get(code).getCreated())
        ) {
            publishersByCode.put(code, new PublisherDto(created, code, publisherFileName));
        }
    }
}
