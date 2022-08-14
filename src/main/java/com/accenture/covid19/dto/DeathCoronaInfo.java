package com.accenture.covid19.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeathCoronaInfo {

    private String country;
    private String continent;
    private Double confirmed;
    private Double recovered;
    private Double deaths;
    private Double population;

    public Double getPercentageDeath() {
        if (deaths == null
                || deaths == 0
                || population == null
                || population == 0) {
            return 0.0;
        }
        return new BigDecimal(deaths * 100 / population)
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
