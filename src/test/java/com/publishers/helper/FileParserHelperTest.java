package com.publishers.helper;

import com.publishers.exception.FileProcessingException;
import com.publishers.dto.PublisherDto;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileParserHelperTest {

    @Test
    void givenMultipartFile_whenExtract_thenReturnOk() {
        String content = "2021-07-24 07:27:47 LQFCNO-3191e7b0-8c6f-4ba8-b9e8-83dc88a5488c\n" +
                "2021-08-02 07:27:47 GYODHW-992a3ff4-e392-482e-a761-57cff507131c\n" +
                "2021-07-14 07:27:47 LQFCNO-1b266b40-8429-48fe-8ff0-94d254b32392\n" +
                "2021-08-14 07:27:47 TTAZNE-4abb4cf7-afbb-43a3-9395-43a15fb15647\n" +
                "2021-07-17 07:27:47 EZYFEX-fc993f58-da1d-4ace-8898-106c1dc26191\n" +
                "2021-07-20 07:27:47 AICQUN-c130028c-8ba5-4a74-b0bc-fab353609cf3\n" +
                "2021-07-22 07:27:47 AICQUN-c130028c-8ba5-4a74-b0bc-fab353609ana";
        MultipartFile file = new MockMultipartFile("file1", content.getBytes(StandardCharsets.UTF_8));

        Map<String, PublisherDto> publishersByCode = FileParserHelper.extract(file);
        assertFalse(publishersByCode.isEmpty());
    }

    @Test
    void givenInvalidMultipartFile_whenExtract_thenThrowFileProcessingException() {
        FileProcessingException thrown = assertThrows(
                FileProcessingException.class,
                () -> FileParserHelper.extract(null)
        );

        assertTrue(thrown.getMessage().contains("Problem occurred during file processing"));
    }

    @Test
    void givenMultipleDataForPublisher_whenAddPublisher_thenChooseLatestData() {
        Map<String, PublisherDto> publisherDataByCode = new HashMap<>();
        PublisherDto p1 = new PublisherDto(LocalDateTime.of(2021, 5, 12, 10, 15), "Publisher1", "AICQUN-c130028c-8ba5-4a74-b0bc-fab353609a1");
        PublisherDto p2 = new PublisherDto(LocalDateTime.now(), "Publisher1", "AICQUN-c130028c-8ba5-4a74-b0bc-fab353609a2");
        PublisherDto p3 = new PublisherDto(LocalDateTime.of(2021, 9, 12, 10, 15), "Publisher1", "AICQUN-c130028c-8ba5-4a74-b0bc-fab353609a3");

        FileParserHelper.addPublisher(publisherDataByCode, p1.getFileName(), p1.getPublisherCode(), p1.getCreated());
        FileParserHelper.addPublisher(publisherDataByCode, p2.getFileName(), p2.getPublisherCode(), p2.getCreated());
        FileParserHelper.addPublisher(publisherDataByCode, p3.getFileName(), p3.getPublisherCode(), p3.getCreated());

        assertEquals(publisherDataByCode.get("Publisher1").getFileName(), "AICQUN-c130028c-8ba5-4a74-b0bc-fab353609a2");
    }


    @Test
    void givenInvalidLine_whenParseLine_thenSkipLine() {
        String invalidLine = "2021-7-24 07:27:47 LQFCNO-3191e7b0-8c6f-4ba8-b9e8-83dc88a5488c";
        Map<String, PublisherDto> publisherDataByCode = new HashMap<>();

        FileParserHelper.parseLine(publisherDataByCode, invalidLine);
        assertTrue(publisherDataByCode.isEmpty());
    }

    @Test
    void givenLine_whenExtractFromLine_thenReturnOk() {
        String invalidLine = "2021-07-24 07:27:47 LQFCNO-3191e7b0-8c6f-4ba8-b9e8-83dc88a5488c";
        Map<String, PublisherDto> publisherDataByCode = new HashMap<>();

        FileParserHelper.parseLine(publisherDataByCode, invalidLine);
        assertFalse(publisherDataByCode.isEmpty());
    }


}