package com.github.tshtk.gifonrate.service;

import com.github.tshtk.gifonrate.model.RateChange;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GifOnRateServiceTest {

    @Autowired
    private GifOnRateService gifOnRateService;

    @Test
    void shouldReturnTag() {
        for (RateChange change: RateChange.values()) {
            assertThat(gifOnRateService.getTagByChange(change))
                .isExactlyInstanceOf(String.class)
                .isNotNull();
        }
    }
}
