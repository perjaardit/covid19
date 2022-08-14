package com.accenture.covid19.util;

import org.springframework.http.MediaType;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpRequest;

import static com.accenture.covid19.util.Constants.ALL_KEY;
import static com.accenture.covid19.util.Constants.BASE_FILE_SOURCE;
import static com.accenture.covid19.util.Constants.CONTINENT_ARGUMENT;
import static com.accenture.covid19.util.Constants.CORONA_DEATH_CASES_URI;
import static com.accenture.covid19.util.Constants.CORONA_VACCINE_CASES;
import static com.accenture.covid19.util.Constants.FILE_EXTENSION;
import static com.accenture.covid19.util.Constants.FILE_NAME_VACCINE_SUFFIX;

public final class CovidResourceUtil {

    /**
     * Return the corona death or vaccination json data as {@link InputStream}.
     * In case the {@param continent } is null, the result should have the vaccinated
     * data, otherwise the death data.
     *
     * @param continent        the specific continent in search
     * @param isVaccinatedFile indicates if the file should have vaccination data
     * @return file data as {@link InputStream}
     */
    public static InputStream getFileAsIOStream(final String continent,
                                                final boolean isVaccinatedFile) {
        String fileName = continent;

        if (fileName == null) {
            // all json file, no specific continent
            fileName = ALL_KEY;
        }

        if (isVaccinatedFile) {
            fileName += FILE_NAME_VACCINE_SUFFIX;
        }

        return CovidResourceUtil.class
                .getClassLoader()
                .getResourceAsStream(BASE_FILE_SOURCE + fileName + FILE_EXTENSION);
    }

    /**
     * Return the build request as {@link HttpRequest}.
     * In case the {@param continent } is null, the request should be of the vaccinated resource,
     * otherwise the death cases resource. The request is directed to a 3rd party provider.
     *
     * @param continent           the specific continent in search
     * @param isVaccinatedRequest indicates if the request should have vaccination data
     * @return file data as {@link HttpRequest}
     */
    public static HttpRequest getResourceRequest(final String continent,
                                                 final boolean isVaccinatedRequest) {
        String covidURI = CORONA_DEATH_CASES_URI;
        if (isVaccinatedRequest) {
            covidURI = CORONA_VACCINE_CASES;
        }

        if (continent != null) {
            covidURI += CONTINENT_ARGUMENT + continent;
        }

        return HttpRequest.newBuilder()
                .GET().uri(URI.create(covidURI))
                .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
