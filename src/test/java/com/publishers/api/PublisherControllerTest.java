package com.publishers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.publishers.service.DefaultPublisherService;
import com.publishers.util.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.net.URL;

import static com.publishers.util.Constants.ApiAttribute.MESSAGE;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PublisherController.class)
class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DefaultPublisherService defaultPublisherService;

    @Test
    void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Upload File")));
    }

    @Test
    void givenFile_whenUpload_thenReturnSuccessMessages() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("publishersFileList.txt");

        assert url != null;
        File file = new File(url.getPath());
        String fileName = FilenameUtils.getName(file.getName());
        byte[] bytes = FileUtils.readFileToByteArray(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, MediaType.MULTIPART_FORM_DATA_VALUE, bytes);

        this.mockMvc.perform(multipart("/upload").file(mockMultipartFile))
                .andExpect(status().is(302))
                .andExpect(flash().attribute(MESSAGE, String.format(Constants.ApiMessages.OK_UPLOAD, fileName)))
                .andDo(print());
    }

    @Test
    void givenInvalidFile_whenUpload_thenReturnMessages() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("invalid-publishersFileList.txt");

        assert url != null;
        File file = new File(url.getPath());
        String fileName = FilenameUtils.getName(file.getName());
        byte[] bytes = FileUtils.readFileToByteArray(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName,
                MediaType.MULTIPART_FORM_DATA_VALUE, bytes);

        this.mockMvc.perform(multipart("/upload").file(mockMultipartFile))
                .andExpect(status().is(302))
                .andExpect(flash().attribute(MESSAGE, String.format(Constants.ApiMessages.OK_UPLOAD, fileName)))
                .andDo(print());
    }

    @Test
    void givenEmptyFile_whenUpload_thenReturnMessage() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("empty-publishersFileList.txt");

        assert url != null;
        File file = new File(url.getPath());
        String fileName = FilenameUtils.getName(file.getName());
        byte[] bytes = FileUtils.readFileToByteArray(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, MediaType.MULTIPART_FORM_DATA_VALUE, bytes);

        this.mockMvc.perform(multipart("/upload").file(mockMultipartFile))
                .andExpect(status().is(302))
                .andExpect(flash().attribute(MESSAGE, Constants.ApiMessages.ERROR_FILE_NOT_SELECTED))
                .andDo(print());
    }


}