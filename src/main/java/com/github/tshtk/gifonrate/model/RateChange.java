package com.github.tshtk.gifonrate.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RateChange {
    INCREASE(1), DECREASE(-1), CONST(0);

    private final int value;

    public static RateChange byValue(int value) {
        for (RateChange rateChange : values()) {
            if (rateChange.value == value) {
                return rateChange;
            }
        }
        return null;
    }
}
