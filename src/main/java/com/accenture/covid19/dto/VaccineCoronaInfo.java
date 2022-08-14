package com.accenture.covid19.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VaccineCoronaInfo {

    private String country;
    private String continent;
    private Double population;

    @JsonProperty(value = "people_vaccinated")
    private Double peopleVaccinated;

    public Double getPercentageVaccinated() {
        if (peopleVaccinated == null
                || peopleVaccinated == 0
                || population == null
                || population == 0) {
            return 0.0;
        }

        return new BigDecimal(peopleVaccinated * 100 / population)
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
