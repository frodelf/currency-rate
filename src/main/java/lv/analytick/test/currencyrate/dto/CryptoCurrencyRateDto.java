package lv.analytick.test.currencyrate.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CryptoCurrencyRateDto {
    private String name;
    private double value;

    public CryptoCurrencyRateDto() {
    }

    public CryptoCurrencyRateDto(String name, double value) {
        this.name = name;
        this.value = new BigDecimal(String.valueOf(value))
                .setScale(2, RoundingMode.CEILING)
                .doubleValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = new BigDecimal(String.valueOf(value))
                .setScale(2, RoundingMode.CEILING)
                .doubleValue();
    }
}