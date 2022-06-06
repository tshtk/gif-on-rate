package com.github.tshtk.gifonrate.service;

import com.github.tshtk.gifonrate.model.RateChange;
import com.github.tshtk.gifonrate.validation.CurrencyCodeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GifOnRateService {
    private final CurrencyCodeValidator validator;
    private final RateService rateService;
    private final GifService gifService;
    private final Map<RateChange, String> tags;

    public byte[] getGif(String currency) {
        validator.validate(currency);
        RateChange changeLastDay = rateService.getChangeLastDay(currency);
        String tag = getTagByChange(changeLastDay);
        return gifService.getGifByTag(tag);
    }

    public String getTagByChange(RateChange change) {
        return tags.get(change);
    }
}
