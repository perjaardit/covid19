package com.accenture.covid19.util;

import com.accenture.covid19.dto.VaccineContinentCoronaInfo;
import com.accenture.covid19.dto.VaccineCoronaInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static com.accenture.covid19.util.Constants.ALL_KEY;

@Slf4j
public class VaccineCoronaInfoDeserializer extends JsonDeserializer<VaccineContinentCoronaInfo> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public VaccineContinentCoronaInfo deserialize(final JsonParser jp,
                                                  final DeserializationContext context) throws IOException {
        final JsonNode rootNode = jp.getCodec().readTree(jp);

        final List<VaccineCoronaInfo> coronaInfoList = new LinkedList<>();
        rootNode.elements().forEachRemaining(child -> {
            try {

                final VaccineCoronaInfo coronaInfo = objectMapper.readValue(child.get(ALL_KEY)
                        .toString(), VaccineCoronaInfo.class);
                if (coronaInfo.getContinent() != null) {
                    coronaInfoList.add(coronaInfo);
                }
            } catch (Exception ex) {
                log.error("Error while parsing response data!", ex);
            }
        });

        coronaInfoList.sort(Comparator.comparing(VaccineCoronaInfo::getCountry));
        return VaccineContinentCoronaInfo.builder().countries(coronaInfoList).build();
    }
}
