package com.accenture.covid19.util;

public final class Constants {

    public static final String CACHE_NAME = "covidCache";

    static final String ALL_KEY = "All";
    static final String FILE_NAME_VACCINE_SUFFIX = "_Vaccine";

    // USED WITH LOCAL DATA
    static final String BASE_FILE_SOURCE = "data/";
    static final String FILE_EXTENSION = ".json";

    // USED WITH 3rd party PROVIDER
    private static final String BASE_URI = "https://covid-api.mmediagroup.fr/v1";
    static final String CORONA_DEATH_CASES_URI = BASE_URI + "/cases";
    static final String CORONA_VACCINE_CASES = BASE_URI +"/vaccines";
    static final String CONTINENT_ARGUMENT = "?continent=";
}
