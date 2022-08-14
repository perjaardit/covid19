package com.accenture.covid19.exception;

public class GeneralCovidException extends RuntimeException {

    public static final String INTERNAL_ERROR_MESSAGE = "Impossible to proceed due to an internal error!";
    public static final String DATA_RETRIEVAL_ERROR_MESSAGE = "Impossible retrieving data from provider!";
    public static final String NO_DATA_PRESENT = "No data present. Nothing to calculate!";

    public GeneralCovidException(final String message) {
        super(message);
    }
}
