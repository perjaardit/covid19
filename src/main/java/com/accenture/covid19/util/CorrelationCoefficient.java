package com.accenture.covid19.util;

public enum CorrelationCoefficient {

    VERY_HIGHLY(0.9, 1.0, " very highly correlated"),
    HIGHLY(0.7, 0.9, " highly correlated"),
    MODERATELY(0.5, 0.7, " moderately correlated"),
    LOW(0.3, 0.5, " low correlation"),
    VERY_LOW(0.0, 0.3, " little if any (linear) correlation");

    private double inclusiveFrom;
    private double exclusiveTo;
    private String interpretation;

    CorrelationCoefficient(final double inclusiveFrom,
                           final double exclusiveTo,
                           final String interpretation) {
        this.inclusiveFrom = inclusiveFrom;
        this.exclusiveTo = exclusiveTo;
        this.interpretation = interpretation;
    }

    public double getInclusiveFrom() {
        return inclusiveFrom;
    }

    public double getExclusiveTo() {
        return exclusiveTo;
    }

    public String getInterpretation() {
        return interpretation;
    }

    /**
     * Return the specific interpretation for the calculated coefficient.
     * <p>
     * 0.9 and 1.0 -> very highly correlated
     * 0.7 and 0.9 -> highly correlated
     * 0.5 and 0.7 -> moderately correlated
     * 0.3 and 0.5 -> low correlation
     * less than 0.3 have little if any (linear) correlation
     *
     * @param correlationCoefficient the calculated coefficient
     * @return the interpretation of the specific coefficient
     */
    public static String getCorrelationMessage(final double correlationCoefficient) {
        for (final CorrelationCoefficient coefficient : CorrelationCoefficient.values()) {
            if (correlationCoefficient >= coefficient.getInclusiveFrom()
                    && correlationCoefficient < coefficient.getExclusiveTo()) {
                return coefficient.getInterpretation();
            }
        }

        if (correlationCoefficient == VERY_HIGHLY.getExclusiveTo()) {
            return VERY_HIGHLY.getInterpretation();
        }

        return null;
    }
}
