package lv.analytick.test.currencyrate.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FiatCurrencyRateDto {
    private String currency;
    private double rate;

    public FiatCurrencyRateDto() {
    }

    public FiatCurrencyRateDto(String currency, double rate) {
        this.currency = currency;
        this.rate = new BigDecimal(String.valueOf(rate))
                .setScale(2, RoundingMode.CEILING)
                .doubleValue();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = new BigDecimal(String.valueOf(rate))
                .setScale(2, RoundingMode.CEILING)
                .doubleValue();
    }
}