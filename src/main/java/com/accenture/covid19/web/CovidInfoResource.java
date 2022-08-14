package com.accenture.covid19.web;

import com.accenture.covid19.dto.response.CorrelationResponseDTO;
import com.accenture.covid19.service.CovidInfoService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/correlation/details")
public class CovidInfoResource {

    private final CovidInfoService covidInfoService;

    @GetMapping
    @Cacheable(value = "covidCache")
    public ResponseEntity<CorrelationResponseDTO> getAll() {
//        return ResponseEntity.ok().body(covidInfoService.getContinentCoronaInfoFromFile(null));
        return ResponseEntity.ok().body(covidInfoService.getContinentCoronaInfoFromProvider(null));
    }


    @Cacheable(value = "covidCache")
    @GetMapping(value = "/{continent}")
    public ResponseEntity<CorrelationResponseDTO> getSpecificContinent(@PathVariable("continent") final String continent) {
//        return ResponseEntity.ok().body(covidInfoService.getContinentCoronaInfoFromFile(continent));
        return ResponseEntity.ok().body(covidInfoService.getContinentCoronaInfoFromProvider(continent));
    }
}
