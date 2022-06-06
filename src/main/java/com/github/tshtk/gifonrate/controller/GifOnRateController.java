package com.github.tshtk.gifonrate.controller;

import com.github.tshtk.gifonrate.exception.InvalidCurrencyCodeException;
import com.github.tshtk.gifonrate.exception.NoCurrencyDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.github.tshtk.gifonrate.service.GifOnRateService;

@RestController
@RequiredArgsConstructor
public class GifOnRateController {

    private final GifOnRateService gifOnRateService;

    @GetMapping(path = "/gifs", produces = "image/gif")
    public byte[] getGif(@RequestParam final String currency) {
        return gifOnRateService.getGif(currency);
    }


    @ExceptionHandler(InvalidCurrencyCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(InvalidCurrencyCodeException e) {
        return new ResponseEntity<>("Invalid currency code: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoCurrencyDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(NoCurrencyDataException e) {
        return new ResponseEntity<>("No data for currency: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
