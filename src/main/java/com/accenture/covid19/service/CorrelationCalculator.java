package com.accenture.covid19.service;

import com.accenture.covid19.dto.DeathContinentCoronaInfo;
import com.accenture.covid19.dto.DeathCoronaInfo;
import com.accenture.covid19.dto.VaccineContinentCoronaInfo;
import com.accenture.covid19.dto.VaccineCoronaInfo;
import com.accenture.covid19.exception.GeneralCovidException;
import com.accenture.covid19.util.CorrelationCoefficient;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

class CorrelationCalculator {

    /**
     * Calculate the correlation coefficient with the interpretation based for the given data.
     * <p>
     * - In case that the retrieved statistic for death and vaccination are not present, no correlation is calculated.
     * <p>
     * As death (/cases) and vaccine(/vaccine) resource might not have same countries, we retain just the countries
     * that are present on both data.
     *
     * @param deathData   {@link DeathContinentCoronaInfo} corona information about the death statistics
     * @param vaccineData {@link VaccineContinentCoronaInfo} corona information about the vaccine statistics
     * @return the correlation coefficient with the interpretation of it
     */
    String getCorrelation(final DeathContinentCoronaInfo deathData,
                          final VaccineContinentCoronaInfo vaccineData) {
        if (deathData.getCountries().isEmpty()
                || vaccineData.getCountries().isEmpty()) {
            // the retrieved data should not be empty
            throw new GeneralCovidException(GeneralCovidException.NO_DATA_PRESENT);
        }

        final List<String> existingCountries = deathData.getCountries().stream()
                .map(DeathCoronaInfo::getCountry).collect(Collectors.toList());
        existingCountries.retainAll(vaccineData.getCountries().stream()
                .map(VaccineCoronaInfo::getCountry).collect(Collectors.toList()));

        if (existingCountries.isEmpty()) {
            throw new GeneralCovidException(GeneralCovidException.NO_DATA_PRESENT);
        }

        final double[] covidVaccine = vaccineData.getCountries().stream()
                .filter(vaccineCoronaInfo -> existingCountries.contains(vaccineCoronaInfo.getCountry()))
                .mapToDouble(VaccineCoronaInfo::getPercentageVaccinated).toArray();
        final double[] covidDeath = deathData.getCountries().stream()
                .filter(vaccineCoronaInfo -> existingCountries.contains(vaccineCoronaInfo.getCountry()))
                .mapToDouble(DeathCoronaInfo::getPercentageDeath).toArray();
        final double correlationCoefficient = new BigDecimal(new PearsonsCorrelation()
                .correlation(covidVaccine, covidDeath))
                .setScale(2, RoundingMode.HALF_UP).doubleValue();

        return correlationCoefficient + CorrelationCoefficient.getCorrelationMessage(correlationCoefficient);
    }

}
