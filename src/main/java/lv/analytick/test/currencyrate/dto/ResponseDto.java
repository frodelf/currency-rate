package lv.analytick.test.currencyrate.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResponseDto {
    private List<FiatCurrencyRateDto> fiat = new ArrayList<>();
    private List<CryptoCurrencyRateDto> crypto = new ArrayList<>();

    public List<FiatCurrencyRateDto> getFiat() {
        return fiat;
    }

    public void setFiat(List<FiatCurrencyRateDto> fiat) {
        this.fiat = fiat;
    }

    public List<CryptoCurrencyRateDto> getCrypto() {
        return crypto;
    }

    public void setCrypto(List<CryptoCurrencyRateDto> crypto) {
        this.crypto = crypto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseDto that = (ResponseDto) o;
        return Objects.equals(fiat, that.fiat) && Objects.equals(crypto, that.crypto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fiat, crypto);
    }
}