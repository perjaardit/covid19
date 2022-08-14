package com.accenture.covid19.service;

import com.accenture.covid19.dto.DeathContinentCoronaInfo;
import com.accenture.covid19.dto.VaccineContinentCoronaInfo;
import com.accenture.covid19.dto.response.CorrelationResponseDTO;
import com.accenture.covid19.exception.GeneralCovidException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.accenture.covid19.exception.GeneralCovidException.DATA_RETRIEVAL_ERROR_MESSAGE;
import static com.accenture.covid19.util.CovidResourceUtil.getFileAsIOStream;
import static com.accenture.covid19.util.CovidResourceUtil.getResourceRequest;

@Service
@AllArgsConstructor
public class CovidInfoService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CorrelationCalculator correlationCalculator = new CorrelationCalculator();

    /**
     * Calculate the correlation coefficient for a specific continent or all countries,
     * in case the continent is not specified. The data are retrieved from the json file
     * on resource folder.
     *
     * @param continent the continent for which to calculate the correlation coefficient
     * @return the calculated correlation with is interpretation as {@link CorrelationResponseDTO}
     * @throws GeneralCovidException in case not possible to retrieve the data from resource
     */
    public CorrelationResponseDTO getContinentCoronaInfoFromFile(final String continent) {
        DeathContinentCoronaInfo deathData;
        VaccineContinentCoronaInfo vaccineData;
        try {
            deathData = objectMapper.readValue(getFileAsIOStream(continent, false),
                    DeathContinentCoronaInfo.class);
            vaccineData = objectMapper.readValue(getFileAsIOStream(continent, true),
                    VaccineContinentCoronaInfo.class);
        } catch (Exception ex) {
            throw new GeneralCovidException(DATA_RETRIEVAL_ERROR_MESSAGE);
        }

        return CorrelationResponseDTO.builder()
                .correlation(correlationCalculator.getCorrelation(deathData, vaccineData)).build();
    }

    /**
     * Calculate the correlation coefficient for a specific continent or all countries,
     * in case the continent is not specified. The data are retrieved from the 3rd party provider..
     *
     * @param continent the continent for which to calculate the correlation coefficient
     * @return the calculated correlation with is interpretation as {@link CorrelationResponseDTO}
     * @throws GeneralCovidException in case not possible to retrieve the data from resource
     */
    public CorrelationResponseDTO getContinentCoronaInfoFromProvider(final String continent) {
        HttpRequest request;
        HttpResponse<String> response;
        DeathContinentCoronaInfo deathData;
        VaccineContinentCoronaInfo vaccineData;
        try {
            // corona death cases
            request = getResourceRequest(continent, false);
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            deathData = objectMapper.readValue(response.body(), DeathContinentCoronaInfo.class);

            // corona vaccine cases
            request = getResourceRequest(continent, true);
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            vaccineData = objectMapper.readValue(response.body(), VaccineContinentCoronaInfo.class);
        } catch (Exception ex) {
            throw new GeneralCovidException(DATA_RETRIEVAL_ERROR_MESSAGE);
        }

        return CorrelationResponseDTO.builder()
                .correlation(correlationCalculator.getCorrelation(deathData, vaccineData)).build();
    }

}
