package com.accenture.covid19.service;

import com.accenture.covid19.dto.response.CorrelationResponseDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.http.HttpClient;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CovidInfoServiceTestConfiguration.class})
public class CovidInfoServiceTest {

    @Autowired
    private CovidInfoService covidInfoService;

    @Test
    public void retrieve_specific_continent_correlation_coefficient() {
        final CorrelationResponseDTO response = covidInfoService.getContinentCoronaInfoFromFile("Africa");
        assertThat(response.getCorrelation()).isNotBlank();
    }

    @Test
    public void retrieve_all_countries_correlation_coefficient() {
        final CorrelationResponseDTO response = covidInfoService.getContinentCoronaInfoFromFile(null);
        assertThat(response.getCorrelation()).isNotBlank();
    }

}

@Configuration
class CovidInfoServiceTestConfiguration {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Bean
    public CovidInfoService covidInfoService(final HttpClient httpClient) {
        return new CovidInfoService(httpClient);
    }

}
