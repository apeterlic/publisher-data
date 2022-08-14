package com.publishers;


import com.publishers.api.PublisherController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SmokeTest {

    @Autowired
    private PublisherController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
