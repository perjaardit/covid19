package com.accenture.covid19.util;

import com.accenture.covid19.dto.DeathContinentCoronaInfo;
import com.accenture.covid19.dto.DeathCoronaInfo;
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
public class DeathCoronaInfoDeserializer extends JsonDeserializer<DeathContinentCoronaInfo> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public DeathContinentCoronaInfo deserialize(final JsonParser jp,
                                                final DeserializationContext context) throws IOException {
        final JsonNode rootNode = jp.getCodec().readTree(jp);

        final List<DeathCoronaInfo> coronaInfoList = new LinkedList<>();
        rootNode.elements().forEachRemaining(child -> {
            try {
                final DeathCoronaInfo coronaInfo = objectMapper.readValue(child.get(ALL_KEY).toString(),
                        DeathCoronaInfo.class);
                if (coronaInfo.getContinent() != null) {
                    coronaInfoList.add(coronaInfo);
                }
            } catch (Exception ex) {
                log.error("Error while parsing response data!", ex);
            }
        });

        coronaInfoList.sort(Comparator.comparing(DeathCoronaInfo::getCountry));
        return DeathContinentCoronaInfo.builder().countries(coronaInfoList).build();
    }
}
