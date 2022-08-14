package com.accenture.covid19.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorrelationResponseDTO {

    private String message;
    private String correlation;
}
