package com.accenture.covid19.dto;

import com.accenture.covid19.util.DeathCoronaInfoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonDeserialize(using = DeathCoronaInfoDeserializer.class)
public class DeathContinentCoronaInfo {

    private List<DeathCoronaInfo> countries;
}

