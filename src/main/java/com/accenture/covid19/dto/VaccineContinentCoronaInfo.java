package com.accenture.covid19.dto;

import com.accenture.covid19.util.VaccineCoronaInfoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonDeserialize(using = VaccineCoronaInfoDeserializer.class)
public class VaccineContinentCoronaInfo {

    private List<VaccineCoronaInfo> countries;
}

